package com.simicart.saletracking.dashboard.controller;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.customer.request.ListCustomersRequest;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.request.ListSalesRequest;
import com.simicart.saletracking.order.request.ListOrdersRequest;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardController extends AppController {

    protected DashboardDelegate mDelegate;

    @Override
    public void onStart() {
        requestSales();
        requestLatestOrders();
        requestLatestCustomers();
    }

    @Override
    public void onResume() {

    }

    protected void requestSales() {
        mDelegate.showLoading();
        ListSalesRequest listSalesRequest = new ListSalesRequest();
        listSalesRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mDelegate.showChart(collection);
            }
        });
        listSalesRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
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
            }
        });
        listOrdersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {

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
            }
        });
        listCustomersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {

            }
        });
        listCustomersRequest.setExtendUrl("customers");
        listCustomersRequest.addParam("dir", "desc");
        listCustomersRequest.addParam("limit", "5");
        listCustomersRequest.addParam("offset", "0");
        listCustomersRequest.request();
    }

    public void setDelegate(DashboardDelegate delegate) {
        mDelegate = delegate;
    }

}
