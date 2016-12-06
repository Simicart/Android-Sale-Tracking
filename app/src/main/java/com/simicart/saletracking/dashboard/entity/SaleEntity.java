package com.simicart.saletracking.dashboard.entity;

import com.simicart.saletracking.base.entity.AppEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/5/2016.
 */

public class SaleEntity extends AppEntity {

    protected ArrayList<ChartEntity> mListTotalCharts;
    protected float mTotalSaleRevenue;
    protected float mTotalSaleTax;
    protected float mTotalSaleShipping;
    protected int mTotalSaleQuantity;
    protected float mLifeTimeSaleTotal;
    protected float mLifeTimeSaleAverage;

    private final String TOTAL_CHART = "total_chart";
    private final String TOTAL_SALE = "total_sale";
    private final String LIFETIME_SALE = "lifetime_sale";
    private final String REVENUE = "revenue";
    private final String TAX = "tax";
    private final String SHIPPING = "shipping";
    private final String QUANTITY = "quantity";
    private final String LIFETIME = "lifetime";
    private final String AVERAGE = "average";

    @Override
    public void parse() {

        JSONArray chartsArr = getJSONArrayWithKey(mJSON, TOTAL_CHART);
        if (chartsArr != null) {
            mListTotalCharts = new ArrayList<>();
            try {
                for (int i = 0; i < chartsArr.length(); i++) {
                    JSONObject chartObj = chartsArr.getJSONObject(i);
                    ChartEntity chartEntity = new ChartEntity();
                    chartEntity.parse(chartObj);
                    mListTotalCharts.add(chartEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject totalSaleObj = getJSONObjectWithKey(mJSON, TOTAL_SALE);
        if(totalSaleObj != null) {
            String revenue = getStringWithKey(totalSaleObj, REVENUE);
            mTotalSaleRevenue = parseFloat(revenue);

            String tax = getStringWithKey(totalSaleObj, TAX);
            mTotalSaleTax = parseFloat(tax);

            String shipping = getStringWithKey(totalSaleObj, SHIPPING);
            mTotalSaleShipping = parseFloat(shipping);

            String quantity = getStringWithKey(totalSaleObj, QUANTITY);
            mTotalSaleQuantity = parseInt(quantity);
        }

        JSONObject lifetimeSaleObj = getJSONObjectWithKey(mJSON, LIFETIME_SALE);
        if(lifetimeSaleObj != null) {
            String lifetime = getStringWithKey(lifetimeSaleObj, LIFETIME);
            mLifeTimeSaleTotal = parseFloat(lifetime);

            String average = getStringWithKey(lifetimeSaleObj, AVERAGE);
            mLifeTimeSaleAverage = parseFloat(average);
        }

    }

    public float getLifeTimeSaleAverage() {
        return mLifeTimeSaleAverage;
    }

    public void setLifeTimeSaleAverage(float lifeTimeSaleAverage) {
        mLifeTimeSaleAverage = lifeTimeSaleAverage;
    }

    public float getLifeTimeSaleTotal() {
        return mLifeTimeSaleTotal;
    }

    public void setLifeTimeSaleTotal(float lifeTimeSaleTotal) {
        mLifeTimeSaleTotal = lifeTimeSaleTotal;
    }

    public ArrayList<ChartEntity> getListTotalCharts() {
        return mListTotalCharts;
    }

    public void setListTotalCharts(ArrayList<ChartEntity> listTotalCharts) {
        mListTotalCharts = listTotalCharts;
    }

    public int getTotalSaleQuantity() {
        return mTotalSaleQuantity;
    }

    public void setTotalSaleQuantity(int totalSaleQuantity) {
        mTotalSaleQuantity = totalSaleQuantity;
    }

    public float getTotalSaleRevenue() {
        return mTotalSaleRevenue;
    }

    public void setTotalSaleRevenue(float totalSaleRevenue) {
        mTotalSaleRevenue = totalSaleRevenue;
    }

    public float getTotalSaleShipping() {
        return mTotalSaleShipping;
    }

    public void setTotalSaleShipping(float totalSaleShipping) {
        mTotalSaleShipping = totalSaleShipping;
    }

    public float getTotalSaleTax() {
        return mTotalSaleTax;
    }

    public void setTotalSaleTax(float totalSaleTax) {
        mTotalSaleTax = totalSaleTax;
    }
}
