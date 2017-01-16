package com.simicart.saletracking.forecast.controller;

import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.entity.ChartEntity;
import com.simicart.saletracking.forecast.delegate.ForecastDelegate;
import com.simicart.saletracking.forecast.request.ForecastRequest;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastController extends AppController {

    protected ForecastDelegate mDelegate;
    protected ForecastRequest forecastRequest;
    protected TimeLayerEntity mTimeLayerEntity;

    @Override
    public void onStart() {
        mTimeLayerEntity = mDelegate.getListTimeLayers().get(0);

        requestForecast();
    }

    @Override
    public void onResume() {
        showChart(forecastRequest.getCollection());
    }

    protected void requestForecast() {
        mDelegate.showLoading();
        forecastRequest = new ForecastRequest();
        forecastRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                showChart(collection);
            }
        });
        forecastRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        forecastRequest.setExtendUrl("simitracking/rest/v2/salesforecasts/day");
        forecastRequest.request();
    }

    protected void showChart(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("salesforecast")) {
                ArrayList<ChartEntity> chartEntities = (ArrayList<ChartEntity>) collection.getDataWithKey("salesforecast");
                
            }
        }
    }

    public void setDelegate(ForecastDelegate delegate) {
        mDelegate = delegate;
    }

}
