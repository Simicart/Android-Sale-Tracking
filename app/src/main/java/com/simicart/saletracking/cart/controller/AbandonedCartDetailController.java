package com.simicart.saletracking.cart.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.cart.request.AbandonedCartDetailRequest;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected String mAbandonedCartID;

    @Override
    public void onStart() {
        requestAbandonedCart();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestAbandonedCart() {
        mDelegate.showLoading();
        AbandonedCartDetailRequest abandonedCartDetailRequest = new AbandonedCartDetailRequest();
        abandonedCartDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        abandonedCartDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        abandonedCartDetailRequest.setExtendUrl("simitracking/rest/v2/abandonedcarts/" + mAbandonedCartID);
        abandonedCartDetailRequest.request();
    }

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setAbandonedCartID(String abandonedCartID) {
        mAbandonedCartID = abandonedCartID;
    }
}
