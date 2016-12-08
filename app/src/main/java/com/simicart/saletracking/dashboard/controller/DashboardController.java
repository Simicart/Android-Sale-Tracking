package com.simicart.saletracking.dashboard.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.customer.request.ListCustomersRequest;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.request.ListSalesRequest;
import com.simicart.saletracking.order.request.ListOrdersRequest;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardController extends AppController {

    protected DashboardDelegate mDelegate;
    protected int mTotalStep = 0;
    protected int mCurrentStep = 0;

    @Override
    public void onStart() {

        mDelegate.showLoading();

        if(AppPreferences.getShowSaleReport()) {
            mTotalStep++;
            requestSales();
        }

        if(AppPreferences.getShowLatestOrder()) {
            mTotalStep++;
            requestLatestOrders();
        }

        if(AppPreferences.getShowLatestCustomer()) {
            mTotalStep++;
            requestLatestCustomers();
        }

        if(mTotalStep == 0) {
            mDelegate.dismissLoading();
        }

    }

    @Override
    public void onResume() {

    }

    protected void requestSales() {
        ListSalesRequest listSalesRequest = new ListSalesRequest();
        listSalesRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.showChart(collection);
                checkStep();
            }
        });
        listSalesRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                checkStep();
                AppNotify.getInstance().showError(message);
            }
        });
        listSalesRequest.setExtendUrl("sales/saleinfo");
        listSalesRequest.addParam("dir", "desc");
        listSalesRequest.addParam("from_date", "2016-11-01");
        listSalesRequest.addParam("to_date", "2016-11-30");
        listSalesRequest.addParam("period", "day");
        listSalesRequest.request();
    }

    protected void requestLatestOrders() {
        ListOrdersRequest listOrdersRequest = new ListOrdersRequest();
        listOrdersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.showLatestOrders(collection);
                checkStep();
            }
        });
        listOrdersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                checkStep();
                AppNotify.getInstance().showError(message);
            }
        });
        listOrdersRequest.setExtendUrl("orders");
        listOrdersRequest.addParam("dir", "desc");
        listOrdersRequest.addParam("limit", "5");
        listOrdersRequest.addParam("offset", "0");
        listOrdersRequest.request();
    }

    protected void requestLatestCustomers() {
        ListCustomersRequest listCustomersRequest = new ListCustomersRequest();
        listCustomersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.showLatestCustomers(collection);
                checkStep();
            }
        });
        listCustomersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                checkStep();
                AppNotify.getInstance().showError(message);
            }
        });
        listCustomersRequest.setExtendUrl("customers");
        listCustomersRequest.addParam("dir", "desc");
        listCustomersRequest.addParam("limit", "5");
        listCustomersRequest.addParam("offset", "0");
        listCustomersRequest.request();
    }

    protected void checkStep() {
        mCurrentStep++;
        if(mCurrentStep == mTotalStep) {
            mDelegate.dismissLoading();
        }
    }

    public void setDelegate(DashboardDelegate delegate) {
        mDelegate = delegate;
    }

}
