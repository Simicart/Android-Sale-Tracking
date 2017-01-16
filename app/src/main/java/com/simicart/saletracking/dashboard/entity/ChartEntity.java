package com.simicart.saletracking.dashboard.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 12/5/2016.
 */

public class ChartEntity extends AppEntity {

    protected String mPeriod;
    protected int mOrdersCount;
    protected int mTotalQtyOrdered;
    protected int mTotalQtyInvoiced;
    protected float mTotalIncomeAmount;
    protected float mTotalRevenueAmount;
    protected float mTotalProfitAmount;
    protected float mTotalInvoicedAmount;
    protected float mTotalCanceledAmount;
    protected float mToTalPaidAmount;
    protected float mTotalRefundedAmount;
    protected float mTotalTaxAmount;
    protected float mTotalTaxAmountActual;
    protected float mTotalShippingAmount;
    protected float mTotalShippingAmountActual;
    protected float mTotalDiscountAmount;
    protected float mTotalDiscountAmountActual;
    protected float mOrdersCountUpper;
    protected float mOrdersCountLower;
    protected float mTotalInvoicedAmountUpper;
    protected float mTotalInvoicedAmountLower;

    private final String PERIOD = "period";
    private final String ORDERS_COUNT = "orders_count";
    private final String TOTAL_QTY_ORDERED = "total_qty_ordered";
    private final String TOTAL_QTY_INVOICED = "total_qty_invoiced";
    private final String TOTAL_INCOME_AMOUNT = "total_income_amount";
    private final String TOTAL_REVENUE_AMOUNT = "total_revenue_amount";
    private final String TOTAL_PROFIT_AMOUNT = "total_profit_amount";
    private final String TOTAL_INVOICED_AMOUNT = "total_invoiced_amount";
    private final String TOTAL_CANCELED_AMOUNT = "total_canceled_amount";
    private final String TOTAL_PAID_AMOUNT = "total_paid_amount";
    private final String TOTAL_REFUNDED_AMOUNT = "total_refunded_amount";
    private final String TOTAL_TAX_AMOUNT = "total_tax_amount";
    private final String TOTAL_TAX_AMOUNT_ACTUAL = "total_tax_amount_actual";
    private final String TOTAL_SHIPPING_AMOUNT = "total_shipping_amount";
    private final String TOTAL_SHIPPING_AMOUNT_ACTUAL = "total_shipping_amount_actual";
    private final String TOTAL_DISCOUNT_AMOUNT = "total_discount_amount";
    private final String TOTAL_DISCOUNT_AMOUNT_ACTUAL = "total_discount_amount_actual";
    private final String ORDERS_COUNT_UPPER = "orders_count_upper";
    private final String ORDERS_COUNT_LOWER = "orders_count_lower";
    private final String TOTAL_INVOICED_AMOUNT_UPPER = "total_invoiced_amount_upper";
    private final String TOTAL_INVOICED_AMOUNT_LOWER = "total_invoiced_amount_lower";

    @Override
    public void parse() {

        mPeriod = getString(PERIOD);

        String ordersCount = getString(ORDERS_COUNT);
        mOrdersCount = parseInt(ordersCount);

        String totalQtyOrdered = getString(TOTAL_QTY_ORDERED);
        mTotalQtyOrdered = parseInt(totalQtyOrdered);

        String totalQtyInvoice = getString(TOTAL_QTY_INVOICED);
        mTotalQtyInvoiced = parseInt(totalQtyInvoice);

        String totalIncomeAmount = getString(TOTAL_INCOME_AMOUNT);
        mTotalIncomeAmount = parseFloat(totalIncomeAmount);

        String totalRevenueAmount = getString(TOTAL_REVENUE_AMOUNT);
        mTotalRevenueAmount = parseFloat(totalRevenueAmount);

        String totalProfitAmount = getString(TOTAL_PROFIT_AMOUNT);
        mTotalProfitAmount = parseFloat(totalProfitAmount);

        String totalInvoicedAmount = getString(TOTAL_INVOICED_AMOUNT);
        mTotalInvoicedAmount = parseFloat(totalInvoicedAmount);

        String totalCanceledAmount = getString(TOTAL_CANCELED_AMOUNT);
        mTotalCanceledAmount = parseFloat(totalCanceledAmount);

        String totalPaidAmount = getString(TOTAL_PAID_AMOUNT);
        mToTalPaidAmount = parseFloat(totalPaidAmount);

        String totalRefundedAmount = getString(TOTAL_REFUNDED_AMOUNT);
        mTotalRefundedAmount = parseFloat(totalRefundedAmount);

        String totalTaxAmount = getString(TOTAL_TAX_AMOUNT);
        mTotalTaxAmount = parseFloat(totalTaxAmount);

        String totalTaxAmountActual = getString(TOTAL_TAX_AMOUNT_ACTUAL);
        mTotalTaxAmountActual = parseFloat(totalTaxAmountActual);

        String totalShippingAmount = getString(TOTAL_SHIPPING_AMOUNT);
        mTotalShippingAmount = parseFloat(totalShippingAmount);

        String totalShippingAmountActual = getString(TOTAL_SHIPPING_AMOUNT_ACTUAL);
        mTotalShippingAmountActual = parseFloat(totalShippingAmountActual);

        String totalDiscountAmount = getString(TOTAL_DISCOUNT_AMOUNT);
        mTotalDiscountAmount = parseFloat(totalDiscountAmount);

        String totalDiscountAmountActual = getString(TOTAL_DISCOUNT_AMOUNT_ACTUAL);
        mTotalDiscountAmountActual = parseFloat(totalDiscountAmountActual);

        String ordersCountUpper = getString(ORDERS_COUNT_UPPER);
        mOrdersCountUpper = parseFloat(ordersCountUpper);

        String ordersCountLower = getString(ORDERS_COUNT_LOWER);
        mOrdersCountLower = parseFloat(ordersCountLower);

        String totalInvoicedAmountUpper = getString(TOTAL_INVOICED_AMOUNT_UPPER);
        mTotalInvoicedAmountUpper = parseFloat(totalInvoicedAmountUpper);

        String totalInvoicedAmountLower = getString(TOTAL_INVOICED_AMOUNT_LOWER);
        mTotalInvoicedAmountLower = parseFloat(totalInvoicedAmountLower);

    }

