package com.simicart.saletracking.base.delegate;

import com.simicart.saletracking.base.request.AppCollection;

/**
 * Created by Glenn on 11/24/2016.
 */

public interface AppDelegate {

    public void showLoading();

    public void dismissLoading();

    public void showDialogLoading();

    public void dismissDialogLoading();

    public void updateView(AppCollection collection);

}
