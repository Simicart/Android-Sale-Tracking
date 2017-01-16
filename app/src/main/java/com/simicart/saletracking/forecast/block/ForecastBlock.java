package com.simicart.saletracking.forecast.block;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.forecast.delegate.ForecastDelegate;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastBlock extends AppBlock implements ForecastDelegate {

    protected TextView tvTime, tvXAxis, tvYAxis;
    protected LineChart mLineChart;
    protected ArrayList<TimeLayerEntity> mListTimeLayers;

    public ForecastBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initTimeLayer();

        tvTime = (TextView) mView.findViewById(R.id.tv_time);
        Utils.setTextHtml(tvTime, "<p><u>3 month</u> forecast</p>");

        tvXAxis = (TextView) mView.findViewById(R.id.tv_x_label);
        tvXAxis.setText("Orders");

        tvYAxis = (TextView) mView.findViewById(R.id.tv_y_label);
        tvYAxis.setText("Invoices");

        mLineChart = (LineChart) mView.findViewById(R.id.chart);
    }

    @Override
    public void updateView(AppCollection collection) {

    }

    @Override
    public ArrayList<TimeLayerEntity> getListTimeLayers() {
        return mListTimeLayers;
    }

    protected void initTimeLayer() {
        mListTimeLayers = new ArrayList<>();

        TimeLayerEntity last7Days = new TimeLayerEntity();
        last7Days.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        last7Days.setToDate(Utils.getDate(Calendar.DATE, 30, true));
        last7Days.setLabel("1 Month");
        last7Days.setPeriod("day");
        mListTimeLayers.add(last7Days);

        TimeLayerEntity currentMonth = new TimeLayerEntity();
        currentMonth.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        currentMonth.setToDate(Utils.getDate(Calendar.DATE, 60, true));
        currentMonth.setLabel("2 Months");
        currentMonth.setPeriod("day");
        mListTimeLayers.add(currentMonth);

        TimeLayerEntity lastMonth = new TimeLayerEntity();
        lastMonth.setFromDate(Utils.getDate(Calendar.DATE, 1, true));
        lastMonth.setToDate(Utils.getDate(Calendar.DATE, 90, true));
        lastMonth.setLabel("3 Months");
        lastMonth.setPeriod("day");
        mListTimeLayers.add(lastMonth);
    }
}
