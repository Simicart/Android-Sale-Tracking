package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 11/29/2016.
 */

public class ProductEntity extends AppEntity {

    protected String mName;
    protected String mSku;
    protected String mBasePrice;
    protected String mPrice;
    protected String mQuantityOrdered;
    protected String mImage;

    private final String NAME = "name";
    private final String SKU = "sku";
    private final String BASE_PRICE = "base_price";
    private final String PRICE = "price";
    private final String QTY_ORDERED = "qty_ordered";
    private final String IMAGE = "image";

    @Override
    public void parse() {

        mName = getString(NAME);

        mSku = getString(SKU);

        mBasePrice = getString(BASE_PRICE);

        mPrice = getString(PRICE);

        mQuantityOrdered = getString(QTY_ORDERED);

        mImage = getString(IMAGE);

    }

    public String getBasePrice() {
        return mBasePrice;
    }

    public void setBasePrice(String basePrice) {
        mBasePrice = basePrice;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantityOrdered() {
        return mQuantityOrdered;
    }

    public void setQuantityOrdered(String quantityOrdered) {
        mQuantityOrdered = quantityOrdered;
    }

    public String getSku() {
        return mSku;
    }

    public void setSku(String sku) {
        mSku = sku;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
