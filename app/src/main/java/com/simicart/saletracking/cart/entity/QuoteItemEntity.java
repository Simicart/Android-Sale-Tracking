package com.simicart.saletracking.cart.entity;

import com.simicart.saletracking.product.entity.ProductEntity;

/**
 * Created by Glenn on 12/8/2016.
 */

public class QuoteItemEntity extends ProductEntity {

    protected String mItemsQty;

    private final String QTY = "qty";

    @Override
    public void parse() {
        super.parse();

        mItemsQty = getString(QTY);
    }

    public String getItemsQty() {
        return mItemsQty;
    }

    public void setItemsQty(String itemsQty) {
        mItemsQty = itemsQty;
    }
}
