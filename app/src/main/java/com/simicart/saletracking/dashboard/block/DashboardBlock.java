package com.simicart.saletracking.dashboard.block;

import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.dashboard.adapter.LatestCustomerAdapter;
import com.simicart.saletracking.dashboard.adapter.LatestOrdersAdapter;
import com.simicart.saletracking.dashboard.component.ChartComponent;
import com.simicart.saletracking.dashboard.delegate.DashboardDelegate;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.order.entity.OrderEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/5/2016.
 */

public class DashboardBlock extends AppBlock implements DashboardDelegate {

    protected LinearLayout llTime, llChart;
    protected TextView tvTime;
    protected TableLayout tlSummary;
    protected TextView tvRevenueLabel, tvTaxLabel, tvShippingLabel, tvQuantityLabel, tvLifeTimeSaleLabel, tvAverageLabel;
    protected TextView tvRevenue, tvTax, tvShipping, tvQuantity, tvLifeTimeSale, tvAverage;
    protected TextView tvLatestOrdersTitle, tvLatestCustomersTitle;
    protected RecyclerView rvLatestOrders, rvLatestCustomers;

    public DashboardBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        tvTime = (TextView) mView.findViewById(R.id.tv_time);
        llChart = (LinearLayout) mView.findViewById(R.id.ll_chart);
        llTime = (LinearLayout) mView.findViewById(R.id.ll_time);

        initTotal();

        tvLatestOrdersTitle = (TextView) mView.findViewById(R.id.tv_latest_orders_title);
        tvLatestOrdersTitle.setText("LATEST ORDERS");
        tvLatestOrdersTitle.setBackgroundColor(AppColor.getInstance().getThemeColor());
        tvLatestOrdersTitle.setTextColor(AppColor.getInstance().getWhiteColor());

        rvLatestOrders = (RecyclerView) mView.findViewById(R.id.rv_latest_orders);
        rvLatestOrders.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvLatestOrders.setNestedScrollingEnabled(false);

        tvLatestCustomersTitle = (TextView) mView.findViewById(R.id.tv_latest_customers_title);
        tvLatestCustomersTitle.setText("LATEST CUSTOMERS");
        tvLatestCustomersTitle.setBackgroundColor(AppColor.getInstance().getThemeColor());
        tvLatestCustomersTitle.setTextColor(AppColor.getInstance().getWhiteColor());

        rvLatestCustomers = (RecyclerView) mView.findViewById(R.id.rv_latest_customers);
        rvLatestCustomers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvLatestCustomers.setNestedScrollingEnabled(false);

        int gone = 0;
        if(!AppPreferences.getShowSaleReport()) {
            llTime.setVisibility(View.GONE);
            llChart.setVisibility(View.GONE);
            tlSummary.setVisibility(View.GONE);
            gone++;
        }

        if(!AppPreferences.getShowLatestCustomer()) {
            tvLatestCustomersTitle.setVisibility(View.GONE);
            rvLatestCustomers.setVisibility(View.GONE);
            gone++;
        }

        if(!AppPreferences.getShowLatestOrder()) {
            tvLatestOrdersTitle.setVisibility(View.GONE);
            rvLatestOrders.setVisibility(View.GONE);
            gone++;
        }

        if(gone == 3) {
            showEmptyMessage();
        }

    }

    @Override
    public void updateView(AppCollection collection) {
    }

    @Override
    public void showChart(AppCollection collection) {
        if(collection != null) {
            if(collection.containKey("sale")) {
                SaleEntity saleEntity = (SaleEntity) collection.getDataWithKey("sale");
                if(saleEntity != null) {
                    ChartComponent chartComponent = new ChartComponent();
                    chartComponent.setSaleEntity(saleEntity);
                    View chartView = chartComponent.createView();
                    if(chartView != null) {
                        llChart.removeAllViewsInLayout();
                        llChart.addView(chartView);
                    }

                    showTotal(saleEntity);
                }
            }
        }
    }

    @Override
    public void showLatestOrders(AppCollection collection) {
        if(collection != null) {
            ArrayList<OrderEntity> listOrders = (ArrayList<OrderEntity>) collection.getDataWithKey("orders");
            if(listOrders != null && listOrders.size() > 0) {
                LatestOrdersAdapter latestOrdersAdapter = new LatestOrdersAdapter(listOrders);
                rvLatestOrders.setAdapter(latestOrdersAdapter);
            }
        }
    }

    @Override
    public void showLatestCustomers(AppCollection collection) {
        if(collection != null) {
            if (collection.containKey("customers")) {
                ArrayList<CustomerEntity> listCustomers = (ArrayList<CustomerEntity>) collection.getDataWithKey("customers");
                if (listCustomers != null && listCustomers.size() > 0) {
                    LatestCustomerAdapter latestCustomerAdapter = new LatestCustomerAdapter(listCustomers);
                    rvLatestCustomers.setAdapter(latestCustomerAdapter);
                }
            }
        }
    }

    protected void initTotal() {
        tlSummary = (TableLayout) mView.findViewById(R.id.tl_summary);

        tvRevenueLabel = (TextView) mView.findViewById(R.id.tv_revenue_label);
        tvRevenueLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvRevenueLabel.setText("Revenue");

        tvRevenue = (TextView) mView.findViewById(R.id.tv_revenue);
        tvRevenue.setTextColor(AppColor.getInstance().getBlackColor());

        tvTaxLabel = (TextView) mView.findViewById(R.id.tv_tax_label);
        tvTaxLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvTaxLabel.setText("Tax");

        tvTax = (TextView) mView.findViewById(R.id.tv_tax);
        tvTax.setTextColor(AppColor.getInstance().getBlackColor());

        tvShippingLabel = (TextView) mView.findViewById(R.id.tv_shipping_label);
        tvShippingLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvShippingLabel.setText("Shipping");

        tvShipping = (TextView) mView.findViewById(R.id.tv_shipping);
        tvShipping.setTextColor(AppColor.getInstance().getBlackColor());

        tvQuantityLabel = (TextView) mView.findViewById(R.id.tv_quantity_label);
        tvQuantityLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvQuantityLabel.setText("Quantity");

        tvQuantity = (TextView) mView.findViewById(R.id.tv_quantity);
        tvQuantity.setTextColor(AppColor.getInstance().getBlackColor());

        tvLifeTimeSaleLabel = (TextView) mView.findViewById(R.id.tv_lifetime_sale_label);
        tvLifeTimeSaleLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvLifeTimeSaleLabel.setText("Lifetime Sale");

        tvLifeTimeSale = (TextView) mView.findViewById(R.id.tv_lifetime_sale);
        tvLifeTimeSale.setTextColor(AppColor.getInstance().getBlackColor());

        tvAverageLabel = (TextView) mView.findViewById(R.id.tv_average_label);
        tvAverageLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvAverageLabel.setText("Average");

        tvAverage = (TextView) mView.findViewById(R.id.tv_average);
        tvAverage.setTextColor(AppColor.getInstance().getBlackColor());
    }

    protected void showTotal(SaleEntity saleEntity) {

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

    public void showEmptyMessage() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(AppColor.getInstance().getBlackColor());
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

}
