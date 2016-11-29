package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 11/29/2016.
 */

public class FeeEntity extends AppEntity {

    protected String mSubTotalExcl;
    protected String mSubTotalIncl;
    protected String mTax;
    protected String mShippingExcl;
    protected String mGrandTotal;

    private final String SUBTOTAL_EXCL_TAX = "subtotal_excl_tax";
    private final String SUBTOTAL_INCL_TAX = "subtotal_incl_tax";
    private final String TAX = "tax";
    private final String SHIPPING_HAND_EXCL_TAX = "shipping_hand_excl_tax";
    private final String GRAND_TOTAL_INCL_TAX = "grand_total_incl_tax";

    @Override
    public void parse() {

        mSubTotalExcl = getString(SUBTOTAL_EXCL_TAX);

        mSubTotalIncl = getString(SUBTOTAL_INCL_TAX);

        mTax = getString(TAX);

        mShippingExcl = getString(SHIPPING_HAND_EXCL_TAX);

        mGrandTotal = getString(GRAND_TOTAL_INCL_TAX);

    }

    public String getGrandTotal() {
        return mGrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        mGrandTotal = grandTotal;
    }

    public String getShippingExcl() {
        return mShippingExcl;
    }

    public void setShippingExcl(String shippingExcl) {
        mShippingExcl = shippingExcl;
    }

    public String getSubTotalExcl() {
        return mSubTotalExcl;
    }

    public void setSubTotalExcl(String subTotalExcl) {
        mSubTotalExcl = subTotalExcl;
    }

    public String getSubTotalIncl() {
        return mSubTotalIncl;
    }

    public void setSubTotalIncl(String subTotalIncl) {
        mSubTotalIncl = subTotalIncl;
    }

    public String getTax() {
        return mTax;
    }

    public void setTax(String tax) {
        mTax = tax;
    }
}
