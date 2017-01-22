package com.simicart.saletracking.login.request;

import android.util.Log;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.common.AppLogging;
import com.simicart.saletracking.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                if (licenseObj.has("status")) {
                    String status = licenseObj.getString("status");
                    boolean isActive = Utils.getBoolean(status);
                    mCollection.putDataWithKey("is_active", isActive);
                    if (licenseObj.has("message")) {
                        String message = licenseObj.getString("message");
                        mCollection.putDataWithKey("message", message);
                    }
                }
                if(licenseObj.has("expired_time")) {
                    String expiredTime = licenseObj.getString("expired_time");
                    boolean isExpired = false;
                    if(Utils.validateString(expiredTime)) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentDate = sdf.parse(sdf.format(calendar.getTime()));
                        Date expiredDate = sdf.parse(expiredTime);
                        if(currentDate.compareTo(expiredDate) >= 0) {
                            isExpired = true;
                        }
                    }
                    mCollection.putDataWithKey("expired_time", isExpired);
                }
                if(licenseObj.has("version")) {
                    String version = licenseObj.getString("version");
                    mCollection.putDataWithKey("version", version);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
