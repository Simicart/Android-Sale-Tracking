package com.simicart.saletracking.login.request;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.common.user.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 11/26/2016.
 */

public class LoginRequest extends AppRequest {

    @Override
    protected void parseData() {
        try {
            if (mJSONResult.has("staff")) {
                JSONObject staffObj = mJSONResult.getJSONObject("staff");
                UserEntity userEntity = new UserEntity();
                userEntity.parse(staffObj);
                AppManager.getInstance().setCurrentUser(userEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
