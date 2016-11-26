package com.simicart.saletracking.base.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.simicart.saletracking.base.manager.AppManager;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppRequestController {

    private RequestQueue mRequestQueue;

    private static AppRequestController instance;

    public static AppRequestController getInstance() {
        if(instance == null) {
            instance = new AppRequestController();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(AppManager.getInstance().getCurrentActivity());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.e("Request", req.getUrl());
        getRequestQueue().add(req);
    }

}
