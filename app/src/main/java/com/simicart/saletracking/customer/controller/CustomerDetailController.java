package com.simicart.saletracking.customer.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.customer.request.CustomerDetailRequest;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailController extends AppController {

    protected AppDelegate mDelegate;

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        requestCustomerDetail();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestCustomerDetail() {
        mDelegate.showLoading();
        CustomerDetailRequest customerDetailRequest = new CustomerDetailRequest();
        customerDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        customerDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        customerDetailRequest.setExtendUrl("customers/24");
        customerDetailRequest.request();
    }

}
