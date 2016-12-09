package com.simicart.saletracking.login.controller;

import android.provider.Settings;
import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.login.delegate.LoginDelegate;
import com.simicart.saletracking.login.entity.LoginEntity;
import com.simicart.saletracking.login.request.LoginRequest;
import com.simicart.saletracking.store.entity.StoreViewEntity;
import com.simicart.saletracking.store.request.GetStoreRequest;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginController extends AppController {

    protected LoginDelegate mDelegate;
    protected View.OnClickListener onTryDemoClick;
    protected View.OnClickListener onLoginClick;
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
                    onLoginUser(loginEntity);
                }
            }
        };

    }

    @Override
    public void onResume() {

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
        loginDemoRequest.setExtendUrl("staffs/login");
        loginDemoRequest.addParam("email", "test@simicart.com");
        loginDemoRequest.addParam("password", "123456");
        loginDemoRequest.addParam("platform", "3");
        if (Utils.validateString(mDeviceID)) {
            loginDemoRequest.addParam("device_token", "nontoken_" + mDeviceID);
        }
        loginDemoRequest.request();
    }

    protected void onLoginUser(final LoginEntity loginEntity) {
        mDelegate.showDialogLoading();
        LoginRequest loginUserRequest = new LoginRequest();
        loginUserRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                AppPreferences.setSignInComplete(true);
                AppPreferences.saveCustomerInfo(loginEntity.getUrl(), loginEntity.getEmail(), loginEntity.getPassword());
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
        loginUserRequest.setExtendUrl("staffs/login");
        loginUserRequest.addParam("url", loginEntity.getUrl());
        loginUserRequest.addParam("email", loginEntity.getEmail());
        loginUserRequest.addParam("password", loginEntity.getPassword());
        loginUserRequest.addParam("platform", "3");
        if (Utils.validateString(mDeviceID)) {
            loginUserRequest.addParam("device_token", "nontoken_" + mDeviceID);
        }
        loginUserRequest.request();
    }

    protected void goToHome() {
        requestListStoreViews();
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
        getStoreRequest.setExtendUrl("stores");
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

}
