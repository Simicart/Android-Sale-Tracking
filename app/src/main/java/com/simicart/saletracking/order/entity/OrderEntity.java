package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Glenn on 11/27/2016.
 */

public class OrderEntity extends AppEntity {

    protected String mID;
    protected String mStatus;
    protected String mCustomerFirstName;
    protected String mCustomerLastName;
    protected String mIncrementID;
    protected String mCustomerEmail;
    protected String mCreatedAtDate;
    protected String mCreatedAtTime;
    protected String mUpdatedAt;
    protected String mGrandTotal;
    protected String mOrderCurrencySymbol;

    private final String ENTITY_ID = "entity_id";
    private final String STATUS = "status";
    private final String CUSTOMER_FIRST_NAME = "customer_firstname";
    private final String CUSTOMER_LAST_NAME = "customer_lastname";
    private final String INCREMENT_ID = "increment_id";
    private final String CUSTOMER_EMAIL = "customer_email";
    private final String CREATED_AT = "created_at";
    private final String UPDATED_AT = "updated_at";
    private final String GRAND_TOTAL = "grand_total";
    private final String ORDER_CURRENCY_SYMBOL = "order_currency_symbol";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mStatus = getString(STATUS);

        mCustomerFirstName = getString(CUSTOMER_FIRST_NAME);

        mCustomerLastName = getString(CUSTOMER_LAST_NAME);

        mIncrementID = getString(INCREMENT_ID);

        mCustomerEmail = getString(CUSTOMER_EMAIL);

        String createdAt = getString(CREATED_AT);
        if(Utils.validateString(createdAt)) {
            String[] splits = createdAt.split(" ");
            mCreatedAtDate = splits[0];
            mCreatedAtTime = splits[1];
        }

        mUpdatedAt = getString(UPDATED_AT);

        mGrandTotal = getString(GRAND_TOTAL);

        mOrderCurrencySymbol = getString(ORDER_CURRENCY_SYMBOL);

    }

    public String getCreatedAtDate() {
        return mCreatedAtDate;
    }

    public void setCreatedAtDate(String createdAtDate) {
        mCreatedAtDate = createdAtDate;
    }

    public String getCreatedAtTime() {
        return mCreatedAtTime;
    }

    public void setCreatedAtTime(String createdAtTime) {
        mCreatedAtTime = createdAtTime;
    }

    public String getCustomerEmail() {
        return mCustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        mCustomerEmail = customerEmail;
    }

    public String getCustomerFirstName() {
        return mCustomerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        mCustomerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return mCustomerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        mCustomerLastName = customerLastName;
    }

    public String getGrandTotal() {
        return mGrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        mGrandTotal = grandTotal;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getIncrementID() {
        return mIncrementID;
    }

    public void setIncrementID(String incrementID) {
        mIncrementID = incrementID;
    }

    public String getOrderCurrencySymbol() {
        return mOrderCurrencySymbol;
    }

    public void setOrderCurrencySymbol(String orderCurrencySymbol) {
        mOrderCurrencySymbol = orderCurrencySymbol;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }
}
