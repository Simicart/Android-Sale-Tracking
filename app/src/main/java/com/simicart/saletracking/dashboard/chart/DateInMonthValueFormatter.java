package com.simicart.saletracking.dashboard.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Glenn on 12/6/2016.
 */

public class DateInMonthValueFormatter implements IAxisValueFormatter {

    protected String month;

    public DateInMonthValueFormatter(String month) {
        this.month = month;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int date = (int) value;
        date++;
        if(date > 0 && date < 10) {
            return "0" + date + "/" + month;
        } else {
            return date + "/" + month;
        }
    }
}
