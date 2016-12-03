package com.simicart.saletracking.customer.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.customer.request.ListAddressesRequest;

/**
 * Created by Glenn on 12/3/2016.
 */

public class ListAddressesController extends AppController {

    protected AppDelegate mDelegate;
    protected String mCustomerID;

    @Override
    public void onStart() {
        requestListAddresses();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestListAddresses() {
        mDelegate.showLoading();
        ListAddressesRequest listAddressesRequest = new ListAddressesRequest();
        listAddressesRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        listAddressesRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        listAddressesRequest.setExtendUrl("addresses");
        listAddressesRequest.addParam("customer_id", mCustomerID);
        listAddressesRequest.request();
    }

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setCustomerID(String customerID) {
        mCustomerID = customerID;
    }
}
