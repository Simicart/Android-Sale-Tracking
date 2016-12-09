package com.simicart.saletracking.bestseller.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellerEntity extends AppEntity {

    protected String mItemName;
    protected String mID;
    protected String mSku;
    protected String mQtyOrdered;

    private final String ORDER_ITEMS_NAME = "order_items_name";
    private final String ENTITY_ID = "entity_id";
    private final String SKU = "sku";
    private final String ORDERED_QTY = "ordered_qty";

    @Override
    public void parse() {
        mItemName = getString(ORDER_ITEMS_NAME);

        mID = getString(ENTITY_ID);

        mSku = getString(SKU);

        mQtyOrdered = getString(ORDERED_QTY);
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getQtyOrdered() {
        return mQtyOrdered;
    }

    public void setQtyOrdered(String qtyOrdered) {
        mQtyOrdered = qtyOrdered;
    }

    public String getSku() {
        return mSku;
    }

    public void setSku(String sku) {
        mSku = sku;
    }
}
