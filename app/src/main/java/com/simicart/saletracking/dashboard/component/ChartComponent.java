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
    protected SaleEntity saleEntity;
    protected TimeLayerEntity mTimeLayerEntity;
    protected CombinedData mCombinedData;
    protected YAxis mAxisRight, mAxisLeft;
    protected XAxis mAxisTop;

    public void setSaleEntity(SaleEntity saleEntity) {
        this.saleEntity = saleEntity;
    }

    public void setTimeLayerEntity(TimeLayerEntity timeLayerEntity) {
        mTimeLayerEntity = timeLayerEntity;
    }

    @Override
    public View createView() {
        rootView = mInflater.inflate(R.layout.component_chart, null);

        initChart();

        mCombinedData = new CombinedData();
        showBar();
        showLine();
        mCombinedChart.setData(mCombinedData);
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

    protected void showBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        ArrayList<ChartEntity> listCharts = saleEntity.getListTotalCharts();
        if (listCharts != null) {
            for (ChartEntity chartEntity : listCharts) {
                entries.add(new BarEntry(listCharts.indexOf(chartEntity), (float) chartEntity.getOrdersCount()));
            }
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Orders Count");
        barDataSet.setColor(AppColor.getInstance().getThemeColor());
        barDataSet.setDrawValues(false);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        mCombinedData.setData(barData);

    }

    protected void showLine() {
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<ChartEntity> listCharts = saleEntity.getListTotalCharts();
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
                entries.add(new Entry(listCharts.indexOf(chartEntity), income));
            }
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Income Value (USD)");
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

}
