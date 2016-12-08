package com.simicart.saletracking.cart.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.entity.ProductEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartEntity extends AppEntity{

    protected String mID;
    protected String mCustomerEmail;
    protected String mItemQty;
    protected String mRemoteIP;
    protected String mCreatedAtDate;
    protected String mCreatedAtTime;
    protected String mUpdatedAt;
    protected String mGrandTotal;
    protected String mQuoteCurrencyCode;
    protected String mBaseCurrencyCode;
    protected String mSubTotal;
    protected ArrayList<QuoteItemEntity> mListQuotes;

    private final String ENTITY_ID = "entity_id";
    private final String CUSTOMER_EMAIL = "customer_email";
    private final String ITEMS_QTY = "items_qty";
    private final String REMOTE_IP = "remote_ip";
    private final String CREATED_AT = "created_at";
    private final String UPDATED_AT = "updated_at";
    private final String GRAND_TOTAL = "grand_total";
    private final String QUOTE_CURRENCY_CODE = "quote_currency_code";
    private final String BASE_CURRENCY_CODE = "base_currency_code";
    private final String SUB_TOTAL = "subtotal";
    private final String PRODUCTS = "products";

    @Override
    public void parse() {
        mID = getString(ENTITY_ID);

        mCustomerEmail = getString(CUSTOMER_EMAIL);

        mItemQty = getString(ITEMS_QTY);

        mRemoteIP = getString(REMOTE_IP);

        String createdAt = getString(CREATED_AT);
        if (Utils.validateString(createdAt)) {
            String[] splits = null;
            if(createdAt.contains(" ")) {
                splits = createdAt.split(" ");
            } else if(createdAt.contains("T")) {
                splits = createdAt.split("T");
            }
            mCreatedAtDate = splits[0];
            mCreatedAtTime = splits[1];
        }

        mUpdatedAt = getString(UPDATED_AT);

        mGrandTotal = getString(GRAND_TOTAL);

        mQuoteCurrencyCode = getString(QUOTE_CURRENCY_CODE);

        mBaseCurrencyCode = getString(BASE_CURRENCY_CODE);

        mSubTotal = getString(SUB_TOTAL);

        JSONArray itemsArr = getJSONArrayWithKey(mJSON, PRODUCTS);
        if (itemsArr != null) {
            try {
                mListQuotes = new ArrayList<>();
                for (int i = 0; i < itemsArr.length(); i++) {
                    JSONObject itemObj = itemsArr.getJSONObject(i);
                    QuoteItemEntity quoteEntity = new QuoteItemEntity();
                    quoteEntity.parse(itemObj);
                    mListQuotes.add(quoteEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCustomerEmail() {
        return mCustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        mCustomerEmail = customerEmail;
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

    public String getItemQty() {
        return mItemQty;
    }

    public void setItemQty(String itemQty) {
        mItemQty = itemQty;
    }

    public String getQuoteCurrencyCode() {
        return mQuoteCurrencyCode;
    }

    public void setQuoteCurrencyCode(String quoteCurrencyCode) {
        mQuoteCurrencyCode = quoteCurrencyCode;
    }

    public String getRemoteIP() {
        return mRemoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        mRemoteIP = remoteIP;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
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

    public String getCreatedAt() {
        return mCreatedAtDate + " " + mCreatedAtTime;
    }

    public ArrayList<QuoteItemEntity> getListQuotes() {
        return mListQuotes;
    }

    public void setListQuotes(ArrayList<QuoteItemEntity> listQuotes) {
        mListQuotes = listQuotes;
    }

    public String getSubTotal() {
        return mSubTotal;
    }

    public void setSubTotal(String subTotal) {
        mSubTotal = subTotal;
    }

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        mBaseCurrencyCode = baseCurrencyCode;
    }
}
