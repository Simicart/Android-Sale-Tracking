package com.simicart.saletracking.dashboard.controller;

import android.view.View;
import android.widget.AdapterView;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.customer.request.ListCustomersRequest;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.dashboard.request.ListSalesRequest;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;
import com.simicart.saletracking.order.request.ListOrdersRequest;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardController extends AppController {

    protected DashboardDelegate mDelegate;
    protected AdapterView.OnItemSelectedListener mOnTimeSelected;
    protected TimeLayerEntity mTimeLayerEntity;
    protected int mTotalStep = 0;
    protected int mCurrentStep = 0;
    protected boolean isFirstRun = true;
    protected boolean isReloadChart = false;

    @Override
    public void onStart() {

        mTimeLayerEntity = mDelegate.getListTimeLayers().get(0);

        mDelegate.showLoading();

        if (AppPreferences.getShowSaleReport()) {
            mTotalStep++;
            requestSales();
        }

        if (AppPreferences.getShowLatestOrder()) {
            mTotalStep++;
            requestLatestOrders();
        }

        if (AppPreferences.getShowLatestCustomer()) {
            mTotalStep++;
            requestLatestCustomers();
        }

        if (mTotalStep == 0) {
            mDelegate.dismissLoading();
        }

        initListener();

    }

    @Override
    public void onResume() {

    }

    protected void requestSales() {
        ListSalesRequest listSalesRequest = new ListSalesRequest();
        listSalesRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                showChart(collection);
                checkStep();
                if(isReloadChart) {
                    mDelegate.dismissDialogLoading();
                    isReloadChart = false;
                }
            }
        });
        listSalesRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                checkStep();
                AppNotify.getInstance().showError(message);
            }
        });
        listSalesRequest.setExtendUrl("simitracking/rest/v2/sales/saleinfo");
        listSalesRequest.addParam("dir", "desc");
        if (mTimeLayerEntity != null) {
            listSalesRequest.addParam(mTimeLayerEntity.getFromDateKey(), mTimeLayerEntity.getFromDate());
            listSalesRequest.addParam(mTimeLayerEntity.getToDateKey(), mTimeLayerEntity.getToDate());
            listSalesRequest.addParam("period", mTimeLayerEntity.getPeriod());
        }
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
        listOrdersRequest.setExtendUrl("simitracking/rest/v2/orders");
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
        listCustomersRequest.setExtendUrl("simitracking/rest/v2/customers");
        listCustomersRequest.addParam("dir", "desc");
        listCustomersRequest.addParam("limit", "5");
        listCustomersRequest.addParam("offset", "0");
        listCustomersRequest.request();
    }

    protected void checkStep() {
        mCurrentStep++;
        if (mCurrentStep == mTotalStep) {
            mDelegate.dismissLoading();
        }
    }

    protected void initListener() {
        mOnTimeSelected = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isFirstRun) {
                    isReloadChart = true;
                    mDelegate.showDialogLoading();
                    mTimeLayerEntity = mDelegate.getListTimeLayers().get(i);
                    requestSales();
                } else {
                    isFirstRun = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    protected void showChart(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("sale")) {
                SaleEntity saleEntity = (SaleEntity) collection.getDataWithKey("sale");
                if (saleEntity != null) {
                    ChartComponent chartComponent = new ChartComponent();
                    chartComponent.setSaleEntity(saleEntity);
                    chartComponent.setTimeLayerEntity(mTimeLayerEntity);
                    View chartView = chartComponent.createView();
                    mDelegate.showChart(chartView);
                    mDelegate.showTotal(saleEntity);
                }
            }
        }
    }

    public void setDelegate(DashboardDelegate delegate) {
        mDelegate = delegate;
    }

    public AdapterView.OnItemSelectedListener getOnTimeSelected() {
        return mOnTimeSelected;
    }
}
