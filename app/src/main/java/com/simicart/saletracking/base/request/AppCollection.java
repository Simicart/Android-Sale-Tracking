package com.simicart.saletracking.base.request;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Glenn on 11/26/2016.
 */

public class AppCollection {

    protected JSONObject mJSONObject;
    protected HashMap<String,Object> hmData;

    public AppCollection() {
        hmData = new HashMap<>();
    }

    public HashMap<String, Object> getData() {
        return hmData;
    }

    public void setData(HashMap<String, Object> hmData) {
        this.hmData = hmData;
    }

    public JSONObject getJSONObject() {
        return mJSONObject;
    }

    public void setJSONObject(JSONObject JSONObject) {
        mJSONObject = JSONObject;
    }

    public Object getDataWithKey(String key) {
        if(hmData.containsKey(key)) {
            return hmData.get(key);
        }
        return null;
    }

    public void putDataWithKey(String key, Object value) {
        hmData.put(key, value);
    }

}
