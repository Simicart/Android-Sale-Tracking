package com.simicart.saletracking.dashboard.controller;

import android.view.View;
import android.widget.AdapterView;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.bestseller.request.BestSellersRequest;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.customer.request.ListCustomersRequest;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.dashboard.request.ListSalesRequest;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;
import com.simicart.saletracking.order.request.ListOrdersRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardController extends AppController {

    protected DashboardDelegate mDelegate;
    protected AdapterView.OnItemSelectedListener mOnTimeSelected;
    protected View.OnClickListener mOnRefreshStatsClick;
    protected TimeLayerEntity mTimeLayerEntity;
    protected int mTotalStep = 0;
    protected int mCurrentStep = 0;
    protected boolean isFirstRun = true;
    protected boolean isReloadChart = false;

    protected ListSalesRequest listSalesRequest;
    protected BestSellersRequest bestSellersRequest;
    protected ListOrdersRequest listOrdersRequest;
    protected ListCustomersRequest listCustomersRequest;

    @Override
    public void onStart() {

        mTimeLayerEntity = mDelegate.getListTimeLayers().get(0);

        mDelegate.showLoading();

        if (AppPreferences.getShowSaleReport()) {
            mTotalStep++;
            requestSales("saleinfo");
        }

        if (AppPreferences.getShowBestSellers()) {
            mTotalStep++;
            requestBestSellers();
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
        isFirstRun = true;
        showChart(listSalesRequest.getCollection());
        mDelegate.showBestSellers(bestSellersRequest.getCollection());
        mDelegate.showLatestOrders(listOrdersRequest.getCollection());
        mDelegate.showLatestCustomers(listCustomersRequest.getCollection());

    }

    protected void requestSales(String extend) {
        listSalesRequest = new ListSalesRequest();
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
        listSalesRequest.setExtendUrl("simitracking/rest/v2/sales/" + extend);
        listSalesRequest.addParam("dir", "desc");
        if (mTimeLayerEntity != null) {
            listSalesRequest.addParam(mTimeLayerEntity.getFromDateKey(), mTimeLayerEntity.getFromDate());
            listSalesRequest.addParam(mTimeLayerEntity.getToDateKey(), mTimeLayerEntity.getToDate());
            listSalesRequest.addParam("period", mTimeLayerEntity.getPeriod());
        }
        listSalesRequest.request();
    }

    protected void requestBestSellers() {
        bestSellersRequest = new BestSellersRequest();
        bestSellersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.showBestSellers(collection);
                checkStep();
            }
        });
        bestSellersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                checkStep();
                AppNotify.getInstance().showError(message);
            }
        });
        bestSellersRequest.setExtendUrl("simitracking/rest/v2/bestsellers");
        bestSellersRequest.addParam("dir", "desc");
        bestSellersRequest.addParam("limit", "5");
        bestSellersRequest.addParam("offset", "0");
        bestSellersRequest.request();
    }

    protected void requestLatestOrders() {
        listOrdersRequest = new ListOrdersRequest();
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
        listCustomersRequest = new ListCustomersRequest();
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
                    mDelegate.setTimeLayer(mTimeLayerEntity);
                    requestSales("saleinfo");
                } else {
                    isFirstRun = false;
                }

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("filter_action", mTimeLayerEntity.getKey());
                    AppManager.getInstance().trackWithMixPanel("dashboard_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        mOnRefreshStatsClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReloadChart = true;
                mDelegate.showDialogLoading();
                requestSales("refresh");

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "chart_refresh");
                    AppManager.getInstance().trackWithMixPanel("dashboard_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void showChart(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("sale")) {
                SaleEntity saleEntity = (SaleEntity) collection.getDataWithKey("sale");
                if (saleEntity != null) {
                    mDelegate.showChart(saleEntity.getListTotalCharts());
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

    public View.OnClickListener getOnRefreshStatsClick() {
        return mOnRefreshStatsClick;
    }
}
