package com.simicart.saletracking.login.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppEvent;
import com.simicart.saletracking.common.AppLogging;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.login.activity.QrCodeActivity;
import com.simicart.saletracking.login.delegate.LoginDelegate;
import com.simicart.saletracking.login.entity.LoginEntity;
import com.simicart.saletracking.login.fragment.PrivacyPolicyFragment;
import com.simicart.saletracking.login.request.CheckLicenseActiveRequest;
import com.simicart.saletracking.login.request.LoginRequest;
import com.simicart.saletracking.notification.entity.NotificationEntity;
import com.simicart.saletracking.store.entity.StoreViewEntity;
import com.simicart.saletracking.store.request.GetStoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginController extends AppController {

    protected LoginDelegate mDelegate;
    protected View.OnClickListener onTryDemoClick;
    protected View.OnClickListener onLoginClick;
    protected View.OnClickListener onLoginQrClick;
    protected View.OnClickListener onPrivacyPolicyClick;
    protected String mDeviceToken;
    protected BroadcastReceiver onScanResultReceiver;

    @Override
    public void onStart() {

        mDelegate.showDialogLoading();

        new DeviceTokenAsync().execute();

        onTryDemoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard();
                AppManager.getInstance().setDemo(true);
                onLoginDemo();
            }
        };

        onLoginClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard();
                LoginEntity loginEntity = mDelegate.getLoginInfo();
                if (loginEntity != null) {
                    checkLicenseActive(loginEntity, true);
                }
            }
        };

        onLoginQrClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQrCodeClick();
            }
        };

        onPrivacyPolicyClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrivacyPolicyFragment fragment = PrivacyPolicyFragment.newInstance();
                AppManager.getInstance().getManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        };

    }

    @Override
    public void onResume() {

    }

    public class DeviceTokenAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InstanceID instanceID = InstanceID.getInstance(AppManager.getInstance().getCurrentActivity());

                mDeviceToken = instanceID.getToken("1045130557303",
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                AppLogging.logData("LoginController", "GCM Registration Token: " + mDeviceToken);

            } catch (Exception e) {
                AppLogging.logData("LoginController", "Failed to complete token refresh");
            } finally {
                if (mDeviceToken == null) {
                    mDeviceToken = Settings.Secure.getString(AppManager.getInstance().getCurrentActivity().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    mDeviceToken = "nontoken_" + mDeviceToken;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            autoSignIn();
        }
    }

    protected void autoSignIn() {
        if (Utils.isInternetAvailable()) {
            if (AppPreferences.isSignInNormal()) {
                checkLicenseActive(null, true);
            } else if (AppPreferences.isSignInQr()) {
                checkLicenseActive(null, false);
            } else {
                mDelegate.dismissDialogLoading();
            }
        } else {
            mDelegate.dismissDialogLoading();
        }
    }

    protected void checkLicenseActive(final LoginEntity loginEntity, final boolean isLoginNormal) {
        if (loginEntity != null) {
            mDelegate.showDialogLoading();
        }
        CheckLicenseActiveRequest licenseActiveRequest = new CheckLicenseActiveRequest();
        licenseActiveRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if (collection != null) {
                    boolean isActive = true;
                    boolean isExpired = false;
                    String version = "";
                    if (collection.containKey("is_active")) {
                        isActive = (boolean) collection.getDataWithKey("is_active");
                    }
                    if (collection.containKey("expired_time")) {
                        isExpired = (boolean) collection.getDataWithKey("expired_time");
                    }
                    if(collection.containKey("version")) {
                        version = (String) collection.getDataWithKey("version");
                    }
                    if (isActive && !isExpired && version.equals(AppManager.getInstance().getCurrentAppVersion())) {
                        if (isLoginNormal) {
                            onLoginUser(loginEntity);
                        } else {
                            onLoginQr(loginEntity);
                        }
                    } else {
                        mDelegate.dismissDialogLoading();
                        if(!isActive) {
                            AppNotify.getInstance().showError("Your account has not been actived!");
                            return;
                        }
                        if(isExpired) {
                            AppNotify.getInstance().showError("Your account has been expired!");
                            return;
                        }
                        if(!version.equals(AppManager.getInstance().getCurrentAppVersion())) {
                            AppNotify.getInstance().showError("Please update your application!");
                            return;
                        }
//                        if (collection.containKey("message")) {
//                            String message = (String) collection.getDataWithKey("message");
//                            if (Utils.validateString(message)) {
//                                AppNotify.getInstance().showError(message);
//                            }
//                        }
                    }
                }
            }
        });
        licenseActiveRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        licenseActiveRequest.setCustomUrl("https://www.simicart.com/usermanagement/api/authorize");
        if (loginEntity != null) {
            licenseActiveRequest.addParam("url", loginEntity.getUrl());
        } else {
            licenseActiveRequest.addParam("url", AppPreferences.getCustomerUrl());
        }
        licenseActiveRequest.request();
    }

    protected void onLoginDemo() {
        mDelegate.showDialogLoading();
        LoginRequest loginDemoRequest = new LoginRequest();
        loginDemoRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "login_demo");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("login_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                goToHome();
            }
        });
        loginDemoRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        loginDemoRequest.setExtendUrl("simitracking/rest/v2/staffs/login");
        loginDemoRequest.addParam("email", Constants.demoEmail);
        loginDemoRequest.addParam("password", Constants.demoPassword);
        loginDemoRequest.addParam("plaform_id", "3");
        if (Utils.validateString(mDeviceToken)) {
            loginDemoRequest.addParam("device_token", mDeviceToken);
        }
        loginDemoRequest.request();
    }

    protected void onLoginUser(final LoginEntity loginEntity) {
        LoginRequest loginUserRequest = new LoginRequest();
        loginUserRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();
                AppPreferences.setSignInNormal(true);
                if (loginEntity != null) {
                    AppPreferences.saveCustomerInfo(loginEntity.getUrl(), loginEntity.getEmail(), loginEntity.getPassword());
                }

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "login_user");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("login_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                goToHome();
            }
        });
        loginUserRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        loginUserRequest.setExtendUrl("simitracking/rest/v2/staffs/login");
        if (loginEntity != null) {
            loginUserRequest.setCustomUrl(loginEntity.getUrl());
            loginUserRequest.addParam("email", loginEntity.getEmail());
            loginUserRequest.addParam("password", loginEntity.getPassword());
        } else {
            loginUserRequest.addParam("email", AppPreferences.getCustomerEmail());
            loginUserRequest.addParam("password", AppPreferences.getCustomerPassword());
        }
        loginUserRequest.addParam("plaform_id", "3");
        if (Utils.validateString(mDeviceToken)) {
            loginUserRequest.addParam("device_token", mDeviceToken);
        }
        loginUserRequest.request();
    }

    protected void onQrCodeClick() {

        Intent intent = new Intent(AppManager.getInstance().getCurrentActivity(), QrCodeActivity.class);
        AppManager.getInstance().getCurrentActivity().startActivityForResult(intent, 123);

        if (onScanResultReceiver == null) {
            onScanResultReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle bundle = intent.getBundleExtra("data");
                    AppData mData = bundle.getParcelable("entity");
                    String result = (String) mData.getData().get("result");
                    if (Utils.validateString(result)) {
                        try {
                            byte[] decodedBytes = Base64.decode(result, Base64.DEFAULT);
                            final String decodedResult = new String(decodedBytes, "UTF-8");
                            parseDecodedQrCode(decodedResult);
                        } catch (UnsupportedEncodingException e) {
                            AppLogging.logData("Decode QR Result", e.getMessage());
                        }
                    }
                    LocalBroadcastManager.getInstance(AppManager.getInstance().getCurrentActivity()).unregisterReceiver(onScanResultReceiver);
                }
            };
            AppEvent.getInstance().registerEvent("login.qrcode", onScanResultReceiver);
        }
    }

    protected void parseDecodedQrCode(String qrCode) {
        try {
            JSONObject qrObj = new JSONObject(qrCode);
            LoginEntity loginEntity = new LoginEntity();
            if (qrObj.has("user_email")) {
                loginEntity.setEmail(qrObj.getString("user_email"));
            }
            if (qrObj.has("url")) {
                loginEntity.setUrl(qrObj.getString("url"));
            }
            if (qrObj.has("session_id")) {
                loginEntity.setSessionID(qrObj.getString("session_id"));
            }
            checkLicenseActive(loginEntity, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onLoginQr(final LoginEntity loginEntity) {
        LoginRequest loginQrRequest = new LoginRequest();
        loginQrRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();
                AppPreferences.setSignInQr(true);
                if (loginEntity != null) {
                    AppPreferences.saveCustomerInfoForQr(loginEntity.getUrl(), loginEntity.getEmail(), loginEntity.getSessionID());
                }

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "login_qr_code");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("login_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                goToHome();
            }
        });
        loginQrRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        loginQrRequest.setExtendUrl("simitracking/rest/v2/staffs/loginWithKeySession");
        if (loginEntity != null) {
            loginQrRequest.setCustomUrl(loginEntity.getUrl());
            loginQrRequest.addParam("qr_session_id", loginEntity.getSessionID());
        } else {
            loginQrRequest.addParam("qr_session_id", AppPreferences.getCustomerQrSession());
        }
        loginQrRequest.addParam("plaform_id", "3");
        if (Utils.validateString(mDeviceToken)) {
            loginQrRequest.addParam("new_token_id", mDeviceToken);
        }
        loginQrRequest.request();
    }

    protected void goToHome() {
        registerNotificationReceiver();
        requestListStoreViews();
        AppManager.getInstance().enableDrawer();
        AppManager.getInstance().initHeader();
        AppManager.getInstance().getManager().popBackStackImmediate();

        NotificationEntity notificationEntity = AppManager.getInstance().getNotificationEntity();
        if(notificationEntity != null) {
            HashMap<String,Object> hmData = new HashMap<String, Object>();
            hmData.put("order_id", notificationEntity.getOrderID());
            AppManager.getInstance().openOrderDetail(hmData);
        } else {
            AppManager.getInstance().navigateFirstFragment();
        }
        AppManager.getInstance().getMenuTopController().showMenuTop(true);
    }

    protected void requestListStoreViews() {
        GetStoreRequest getStoreRequest = new GetStoreRequest();
        getStoreRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if (collection != null) {
                    if (collection.containKey("stores")) {
                        ArrayList<StoreViewEntity> listStores = (ArrayList<StoreViewEntity>) collection.getDataWithKey("stores");
                        AppManager.getInstance().getMenuTopController().setListStoreViews(listStores);
                    }
                }
            }
        });
        getStoreRequest.setExtendUrl("simitracking/rest/v2/stores");
        getStoreRequest.request();
    }

    protected void registerNotificationReceiver() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("data");
                AppData appData = bundle.getParcelable("entity");
                NotificationEntity notificationEntity = (NotificationEntity) appData.getData().get("notification_entity");
                showPopupNotification(notificationEntity);
            }
        };
        AppEvent.getInstance().registerEvent("com.simitracking.notification", receiver);
    }

    protected void showPopupNotification(final NotificationEntity entity) {
        AppManager.getInstance().getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AppManager.getInstance().getCurrentActivity());
                String title = entity.getTitle();
                if (Utils.validateString(title)) {
                    alertDialogBuilder.setTitle(title);
                }
                String content = entity.getContent();
                if (Utils.validateString(content)) {
                    alertDialogBuilder.setMessage(entity.getContent());
                }
                alertDialogBuilder.setPositiveButton("VIEW DETAIL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                HashMap<String,Object> hmData = new HashMap<String, Object>();
                                hmData.put("order_id", entity.getOrderID());
                                AppManager.getInstance().openOrderDetail(hmData);
                            }
                        });

                alertDialogBuilder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void setDelegate(LoginDelegate delegate) {
        mDelegate = delegate;
    }

    public View.OnClickListener getOnTryDemoClick() {
        return onTryDemoClick;
    }

    public View.OnClickListener getOnLoginClick() {
        return onLoginClick;
    }

    public View.OnClickListener getOnLoginQrClick() {
        return onLoginQrClick;
    }

    public View.OnClickListener getOnPrivacyPolicyClick() {
        return onPrivacyPolicyClick;
    }
}