    public int getOrdersCount() {
        return mOrdersCount;
    }

    public void setOrdersCount(int ordersCount) {
        mOrdersCount = ordersCount;
    }

    public String getPeriod() {
        return mPeriod;
    }

    public void setPeriod(String period) {
        mPeriod = period;
    }

    public float getTotalCanceledAmount() {
        return mTotalCanceledAmount;
    }

    public void setTotalCanceledAmount(float totalCanceledAmount) {
        mTotalCanceledAmount = totalCanceledAmount;
    }

    public float getTotalDiscountAmount() {
        return mTotalDiscountAmount;
    }

    public void setTotalDiscountAmount(float totalDiscountAmount) {
        mTotalDiscountAmount = totalDiscountAmount;
    }

    public float getTotalDiscountAmountActual() {
        return mTotalDiscountAmountActual;
    }

    public void setTotalDiscountAmountActual(float totalDiscountAmountActual) {
        mTotalDiscountAmountActual = totalDiscountAmountActual;
    }

    public float getTotalIncomeAmount() {
        return mTotalIncomeAmount;
    }

    public void setTotalIncomeAmount(float totalIncomeAmount) {
        mTotalIncomeAmount = totalIncomeAmount;
    }

    public float getTotalInvoicedAmount() {
        return mTotalInvoicedAmount;
    }

    public void setTotalInvoicedAmount(float totalInvoicedAmount) {
        mTotalInvoicedAmount = totalInvoicedAmount;
    }

    public float getToTalPaidAmount() {
        return mToTalPaidAmount;
    }

    public void setToTalPaidAmount(float toTalPaidAmount) {
        mToTalPaidAmount = toTalPaidAmount;
    }

    public float getTotalProfitAmount() {
        return mTotalProfitAmount;
    }

    public void setTotalProfitAmount(float totalProfitAmount) {
        mTotalProfitAmount = totalProfitAmount;
    }

    public int getTotalQtyInvoiced() {
        return mTotalQtyInvoiced;
    }

    public void setTotalQtyInvoiced(int totalQtyInvoiced) {
        mTotalQtyInvoiced = totalQtyInvoiced;
    }

    public int getTotalQtyOrdered() {
        return mTotalQtyOrdered;
    }

    public void setTotalQtyOrdered(int totalQtyOrdered) {
        mTotalQtyOrdered = totalQtyOrdered;
    }

    public float getTotalRefundedAmount() {
        return mTotalRefundedAmount;
    }

    public void setTotalRefundedAmount(float totalRefundedAmount) {
        mTotalRefundedAmount = totalRefundedAmount;
    }

    public float getTotalRevenueAmount() {
        return mTotalRevenueAmount;
    }

    public void setTotalRevenueAmount(float totalRevenueAmount) {
        mTotalRevenueAmount = totalRevenueAmount;
    }

    public float getTotalShippingAmount() {
        return mTotalShippingAmount;
    }

    public void setTotalShippingAmount(float totalShippingAmount) {
        mTotalShippingAmount = totalShippingAmount;
    }

    public float getTotalShippingAmountActual() {
        return mTotalShippingAmountActual;
    }

    public void setTotalShippingAmountActual(float totalShippingAmountActual) {
        mTotalShippingAmountActual = totalShippingAmountActual;
    }

    public float getTotalTaxAmount() {
        return mTotalTaxAmount;
    }

    public void setTotalTaxAmount(float totalTaxAmount) {
        mTotalTaxAmount = totalTaxAmount;
    }

    public float getTotalTaxAmountActual() {
        return mTotalTaxAmountActual;
    }

    public void setTotalTaxAmountActual(float totalTaxAmountActual) {
        mTotalTaxAmountActual = totalTaxAmountActual;
    }

    public float getOrdersCountLower() {
        return mOrdersCountLower;
    }

    public void setOrdersCountLower(float ordersCountLower) {
        mOrdersCountLower = ordersCountLower;
    }

    public float getOrdersCountUpper() {
        return mOrdersCountUpper;
    }

    public void setOrdersCountUpper(float ordersCountUpper) {
        mOrdersCountUpper = ordersCountUpper;
    }

    public float getTotalInvoicedAmountLower() {
        return mTotalInvoicedAmountLower;
    }

    public void setTotalInvoicedAmountLower(float totalInvoicedAmountLower) {
        mTotalInvoicedAmountLower = totalInvoicedAmountLower;
    }

    public float getTotalInvoicedAmountUpper() {
        return mTotalInvoicedAmountUpper;
    }

    public void setTotalInvoicedAmountUpper(float totalInvoicedAmountUpper) {
        mTotalInvoicedAmountUpper = totalInvoicedAmountUpper;
    }
}
