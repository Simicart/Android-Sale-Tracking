package com.simicart.saletracking.dashboard.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.dashboard.adapter.LatestCustomerAdapter;
import com.simicart.saletracking.dashboard.adapter.LatestOrdersAdapter;
import com.simicart.saletracking.dashboard.adapter.TimeAdapter;
import com.simicart.saletracking.dashboard.adapter.TopBestSellerAdapter;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;
import com.simicart.saletracking.order.entity.OrderEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardBlock extends AppBlock implements DashboardDelegate {

    protected LinearLayout llRefresh, llChart, llTopChart;
    protected TextView tvRefresh;
    protected Spinner spTime;
    protected TableLayout tlSummary;
    protected TableRow trLifeTime;
    protected TextView tvRevenueLabel, tvTaxLabel, tvShippingLabel, tvQuantityLabel, tvLifeTimeSaleLabel, tvAverageLabel;
    protected TextView tvRevenue, tvTax, tvShipping, tvQuantity, tvLifeTimeSale, tvAverage;
    protected TextView tvTopBestSellersTitle, tvLatestOrdersTitle, tvLatestCustomersTitle;
    protected RecyclerView rvTopBestSellers, rvLatestOrders, rvLatestCustomers;
    protected ArrayList<TimeLayerEntity> mListTimeLayers;

    public DashboardBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        initTopChart();

        llChart = (LinearLayout) mView.findViewById(R.id.ll_chart);

        initTotal();

        tvTopBestSellersTitle = (TextView) mView.findViewById(R.id.tv_best_seller_title);
        tvTopBestSellersTitle.setText("BEST SELLERS");
        tvTopBestSellersTitle.setBackgroundColor(AppColor.getInstance().getThemeColor());
        tvTopBestSellersTitle.setTextColor(Color.WHITE);

        rvTopBestSellers = (RecyclerView) mView.findViewById(R.id.rv_best_sellers);
        rvTopBestSellers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvTopBestSellers.setNestedScrollingEnabled(false);

        tvLatestOrdersTitle = (TextView) mView.findViewById(R.id.tv_latest_orders_title);
        tvLatestOrdersTitle.setText("LATEST ORDERS");
        tvLatestOrdersTitle.setBackgroundColor(AppColor.getInstance().getThemeColor());
        tvLatestOrdersTitle.setTextColor(Color.WHITE);

        rvLatestOrders = (RecyclerView) mView.findViewById(R.id.rv_latest_orders);
        rvLatestOrders.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvLatestOrders.setNestedScrollingEnabled(false);

        tvLatestCustomersTitle = (TextView) mView.findViewById(R.id.tv_latest_customers_title);
        tvLatestCustomersTitle.setText("LATEST CUSTOMERS");
        tvLatestCustomersTitle.setBackgroundColor(AppColor.getInstance().getThemeColor());
        tvLatestCustomersTitle.setTextColor(Color.WHITE);

        rvLatestCustomers = (RecyclerView) mView.findViewById(R.id.rv_latest_customers);
        rvLatestCustomers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvLatestCustomers.setNestedScrollingEnabled(false);

        int gone = 0;
        if (!AppPreferences.getShowSaleReport()) {
            llTopChart.setVisibility(View.GONE);
            llChart.setVisibility(View.GONE);
            tlSummary.setVisibility(View.GONE);
            gone++;
        }

        if (!AppPreferences.getShowBestSellers()) {
            tvTopBestSellersTitle.setVisibility(View.GONE);
            rvTopBestSellers.setVisibility(View.GONE);
            gone++;
        }

        if (!AppPreferences.getShowLatestCustomer()) {
            tvLatestCustomersTitle.setVisibility(View.GONE);
            rvLatestCustomers.setVisibility(View.GONE);
            gone++;
        }

        if (!AppPreferences.getShowLatestOrder()) {
            tvLatestOrdersTitle.setVisibility(View.GONE);
            rvLatestOrders.setVisibility(View.GONE);
            gone++;
        }

        if (gone == 4) {
            showEmptyMessage();
        }

    }

    @Override
    public void updateView(AppCollection collection) {

    }

    @Override
    public void showChart(View view) {
        if (view != null) {
            llChart.removeAllViewsInLayout();
            llChart.addView(view);
        }
    }

    @Override
    public void showTotal(SaleEntity saleEntity) {

        float revenue = saleEntity.getTotalSaleRevenue();
        tvRevenue.setText(Utils.getPrice(String.valueOf(revenue), "USD"));

        float tax = saleEntity.getTotalSaleTax();
        tvTax.setText(Utils.getPrice(String.valueOf(tax), "USD"));

        float shipping = saleEntity.getTotalSaleShipping();
        tvShipping.setText(Utils.getPrice(String.valueOf(shipping), "USD"));

        int quantity = saleEntity.getTotalSaleQuantity();
        tvQuantity.setText(String.valueOf(quantity));

        float lifetimeTotal = saleEntity.getLifeTimeSaleTotal();
        tvLifeTimeSale.setText(Utils.getPrice(String.valueOf(lifetimeTotal), "USD"));

        float average = saleEntity.getLifeTimeSaleAverage();
        tvAverage.setText(Utils.getPrice(String.valueOf(average), "USD"));

    }

    @Override
    public void showBestSellers(AppCollection collection) {
        if(collection != null && collection.containKey("bestsellers")) {
            ArrayList<BestSellerEntity> listBestSellers = (ArrayList<BestSellerEntity>) collection.getDataWithKey("bestsellers");
            if (listBestSellers != null && listBestSellers.size() > 0) {
                TopBestSellerAdapter topBestSellerAdapter = new TopBestSellerAdapter(listBestSellers);
                rvTopBestSellers.setAdapter(topBestSellerAdapter);
            }
        }
    }

    @Override
    public void showLatestOrders(AppCollection collection) {
        if (collection != null && collection.containKey("orders")) {
            ArrayList<OrderEntity> listOrders = (ArrayList<OrderEntity>) collection.getDataWithKey("orders");
            if (listOrders != null && listOrders.size() > 0) {
                LatestOrdersAdapter latestOrdersAdapter = new LatestOrdersAdapter(listOrders);
                rvLatestOrders.setAdapter(latestOrdersAdapter);
            }
        }
    }

    @Override
    public void showLatestCustomers(AppCollection collection) {
        if (collection != null && collection.containKey("customers")) {
            if (collection.containKey("customers")) {
                ArrayList<CustomerEntity> listCustomers = (ArrayList<CustomerEntity>) collection.getDataWithKey("customers");
                if (listCustomers != null && listCustomers.size() > 0) {
                    LatestCustomerAdapter latestCustomerAdapter = new LatestCustomerAdapter(listCustomers);
                    rvLatestCustomers.setAdapter(latestCustomerAdapter);
                }
            }
        }
    }

    @Override
    public ArrayList<TimeLayerEntity> getListTimeLayers() {
        return mListTimeLayers;
    }

    protected void initTopChart() {
        llTopChart = (LinearLayout) mView.findViewById(R.id.ll_top_chart);
        spTime = (Spinner) mView.findViewById(R.id.sp_time);
        initTimeLayer();
        TimeAdapter adapter = new TimeAdapter(mListTimeLayers);
        spTime.setAdapter(adapter);

        llRefresh = (LinearLayout) mView.findViewById(R.id.ll_refresh);

        tvRefresh = (TextView) mView.findViewById(R.id.tv_refresh);
        tvRefresh.setTextColor(Color.BLACK);
        tvRefresh.setText("Refresh Site Stats");
    }

    protected void initTimeLayer() {
        mListTimeLayers = new ArrayList<>();

        TimeLayerEntity last7Days = new TimeLayerEntity();
        last7Days.setFromDate(getDate(Calendar.DATE, -7, true));
        last7Days.setToDate(getToDay());
        last7Days.setLabel("Last 7 Days");
        last7Days.setKey("7_days");
        last7Days.setPeriod("day");
        mListTimeLayers.add(last7Days);

        TimeLayerEntity currentMonth = new TimeLayerEntity();
        currentMonth.setFromDate(getDate(Calendar.DAY_OF_MONTH, 1, false));
        currentMonth.setToDate(getToDay());
        currentMonth.setLabel("Current Month");
        currentMonth.setKey("current_month");
        currentMonth.setPeriod("day");
        mListTimeLayers.add(currentMonth);

        TimeLayerEntity lastMonth = new TimeLayerEntity();
        lastMonth.setFromDate(getFirstDayOfLastMonth());
        lastMonth.setToDate(getLastDayOfLastMonth());
        lastMonth.setLabel("Last Month");
        lastMonth.setKey("last_month");
        lastMonth.setPeriod("day");
        mListTimeLayers.add(lastMonth);

        TimeLayerEntity threeMonths = new TimeLayerEntity();
        threeMonths.setFromDate(getDate(Calendar.DAY_OF_MONTH, -90, true));
        threeMonths.setToDate(getToDay());
        threeMonths.setLabel("Last 3 Months (90 Days)");
        threeMonths.setKey("three_months");
        threeMonths.setPeriod("day");
        mListTimeLayers.add(threeMonths);

        TimeLayerEntity thisYear = new TimeLayerEntity();
        thisYear.setFromDate(getDate(Calendar.DAY_OF_YEAR, 1, false));
        thisYear.setToDate(getToDay());
        thisYear.setLabel("Year To Day");
        thisYear.setKey("this_year");
        thisYear.setPeriod("month");
        mListTimeLayers.add(thisYear);

        TimeLayerEntity twoYears = new TimeLayerEntity();
        twoYears.setFromDate(getFirstDayOfLast2Year());
        twoYears.setToDate(getToDay());
        twoYears.setLabel("2 Years To Day");
        twoYears.setKey("two_years");
        twoYears.setPeriod("month");
        mListTimeLayers.add(twoYears);
    }

    protected void initTotal() {
        tlSummary = (TableLayout) mView.findViewById(R.id.tl_summary);
        if(!AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.TOTALS_DETAILS)) {
            tlSummary.setVisibility(View.GONE);
        }

        tvRevenueLabel = (TextView) mView.findViewById(R.id.tv_revenue_label);
        tvRevenueLabel.setTextColor(Color.BLACK);
        tvRevenueLabel.setText("Revenue");

        tvRevenue = (TextView) mView.findViewById(R.id.tv_revenue);
        tvRevenue.setTextColor(Color.BLACK);

        tvTaxLabel = (TextView) mView.findViewById(R.id.tv_tax_label);
        tvTaxLabel.setTextColor(Color.BLACK);
        tvTaxLabel.setText("Tax");

        tvTax = (TextView) mView.findViewById(R.id.tv_tax);
        tvTax.setTextColor(Color.BLACK);

        tvShippingLabel = (TextView) mView.findViewById(R.id.tv_shipping_label);
        tvShippingLabel.setTextColor(Color.BLACK);
        tvShippingLabel.setText("Shipping");

        tvShipping = (TextView) mView.findViewById(R.id.tv_shipping);
        tvShipping.setTextColor(Color.BLACK);

        tvQuantityLabel = (TextView) mView.findViewById(R.id.tv_quantity_label);
        tvQuantityLabel.setTextColor(Color.BLACK);
        tvQuantityLabel.setText("Quantity");

        tvQuantity = (TextView) mView.findViewById(R.id.tv_quantity);
        tvQuantity.setTextColor(Color.BLACK);

        trLifeTime = (TableRow) mView.findViewById(R.id.tr_life_time);
        if(!AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.LIFETIME_SALES)) {
            trLifeTime.setVisibility(View.GONE);
        }

        tvLifeTimeSaleLabel = (TextView) mView.findViewById(R.id.tv_lifetime_sale_label);
        tvLifeTimeSaleLabel.setTextColor(Color.BLACK);
        tvLifeTimeSaleLabel.setText("Lifetime Sale");

        tvLifeTimeSale = (TextView) mView.findViewById(R.id.tv_lifetime_sale);
        tvLifeTimeSale.setTextColor(Color.BLACK);

        tvAverageLabel = (TextView) mView.findViewById(R.id.tv_average_label);
        tvAverageLabel.setTextColor(Color.BLACK);
        tvAverageLabel.setText("Average");

        tvAverage = (TextView) mView.findViewById(R.id.tv_average);
        tvAverage.setTextColor(Color.BLACK);
    }

    public void showEmptyMessage() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("Nothing to show");
        tvEmpty.setTypeface(null, Typeface.BOLD);
        tvEmpty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvEmpty.setGravity(Gravity.CENTER);
        tvEmpty.setLayoutParams(params);
        ((ViewGroup) mView).addView(tvEmpty);
    }

    protected String getDate(int a, int b, boolean add) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (add) {
            cal.add(a, b);
        } else {
            cal.set(a, b);
        }
        return dateFormat.format(cal.getTime());
    }

    protected String getFirstDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(cal.getTime());
    }

    protected String getLastDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    protected String getFirstDayOfLast2Year() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.YEAR, -2);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(cal.getTime());
    }

    protected String getToDay() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

    public void setOnTimeSelected(AdapterView.OnItemSelectedListener listener) {
        spTime.setOnItemSelectedListener(listener);
    }

    public void setOnRefreshStats(View.OnClickListener listener) {
        llRefresh.setOnClickListener(listener);
    }

}
