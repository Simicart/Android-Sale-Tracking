package com.simicart.saletracking.login.controller;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.base.request.AppRequestController;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Config;
import com.simicart.saletracking.login.delegate.LoginDelegate;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginController extends AppController {

    protected LoginDelegate mDelegate;
    protected View.OnClickListener onTryDemoClick;

    @Override
    public void onStart() {

        onTryDemoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.getInstance().setDemo(true);
                onLoginDemo();
            }
        };

    }

    @Override
    public void onResume() {

    }

    protected void onLoginDemo() {
        AppRequest loginDemoRequest = new AppRequest();
        loginDemoRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("abc", response.toString());
            }
        });
        loginDemoRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                Log.e("abc", message);
            }
        });
        loginDemoRequest.setExtendUrl("staffs/login");
        loginDemoRequest.setParam("email", "test@simicart.com");
        loginDemoRequest.setParam("password", "123456");
        loginDemoRequest.request();
    }

    public void setDelegate(LoginDelegate delegate) {
        mDelegate = delegate;
    }

    public View.OnClickListener getOnTryDemoClick() {
        return onTryDemoClick;
    }

}
