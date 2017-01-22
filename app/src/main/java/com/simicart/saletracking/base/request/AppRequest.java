package com.simicart.saletracking.base.request;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.common.AppLogging;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
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

    protected String mCustomUrl;
    protected HashMap<String, String> hmParams;
    protected JSONObject mJSONParams;
    protected String mExtendUrl;
    protected RequestSuccessCallback mRequestSuccessCallback;
    protected RequestFailCallback mRequestFailCallback;
    protected JSONObject mJSONResult;
    protected AppCollection mCollection;
    protected int mRequestMethod = Request.Method.GET;

    public AppRequest() {
        hmParams = new HashMap<>();
        mJSONParams = new JSONObject();
        try {
            if (AppManager.getInstance().isDemo()) {
                hmParams.put("email", Constants.demoEmail);
                hmParams.put("password", Constants.demoPassword);

                mJSONParams.put("email", Constants.demoEmail);
                mJSONParams.put("password", Constants.demoPassword);
            } else if (AppPreferences.isSignInNormal()) {
                hmParams.put("email", AppPreferences.getCustomerEmail());
                hmParams.put("password", AppPreferences.getCustomerPassword());

                mJSONParams.put("email", Constants.demoEmail);
                mJSONParams.put("password", Constants.demoPassword);
            }
            String sessionID = AppManager.getInstance().getSessionID();
            if (Utils.validateString(sessionID)) {
                hmParams.put("session_id", sessionID);

                mJSONParams.put("session_id", sessionID);
            }
            hmParams.put("store_id", AppManager.getInstance().getStoreID());

            mJSONParams.put("store_id", AppManager.getInstance().getStoreID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void request() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mRequestMethod, getUrl(), getJSONParams(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppLogging.logData("Response", response.toString());
                try {
                    if (response != null) {
                        mJSONResult = response;
                        if (mJSONResult.has("errors")) {
                            JSONArray array = mJSONResult.getJSONArray("errors");
                            if (null != array && array.length() > 0) {
                                JSONObject json = array.getJSONObject(0);
                                String error = json.getString("message");
                                if (Utils.validateString(error)) {
                                    if (mRequestFailCallback != null) {
                                        mRequestFailCallback.onFail(error);
                                    } else {
                                        AppNotify.getInstance().showError(error);
                                    }
                                }
                            }
                        } else {
                            new ParseAsync().execute();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if (Utils.validateString(errorMessage)) {
                    AppLogging.logData("Error", errorMessage);
                    if (errorMessage.contains("Exception")) {
                        errorMessage = "Some error occur. Please try again later!";
                    }
                    if (mRequestFailCallback != null) {
                        mRequestFailCallback.onFail(errorMessage);
                    } else {
                        AppNotify.getInstance().showError(errorMessage);
                    }
                } else {
                    AppNotify.getInstance().showError("Some error occur. Please try again later!");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        AppRequestController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private class ParseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            parseData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mRequestSuccessCallback != null) {
                mRequestSuccessCallback.onSuccess(mCollection);
            }
        }
    }

    protected void parseData() {

    }

    private String getUrl() {
        String mBaseUrl = "";
        if (AppManager.getInstance().isDemo()) {
            mBaseUrl = Constants.demoUrl;
        } else {
            mBaseUrl = AppPreferences.getCustomerUrl();
        }

        String url = null;
        if (mCustomUrl == null) {
            url = mBaseUrl;
        } else {
            url = mCustomUrl;
        }

        if (url.charAt(url.length() - 1) != '/') {
            url += "/";
        }

        if (mExtendUrl != null) {
            url = url + mExtendUrl;
        }

        if (!url.contains("http")) {
            url = "https://" + url;
        }

        if (mRequestMethod == Request.Method.GET) {
            String dataParameter = processDataParameter();
            if (Utils.validateString(dataParameter)) {
                url = url + "?" + dataParameter;
            }
        }

        return url;
    }

    protected JSONObject getJSONParams() {
        if(mRequestMethod == Request.Method.POST || mRequestMethod == Request.Method.PUT) {
            return mJSONParams;
        }
        return null;
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

    public void addParamBody(String key, String value) {
        try {
            mJSONParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addFilterParam(String key, String value) {
        if (Utils.validateString(key) && Utils.validateString(value)) {
            String key_filter = "filter[" + key + "]";
            addParam(key_filter, value);
        }
    }

    public void addSearchParam(String key, String value) {
        if (Utils.validateString(key) && Utils.validateString(value)) {
            String key_filter = "filter[" + key + "][like]";
            addParam(key_filter, value);
        }
    }

    public void addSortParam(String key) {
        if (Utils.validateString(key)) {
            addParam("order", key);
        }
    }

    public void addSortDirASCParam() {
        addParam("dir", "asc");
    }

    public void addSortDirDESCParam() {
        addParam("dir", "desc");
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

    public String getCustomUrl() {
        return mCustomUrl;
    }

    public void setCustomUrl(String customUrl) {
        mCustomUrl = customUrl;
    }

    public int getRequestMethod() {
        return mRequestMethod;
    }

    public void setRequestMethod(int requestMethod) {
        mRequestMethod = requestMethod;
    }
}
