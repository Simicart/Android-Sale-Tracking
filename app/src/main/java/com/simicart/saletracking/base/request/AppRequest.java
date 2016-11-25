package com.simicart.saletracking.base.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.simicart.saletracking.common.Config;
import com.simicart.saletracking.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Glenn on 11/25/2016.
 */

public class AppRequest {

    protected HashMap<String, String> hmParams;
    protected String mExtendUrl;
    protected RequestSuccessCallback mRequestSuccessCallback;
    protected RequestFailCallback mRequestFailCallback;

    public AppRequest() {
        hmParams = new HashMap<>();
    }

    public void request() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resultObj = new JSONObject(response);
                            mRequestSuccessCallback.onSuccess(resultObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if(Utils.validateString(errorMessage)) {
                    mRequestFailCallback.onFail(errorMessage);
                }
            }
        });
        AppRequestController.getInstance().addToRequestQueue(stringRequest);
    }

    public String getUrl() {
        String mBaseUrl = "";
        if (Config.getInstance().isDemo()) {
            mBaseUrl = Config.getInstance().getDemoUrl();
        }

        String url = mBaseUrl + mExtendUrl;

        String dataParameter = processDataParameter();
        if (Utils.validateString(dataParameter)) {
            url = url + "?" + dataParameter;
        }

        return url;
    }

    protected String processDataParameter() {
        if (null != hmParams && hmParams.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = hmParams.entrySet()
                    .iterator();
            boolean isFirst = true;
            StringBuilder builder = new StringBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String parameter = processKeyValue(entry);
                if (Utils.validateString(parameter)) {
                    if (isFirst) {
                        isFirst = false;
                        builder.append(parameter);
                    } else {
                        builder.append("&");
                        builder.append(parameter);
                    }
                }
            }
            return builder.toString();
        }
        return "";
    }

    protected String processKeyValue(Map.Entry<String, String> entry) {
        String param = "";
        String key = entry.getKey();
        String value = entry.getValue();
        if (Utils.validateString(key) && Utils.validateString(value)) {
            param = key + "=" + value;
        }
        return param;
    }

    public void setParam(String key, String value) {
        hmParams.put(key, value);
    }

    public void setExtendUrl(String extendUrl) {
        mExtendUrl = extendUrl;
    }

    public void setRequestFailCallback(RequestFailCallback requestFailCallback) {
        mRequestFailCallback = requestFailCallback;
    }

    public void setRequestSuccessCallback(RequestSuccessCallback requestSuccessCallback) {
        mRequestSuccessCallback = requestSuccessCallback;
    }
}
