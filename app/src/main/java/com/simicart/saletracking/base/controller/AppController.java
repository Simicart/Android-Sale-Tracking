package com.simicart.saletracking.base.controller;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/24/2016.
 */

public abstract class AppController {

    protected JSONObject objectResult;

    public abstract  void onStart();

    public abstract void onResume();

}
