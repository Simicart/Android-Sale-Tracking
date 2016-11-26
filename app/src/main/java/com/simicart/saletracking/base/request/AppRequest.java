package com.simicart.saletracking.base.request;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.common.Config;
import com.simicart.saletracking.common.Utils;

import org.json.JSONArray;
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
    protected JSONObject mJSONResult;
    protected AppCollection mCollection;

    public AppRequest() {
        hmParams = new HashMap<>();
    }

    public void request() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(Utils.validateString(response)) {
                                Log.e("Response", response);
                                mJSONResult = new JSONObject(response);
                                new ParseAsync().execute();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if(Utils.validateString(errorMessage)) {
                    if(mRequestFailCallback != null) {
                        mRequestFailCallback.onFail(errorMessage);
                    } else {
                        AppNotify.getInstance().showError(errorMessage);
                    }
                }
            }
        });
        AppRequestController.getInstance().addToRequestQueue(stringRequest);
    }

    private class ParseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            parseData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mRequestSuccessCallback != null) {
                mRequestSuccessCallback.onSuccess(mCollection);
            }
        }
    }

    protected void parseData() {
        if (mJSONResult.has("errors")) {
            try {
                JSONArray array = mJSONResult.getJSONArray("errors");
                if (null != array && array.length() > 0) {
                    JSONObject json = array.getJSONObject(0);
                    String error = json.getString("message");
                    if(Utils.validateString(error)) {
                        AppNotify.getInstance().showError(error);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getUrl() {
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

    private String processDataParameter() {
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

    private String processKeyValue(Map.Entry<String, String> entry) {
        String param = "";
        String key = entry.getKey();
        String value = entry.getValue();
        if (Utils.validateString(key) && Utils.validateString(value)) {
            param = key + "=" + value;
        }
        return param;
    }

    public void addParam(String key, String value) {
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

    public AppCollection getCollection() {
        return mCollection;
    }
}
