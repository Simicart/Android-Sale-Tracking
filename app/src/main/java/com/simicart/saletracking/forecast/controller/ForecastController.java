package com.simicart.saletracking.forecast.controller;

import android.view.View;

import com.simicart.saletracking.base.component.ChooserCallback;
import com.simicart.saletracking.base.component.ChooserPopup;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.entity.ChartEntity;
import com.simicart.saletracking.forecast.delegate.ForecastDelegate;
import com.simicart.saletracking.forecast.request.ForecastRequest;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastController extends AppController {

    protected ForecastDelegate mDelegate;
    protected ForecastRequest forecastRequest;
    protected TimeLayerEntity mTimeLayerEntity;
    protected View.OnClickListener mOnChangeTimeClick;
    protected ArrayList<TimeLayerEntity> mListTimeLayers;
    protected ArrayList<ChartEntity> chartEntities;

    @Override
    public void onStart() {
        initTimeLayer();

        mOnChangeTimeClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeChooser();
            }
        };

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

    protected void initTimeLayer() {
        mListTimeLayers = new ArrayList<>();

        TimeLayerEntity oneMonth = new TimeLayerEntity();
        oneMonth.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        oneMonth.setToDate(Utils.getDate(Calendar.DATE, 30, true));
        oneMonth.setLabel("1 Month");
        oneMonth.setKey("chart_1_month");
        oneMonth.setPeriod("day");
        mListTimeLayers.add(oneMonth);

        TimeLayerEntity twoMonths = new TimeLayerEntity();
        twoMonths.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        twoMonths.setToDate(Utils.getDate(Calendar.DATE, 60, true));
        twoMonths.setLabel("2 Months");
        twoMonths.setKey("chart_2_months");
        twoMonths.setPeriod("day");
        mListTimeLayers.add(twoMonths);

        TimeLayerEntity threeMonths = new TimeLayerEntity();
        threeMonths.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        threeMonths.setToDate(Utils.getDate(Calendar.DATE, 90, true));
        threeMonths.setLabel("3 Months");
        threeMonths.setKey("chart_3_months");
        threeMonths.setPeriod("day");
        mListTimeLayers.add(threeMonths);

        mTimeLayerEntity = mListTimeLayers.get(0);
        mDelegate.setTimeFormat(mTimeLayerEntity);
    }

    protected void showChart(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("salesforecast")) {
                chartEntities = (ArrayList<ChartEntity>) collection.getDataWithKey("salesforecast");
                showChart();
            }
        }
    }

    protected void showChart() {
        mDelegate.showChart(chartEntities, mTimeLayerEntity);
    }

    protected void openTimeChooser() {
        ArrayList<String> listTimes = new ArrayList<>();
        listTimes.add("1 month");
        listTimes.add("2 months");
        listTimes.add("3 months");

        ChooserPopup chooserPopup = new ChooserPopup(listTimes, mListTimeLayers.indexOf(mTimeLayerEntity));
        chooserPopup.setTitle("Select time range to forecast");
        chooserPopup.setChooserCallback(new ChooserCallback() {
            @Override
            public void onClick(int position) {
                mTimeLayerEntity = mListTimeLayers.get(position);
                mDelegate.setTimeFormat(mTimeLayerEntity);
                showChart();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("filter_action", mTimeLayerEntity.getKey());
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("forecast_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        chooserPopup.show();
    }

    public void setDelegate(ForecastDelegate delegate) {
        mDelegate = delegate;
    }

    public View.OnClickListener getOnChangeTimeClick() {
        return mOnChangeTimeClick;
    }
}
