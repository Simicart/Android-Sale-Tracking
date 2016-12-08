package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.AddressEntity;
import com.simicart.saletracking.product.entity.ProductEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    protected String mOrderCurrencyCode;
    protected String mBaseCurrencyCode;
    protected String mStoreView;
    protected String mCustomerID;
    protected String mPaymentMethodCode;
    protected String mPaymentMethodDescription;
    protected String mShippingMethodCode;
    protected String mShippingMethodDescription;
    protected AddressEntity mShippingAddress;
    protected AddressEntity mBillingAddress;
    protected ArrayList<ProductEntity> mListProducts;
    protected FeeEntity mFee;

    private final String ENTITY_ID = "entity_id";
    private final String STATUS = "status";
    private final String CUSTOMER_FIRST_NAME = "customer_firstname";
    private final String CUSTOMER_LAST_NAME = "customer_lastname";
    private final String INCREMENT_ID = "increment_id";
    private final String CUSTOMER_EMAIL = "customer_email";
    private final String CREATED_AT = "created_at";
    private final String UPDATED_AT = "updated_at";
    private final String GRAND_TOTAL = "grand_total";
    private final String BASE_CURRENCY_CODE = "base_currency_code";
    private final String ORDER_CURRENCY_CODE = "order_currency_code";
    private final String STORE_NAME = "store_name";
    private final String CUSTOMER_ID = "customer_id";
    private final String PAYMENT_METHOD = "payment_method";
    private final String PAYMENT_DESCRIPTION = "payment_description";
    private final String SHIPPING_METHOD = "shipping_method";
    private final String SHIPPING_DESCRIPTION = "shipping_description";
    private final String SHIPPING_ADDRESS = "shipping_address";
    private final String BILLING_ADDRESS = "billing_address";
    private final String ORDER_ITEMS = "order_items";
    private final String TOTAL = "total";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mStatus = getString(STATUS);

        mCustomerFirstName = getString(CUSTOMER_FIRST_NAME);

        mCustomerLastName = getString(CUSTOMER_LAST_NAME);

        mIncrementID = getString(INCREMENT_ID);

        mCustomerEmail = getString(CUSTOMER_EMAIL);

        String createdAt = getString(CREATED_AT);
        if (Utils.validateString(createdAt)) {
            String[] splits = createdAt.split(" ");
            mCreatedAtDate = splits[0];
            mCreatedAtTime = splits[1];
        }

        mUpdatedAt = getString(UPDATED_AT);

        mGrandTotal = getString(GRAND_TOTAL);

        mOrderCurrencyCode = getString(ORDER_CURRENCY_CODE);

        mBaseCurrencyCode = getString(BASE_CURRENCY_CODE);

        mStoreView = getString(STORE_NAME);

        mCustomerID = getString(CUSTOMER_ID);

        mPaymentMethodCode = getString(PAYMENT_METHOD);

        mPaymentMethodDescription = getString(PAYMENT_DESCRIPTION);

        mShippingMethodCode = getString(SHIPPING_METHOD);

        mShippingMethodDescription = getString(SHIPPING_DESCRIPTION);

        JSONObject shippingAddressObj = getJSONObjectWithKey(mJSON, SHIPPING_ADDRESS);
        if (shippingAddressObj != null) {
            mShippingAddress = new AddressEntity();
            mShippingAddress.parse(shippingAddressObj);
        }

        JSONObject billingAddressObj = getJSONObjectWithKey(mJSON, BILLING_ADDRESS);
        if (billingAddressObj != null) {
            mBillingAddress = new AddressEntity();
            mBillingAddress.parse(billingAddressObj);
        }

        JSONArray itemsArr = getJSONArrayWithKey(mJSON, ORDER_ITEMS);
        if (itemsArr != null) {
            try {
                mListProducts = new ArrayList<>();
                for (int i = 0; i < itemsArr.length(); i++) {
                    JSONObject itemObj = itemsArr.getJSONObject(i);
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.parse(itemObj);
                    mListProducts.add(productEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject feeObj = getJSONObjectWithKey(mJSON, TOTAL);
        if (feeObj != null) {
            mFee = new FeeEntity();
            mFee.parse(feeObj);
        }

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

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        mBaseCurrencyCode = baseCurrencyCode;
    }

    public String getOrderCurrencyCode() {
        return mOrderCurrencyCode;
    }

    public void setOrderCurrencyCode(String orderCurrencyCode) {
        mOrderCurrencyCode = orderCurrencyCode;
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

    public AddressEntity getBillingAddress() {
        return mBillingAddress;
    }

    public void setBillingAddress(AddressEntity billingAddress) {
        mBillingAddress = billingAddress;
    }

    public String getCustomerID() {
        return mCustomerID;
    }

    public void setCustomerID(String customerID) {
        mCustomerID = customerID;
    }

    public String getPaymentMethodCode() {
        return mPaymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        mPaymentMethodCode = paymentMethodCode;
    }

    public String getPaymentMethodDescription() {
        return mPaymentMethodDescription;
    }

    public void setPaymentMethodDescription(String paymentMethodDescription) {
        mPaymentMethodDescription = paymentMethodDescription;
    }

    public AddressEntity getShippingAddress() {
        return mShippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        mShippingAddress = shippingAddress;
    }

    public String getShippingMethodCode() {
        return mShippingMethodCode;
    }

    public void setShippingMethodCode(String shippingMethodCode) {
        mShippingMethodCode = shippingMethodCode;
    }

    public String getShippingMethodDescription() {
        return mShippingMethodDescription;
    }

    public void setShippingMethodDescription(String shippingMethodDescription) {
        mShippingMethodDescription = shippingMethodDescription;
    }

    public String getStoreView() {
        return mStoreView;
    }

    public void setStoreView(String storeView) {
        mStoreView = storeView;
    }

    public ArrayList<ProductEntity> getListProducts() {
        return mListProducts;
    }

    public void setListProducts(ArrayList<ProductEntity> listProducts) {
        mListProducts = listProducts;
    }

    public FeeEntity getFee() {
        return mFee;
    }

    public void setFee(FeeEntity fee) {
        mFee = fee;
    }
}
