package com.simicart.saletracking.forecast.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.dashboard.entity.ChartEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("salesforecast")) {
                JSONObject forecastObj = mJSONResult.getJSONObject("salesforecast");
                if(forecastObj != null) {
                    JSONArray saleArr = forecastObj.getJSONArray("day");
                    ArrayList<ChartEntity> listCharts = new ArrayList<>();
                    if(saleArr != null) {
                        for(int i=0;i<saleArr.length();i++) {
                            JSONObject object = saleArr.getJSONObject(i);
                            ChartEntity chartEntity = new ChartEntity();
                            chartEntity.parse(object);
                            listCharts.add(chartEntity);
                        }
                    }
                    mCollection.putDataWithKey("salesforecast", listCharts);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
