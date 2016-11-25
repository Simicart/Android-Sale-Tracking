package com.simicart.saletracking.base.delegate;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/24/2016.
 */

public interface AppDelegate {

    public void showLoading();

    public void dismissLoading();

    public void showDialogLoading();

    public void dismissDialogLoading();

    public void updateView(JSONObject objectResult);

}
