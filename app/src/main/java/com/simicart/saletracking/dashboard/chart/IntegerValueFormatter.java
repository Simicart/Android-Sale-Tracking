package com.simicart.saletracking.dashboard.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Glenn on 12/6/2016.
 */

public class IntegerValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.valueOf(Math.round(value));
    }
}
