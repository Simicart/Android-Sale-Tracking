package com.simicart.saletracking.order.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.order.request.OrderDetailRequest;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected String mOrderID;

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }

    @Override
    public void onStart() {
        requestOrderDetail();
    }

    @Override
    public void onResume() {

    }

    protected void requestOrderDetail() {
        mDelegate.showLoading();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        orderDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        orderDetailRequest.setExtendUrl("simitracking/rest/v2/orders/" + mOrderID);
        orderDetailRequest.request();
    }

}
