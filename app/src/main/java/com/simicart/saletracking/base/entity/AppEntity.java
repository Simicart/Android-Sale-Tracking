package com.simicart.saletracking.base.entity;

import com.simicart.saletracking.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 11/26/2016.
 */

public class AppEntity {

    protected JSONObject mJSON;

    public void setJSONObject(JSONObject json) {
        this.mJSON = json;
    }

    public JSONObject getJSONObject() {
        return mJSON;
    }

    public void parse(JSONObject json) {
        mJSON = json;
        this.parse();
    }

    public void parse() {

    }

    protected float parseFloat(String s) {
        if(Utils.isFloat(s)) {
            return Float.parseFloat(s);
        }
        return 0;
    }

    protected int parseInt(String s) {
        if(Utils.isInteger(s)) {
            return Integer.parseInt(s);
        }
        return 0;
    }

    public String getString(String key) {
        if (mJSON != null && mJSON.has(key)) {
            try {
                return this.mJSON.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getStringWithKey(JSONObject object, String key) {
        if (object != null && object.has(key)) {
            try {
                return object.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject getJSONObjectWithKey(JSONObject jsParent, String key) {
        if (null != jsParent) {
            if (jsParent.has(key)) {
                try {
                    JSONObject jsChild = jsParent.getJSONObject(key);
                    return jsChild;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    public JSONArray getJSONArrayWithKey(JSONObject jsParent, String key) {
        if (null != jsParent) {
            if (jsParent.has(key)) {
                try {
                    JSONArray array = jsParent.getJSONArray(key);
                    return array;
                } catch (JSONException e) {
                }
            }

        }
        return null;
    }

}
