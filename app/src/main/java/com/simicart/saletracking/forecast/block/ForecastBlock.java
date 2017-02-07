package com.simicart.saletracking.forecast.block;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.dashboard.chart.DateInMonthValueFormatter;
import com.simicart.saletracking.dashboard.chart.IntegerValueFormatter;
import com.simicart.saletracking.dashboard.chart.MonthInYearValueFormatter;
import com.simicart.saletracking.dashboard.entity.ChartEntity;
import com.simicart.saletracking.forecast.delegate.ForecastDelegate;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastBlock extends AppBlock implements ForecastDelegate {

    protected TextView tvTime;
    protected LineChart mLineChart;

    public ForecastBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        tvTime = (TextView) mView.findViewById(R.id.tv_time);

        initChart();
    }

    @Override
    public void updateView(AppCollection collection) {

    }

    @Override
    public void showChart(ArrayList<ChartEntity> listCharts, TimeLayerEntity timeLayer) {
        initChart();

        int period = 0;
        String key = timeLayer.getKey();
        switch (key) {
            case "chart_1_month":
                period = 30;
                Utils.setTextHtml(tvTime, "<u><font color=#13f501>1 month</font></u> forecast");
                break;
            case "chart_2_months":
                period = 60;
                Utils.setTextHtml(tvTime, "<u><font color=#13f501>2 months</font></u> forecast");
                break;
            case "chart_3_months":
                period = 90;
                Utils.setTextHtml(tvTime, "<u><font color=#13f501>3 months</font></u> forecast");
                break;
            default:
                break;
        }

        LineData lineData = new LineData();

        ArrayList<Entry> upperEntries = new ArrayList<>();
        ArrayList<Entry> lowerEntries = new ArrayList<>();
        if (listCharts != null) {
            for (int i=0;i<period;i++) {
                ChartEntity chartEntity = listCharts.get(i);
                float upperIncome = chartEntity.getTotalInvoicedAmountUpper();
                float lowerIncome = chartEntity.getTotalInvoicedAmountLower();

                upperEntries.add(new Entry(i, upperIncome));
                lowerEntries.add(new Entry(i, lowerIncome));
            }
        }

        LineDataSet lineDataSetUpper = new LineDataSet(upperEntries, "Income Upper");
        lineDataSetUpper.setColor(Color.parseColor("#3399ff"));
        lineDataSetUpper.setLineWidth(1.5f);
        lineDataSetUpper.setDrawCircles(false);
        lineDataSetUpper.setDrawCircleHole(false);
        lineDataSetUpper.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSetUpper.setDrawValues(false);
        lineDataSetUpper.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData.addDataSet(lineDataSetUpper);

        LineDataSet lineDataSetLower = new LineDataSet(lowerEntries, "Income Lower");
        lineDataSetLower.setColor(Color.parseColor("#ffa500"));
        lineDataSetLower.setLineWidth(1.5f);
        lineDataSetLower.setDrawCircles(false);
        lineDataSetLower.setDrawCircleHole(false);
        lineDataSetLower.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSetLower.setDrawValues(false);
        lineDataSetLower.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData.addDataSet(lineDataSetLower);

        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }

    @Override
    public void setTimeFormat(TimeLayerEntity timeLayer) {
        XAxis mAxisTop = mLineChart.getXAxis();
        mAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);
        mAxisTop.setDrawGridLines(true);
        mAxisTop.setValueFormatter(new DateInMonthValueFormatter(timeLayer));
    }

    protected void initChart() {

        mLineChart = (LineChart) mView.findViewById(R.id.chart);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setBackgroundColor(Color.WHITE);
        mLineChart.setDrawGridBackground(false);

        Legend l = mLineChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis mAxisLeft = mLineChart.getAxisLeft();
        mAxisLeft.setDrawGridLines(false);
        mAxisLeft.setAxisMinimum(0f);
    }

    public void setOnChangeTimeClick(View.OnClickListener listener) {
        tvTime.setOnClickListener(listener);
    }
}
