package com.simicart.saletracking.login.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/10/2016.
 */

public class CheckLicenseActiveRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("license")) {
                JSONObject licenseObj = mJSONResult.getJSONObject("license");
                if(licenseObj.has("status")) {
                    String status = licenseObj.getString("status");
                    boolean isActive = Utils.getBoolean(status);
                    if(isActive) {
                        mCollection.putDataWithKey("is_active", isActive);
                    } else {
                        if(licenseObj.has("message")) {
                            String message = licenseObj.getString("message");
                            mCollection.putDataWithKey("message", message);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
