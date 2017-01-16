package com.simicart.saletracking.dashboard.component;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.AppComponent;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.dashboard.chart.DateInMonthValueFormatter;
import com.simicart.saletracking.dashboard.chart.IntegerValueFormatter;
import com.simicart.saletracking.dashboard.chart.MonthInYearValueFormatter;
import com.simicart.saletracking.dashboard.entity.ChartEntity;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/5/2016.
 */

public class ChartComponent extends AppComponent {

    protected CombinedChart mCombinedChart;
    protected LineData mLineData;
    protected ArrayList<ChartEntity> listCharts;
    protected TimeLayerEntity mTimeLayerEntity;
    protected CombinedData mCombinedData;
    protected YAxis mAxisRight, mAxisLeft;
    protected XAxis mAxisTop;
    protected int mType;

    public void setListCharts(ArrayList<ChartEntity> listCharts) {
        this.listCharts = listCharts;
    }

    public void setTimeLayerEntity(TimeLayerEntity timeLayerEntity) {
        mTimeLayerEntity = timeLayerEntity;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    public View createView() {
        rootView = mInflater.inflate(R.layout.component_chart, null);

        initChart();

        mCombinedData = new CombinedData();
        if(mType == Constants.Chart.DASHBOARD) {
            showDashboard();
            mCombinedChart.setData(mCombinedData);
        } else {
            showForecast();
        }
        mCombinedChart.invalidate();

        return rootView;
    }

    protected void initChart() {
        mCombinedChart = (CombinedChart) rootView.findViewById(R.id.chart);
        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawGridBackground(false);
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.setHighlightFullBarEnabled(false);
        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});

        Legend l = mCombinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        mAxisRight = mCombinedChart.getAxisRight();
        mAxisRight.setDrawGridLines(false);
        mAxisRight.setAxisMinimum(0f);
        mAxisRight.setValueFormatter(new IntegerValueFormatter());

        mAxisLeft = mCombinedChart.getAxisLeft();
        mAxisLeft.setDrawGridLines(true);
        mAxisLeft.setAxisMinimum(0f);

        mAxisTop = mCombinedChart.getXAxis();
        mAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);
        mAxisTop.setDrawGridLines(true);
        if(mTimeLayerEntity.getPeriod().equals("day")) {
            mAxisTop.setValueFormatter(new DateInMonthValueFormatter(mTimeLayerEntity));
        } else {
            mAxisTop.setValueFormatter(new MonthInYearValueFormatter(mTimeLayerEntity));
        }
    }

    protected void showDashboard() {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        if (listCharts != null) {
            for (ChartEntity chartEntity : listCharts) {
                barEntries.add(new BarEntry(listCharts.indexOf(chartEntity), (float) chartEntity.getOrdersCount()));
            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Orders Count");
        barDataSet.setColor(AppColor.getInstance().getThemeColor());
        barDataSet.setDrawValues(false);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        mCombinedData.setData(barData);

        LineData lineData = new LineData();

        ArrayList<Entry> lineEntries = new ArrayList<>();
        float maxIncome = 0;
        float minIncome = 0;
        if (listCharts != null) {
            for (ChartEntity chartEntity : listCharts) {
                float income = chartEntity.getTotalIncomeAmount();
                if (income < minIncome) {
                    minIncome = income;
                }
                if (income > maxIncome) {
                    maxIncome = income;
                }
                lineEntries.add(new Entry(listCharts.indexOf(chartEntity), income));
            }
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Income Value (USD)");
        lineDataSet.setColor(Color.parseColor("#cc0000"));
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setDrawValues(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData.addDataSet(lineDataSet);

        mCombinedData.setData(lineData);
    }

    protected void showForecast() {
        LineData lineDataUpper = new LineData();

        ArrayList<Entry> lineEntrieslineDataSetUpper = new ArrayList<>();
        float maxIncomeUpper = 0;
        float minIncomeUpper = 0;
        if (listCharts != null) {
            for (ChartEntity chartEntity : listCharts) {
                float income = chartEntity.getTotalInvoicedAmountUpper();
                if (income < minIncomeUpper) {
                    minIncomeUpper = income;
                }
                if (income > maxIncomeUpper) {
                    maxIncomeUpper = income;
                }
                lineEntrieslineDataSetUpper.add(new Entry(listCharts.indexOf(chartEntity), income));
            }
        }

        LineDataSet lineDataSetUpper = new LineDataSet(lineEntrieslineDataSetUpper, "Income Upper");
        lineDataSetUpper.setColor(Color.parseColor("#ffff00"));
        lineDataSetUpper.setLineWidth(1.5f);
        lineDataSetUpper.setDrawCircles(true);
        lineDataSetUpper.setDrawCircleHole(false);
        lineDataSetUpper.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetUpper.setDrawValues(false);
        lineDataSetUpper.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataUpper.addDataSet(lineDataSetUpper);

        mCombinedData.setData(lineDataUpper);

        LineData lineDataLower = new LineData();

        ArrayList<Entry> lineEntrieslineDataSetLower = new ArrayList<>();
        float maxIncomeLower = 0;
        float minIncomeLower = 0;
        if (listCharts != null) {
            for (ChartEntity chartEntity : listCharts) {
                float income = chartEntity.getTotalInvoicedAmountLower();
                if (income < minIncomeLower) {
                    minIncomeLower = income;
                }
                if (income > maxIncomeLower) {
                    maxIncomeLower = income;
                }
                lineEntrieslineDataSetLower.add(new Entry(listCharts.indexOf(chartEntity), income));
            }
        }

        LineDataSet lineDataSetLower = new LineDataSet(lineEntrieslineDataSetLower, "Income Lower");
        lineDataSetLower.setColor(Color.parseColor("#008000"));
        lineDataSetLower.setLineWidth(1.5f);
        lineDataSetLower.setDrawCircles(true);
        lineDataSetLower.setDrawCircleHole(false);
        lineDataSetLower.setMode(LineDataSet.Mode.LINEAR);
        lineDataSetLower.setDrawValues(false);
        lineDataSetLower.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataLower.addDataSet(lineDataSetLower);

        mCombinedData.setData(new LineData());
    }

}
