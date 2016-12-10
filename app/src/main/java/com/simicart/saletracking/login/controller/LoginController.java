package com.simicart.saletracking.login.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.login.delegate.LoginDelegate;
import com.simicart.saletracking.login.entity.LoginEntity;
import com.simicart.saletracking.login.request.CheckLicenseActiveRequest;
import com.simicart.saletracking.login.request.LoginRequest;
import com.simicart.saletracking.store.entity.StoreViewEntity;
import com.simicart.saletracking.store.request.GetStoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginController extends AppController {

    protected LoginDelegate mDelegate;
    protected View.OnClickListener onTryDemoClick;
    protected View.OnClickListener onLoginClick;
    protected View.OnClickListener onLoginQrClick;
    protected String mDeviceID;

    @Override
    public void onStart() {

        mDeviceID = Settings.Secure.getString(AppManager.getInstance().getCurrentActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

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
                    AppPreferences.saveCustomerInfo(loginEntity.getUrl(), loginEntity.getEmail(), loginEntity.getPassword());
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

        if(AppPreferences.isSignInNormal()) {
            checkLicenseActive(null, true);
        } else if(AppPreferences.isSignInQr()) {
            checkLicenseActive(null, false);
        }

    }

    @Override
    public void onResume() {

    }

    protected void checkLicenseActive(final LoginEntity loginEntity, final boolean isLoginNormal) {
        mDelegate.showDialogLoading();
        CheckLicenseActiveRequest licenseActiveRequest = new CheckLicenseActiveRequest();
        licenseActiveRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if(collection != null) {
                    if(collection.containKey("is_active")) {
                        boolean isActive = (boolean) collection.getDataWithKey("is_active");
                        if(isActive) {
                            if(isLoginNormal) {
                                onLoginUser(loginEntity);
                            } else {
                                onLoginQr(loginEntity);
                            }
                        } else {
                            mDelegate.dismissDialogLoading();
                            if(collection.containKey("message")) {
                                String message = (String) collection.getDataWithKey("message");
                                if(Utils.validateString(message)) {
                                    AppNotify.getInstance().showError(message);
                                }
                            }
                        }
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
        if(loginEntity != null) {
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
        loginDemoRequest.addParam("email", "test@simicart.com");
        loginDemoRequest.addParam("password", "123456");
        loginDemoRequest.addParam("platform", "3");
        if (Utils.validateString(mDeviceID)) {
            loginDemoRequest.addParam("device_token", "nontoken_" + mDeviceID);
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
                goToHome();
            }
        });
        loginUserRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
                AppPreferences.clearCustomerInfo();
            }
        });
        loginUserRequest.setExtendUrl("simitracking/rest/v2/staffs/login");
        if (loginEntity != null) {
            loginUserRequest.addParam("url", loginEntity.getUrl());
            loginUserRequest.addParam("email", loginEntity.getEmail());
            loginUserRequest.addParam("password", loginEntity.getPassword());
        } else {
            loginUserRequest.addParam("url", AppPreferences.getCustomerUrl());
            loginUserRequest.addParam("email", AppPreferences.getCustomerEmail());
            loginUserRequest.addParam("password", AppPreferences.getCustomerPassword());
        }
        loginUserRequest.addParam("platform", "3");
        if (Utils.validateString(mDeviceID)) {
            loginUserRequest.addParam("device_token", "nontoken_" + mDeviceID);
        }
        loginUserRequest.request();
    }

    protected void onQrCodeClick() {
        new IntentIntegrator(AppManager.getInstance().getCurrentActivity()).initiateScan();

        BroadcastReceiver onScanResultReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getBundleExtra("data");
                AppData mData =  bundle.getParcelable("entity");
                int requestCode = (int) mData.getData().get("request_code");
                int resultCode = (int) mData.getData().get("result_code");
                Intent data = (Intent) mData.getData().get("intent");

                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    if (intentResult.getContents() != null) {
                        String result = intentResult.getContents();
                        if(Utils.validateString(result)) {
                            try {
                                byte[] decodedBytes = Base64.decode(result, Base64.DEFAULT);
                                final String decodedResult = new String(decodedBytes, "UTF-8");
                                parseDecodedQrCode(decodedResult);
                            } catch (UnsupportedEncodingException e) {
                                Log.e("Decode QR Result", e.getMessage());
                            }
                        }
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(AppManager.getInstance().getCurrentActivity())
                .registerReceiver(onScanResultReceiver, new IntentFilter("login.qrcode"));
    }

    protected void parseDecodedQrCode(String qrCode) {
        try {
            JSONObject qrObj = new JSONObject(qrCode);
            LoginEntity loginEntity = new LoginEntity();
            if(qrObj.has("user_email")) {
                loginEntity.setEmail(qrObj.getString("user_email"));
            }
            if(qrObj.has("url")) {
                loginEntity.setUrl(qrObj.getString("url"));
            }
            if(qrObj.has("session_id")) {
                loginEntity.setSessionID(qrObj.getString("session_id"));
            }
            AppPreferences.saveCustomerInfoForQr(loginEntity.getUrl(), loginEntity.getEmail(), loginEntity.getSessionID());
            checkLicenseActive(loginEntity, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onLoginQr(LoginEntity loginEntity) {
        LoginRequest loginQrRequest = new LoginRequest();
        loginQrRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();
                AppPreferences.setSignInQr(true);
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
        if(loginEntity != null) {
            loginQrRequest.addParam("qr_session_id", loginEntity.getSessionID());
        } else {
            loginQrRequest.addParam("qr_session_id", AppPreferences.getCustomerQrSession());
        }
        loginQrRequest.addParam("platform", "3");
        if (Utils.validateString(mDeviceID)) {
            loginQrRequest.addParam("new_token_id", "nontoken_" + mDeviceID);
        }
        loginQrRequest.request();
    }

    protected void goToHome() {
        if(AppManager.getInstance().getMenuTopController().getListStoreViews() == null) {
            requestListStoreViews();
        }
        AppManager.getInstance().enableDrawer();
        AppManager.getInstance().initHeader();
        AppManager.getInstance().getManager().popBackStackImmediate();
        AppManager.getInstance().navigateFirstFragment();
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

}
