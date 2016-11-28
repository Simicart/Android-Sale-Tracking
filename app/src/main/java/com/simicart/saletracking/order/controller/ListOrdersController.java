package com.simicart.saletracking.order.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.order.delegate.ListOrdersDelegate;
import com.simicart.saletracking.order.request.ListOrdersRequest;

/**
 * Created by Glenn on 11/28/2016.
 */

public class ListOrdersController extends AppController {

    protected ListOrdersDelegate mDelegate;

    public void setDelegate(ListOrdersDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        requestListOrders();
    }

    @Override
    public void onResume() {

    }

    protected void requestListOrders() {
        mDelegate.showLoading();
        ListOrdersRequest listOrdersRequest = new ListOrdersRequest();
        listOrdersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        listOrdersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        listOrdersRequest.setExtendUrl("orders");
        listOrdersRequest.addParam("dir", "desc");
        listOrdersRequest.addParam("limit", "30");
        listOrdersRequest.addParam("offset", "0");
        listOrdersRequest.request();
    }

}
