package com.simicart.saletracking.base.controller;

import com.simicart.saletracking.base.request.AppCollection;

/**
 * Created by Glenn on 11/24/2016.
 */

public abstract class AppController {

    protected AppCollection mCollection;

    public abstract void onStart();

    public abstract void onResume();

}
