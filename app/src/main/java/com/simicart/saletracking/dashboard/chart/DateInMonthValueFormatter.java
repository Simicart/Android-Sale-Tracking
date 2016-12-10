package com.simicart.saletracking.dashboard.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/6/2016.
 */

public class DateInMonthValueFormatter implements IAxisValueFormatter {

    protected TimeLayerEntity mTimeLayerEntity;
    protected ArrayList<String> mTimes;

    public DateInMonthValueFormatter(TimeLayerEntity timeLayerEntity) {
        mTimeLayerEntity = timeLayerEntity;
        mTimes = Utils.getDatesFromPeriod(mTimeLayerEntity.getFromDate(), mTimeLayerEntity.getToDate());
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int date = ((int) value);
        if(date < mTimes.size()) {
            return mTimes.get(date);
        } else {
            return "";
        }
    }
}
