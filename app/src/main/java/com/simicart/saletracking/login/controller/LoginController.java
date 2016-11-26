package com.simicart.saletracking.login.controller;

import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Config;
import com.simicart.saletracking.login.delegate.LoginDelegate;
import com.simicart.saletracking.login.entity.LoginEntity;
import com.simicart.saletracking.login.request.LoginRequest;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginController extends AppController {

    protected LoginDelegate mDelegate;
    protected View.OnClickListener onTryDemoClick;
    protected View.OnClickListener onLoginClick;

    @Override
    public void onStart() {

        onTryDemoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.getInstance().setDemo(true);
                onLoginDemo();
            }
        };

        onLoginClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEntity loginEntity = mDelegate.getLoginInfo();
                if(loginEntity != null) {
                    onLoginUser(loginEntity);
                }
            }
        };

    }

    @Override
    public void onResume() {

    }

    protected void onLoginDemo() {
        LoginRequest loginDemoRequest = new LoginRequest();
        loginDemoRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                goToHome();
            }
        });
        loginDemoRequest.setExtendUrl("staffs/login");
        loginDemoRequest.addParam("email", "test@simicart.com");
        loginDemoRequest.addParam("password", "123456");
        loginDemoRequest.request();
    }

    protected void onLoginUser(LoginEntity loginEntity) {
        LoginRequest loginUserRequest = new LoginRequest();
        loginUserRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                goToHome();
            }
        });
        loginUserRequest.setExtendUrl("staffs/login");
        loginUserRequest.addParam("url", loginEntity.getUrl());
        loginUserRequest.addParam("email", loginEntity.getEmail());
        loginUserRequest.addParam("password", loginEntity.getPassword());
        loginUserRequest.request();
    }

    protected void goToHome() {
        AppManager.getInstance().getManager().popBackStack();
        AppManager.getInstance().openOrderPage();
        AppManager.getInstance().getMenuTopController().showMenuTop(true);
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
