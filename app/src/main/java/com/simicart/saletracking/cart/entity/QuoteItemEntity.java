package com.simicart.saletracking.cart.entity;

import com.simicart.saletracking.product.entity.ProductEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/8/2016.
 */

public class QuoteItemEntity extends ProductEntity {

    protected String mItemsQty;
    protected String mProductID;

    private final String QTY = "qty";
    private final String PRODUCT = "product";
    private final String PRODUCT_ID = "entity_id";

    @Override
    public void parse() {
        super.parse();

        mItemsQty = getString(QTY);

        JSONObject productObj = getJSONObjectWithKey(mJSON, PRODUCT);
        if(productObj != null) {
            if(productObj.has(PRODUCT_ID)) {
                try {
                    mProductID = productObj.getString(PRODUCT_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getItemsQty() {
        return mItemsQty;
    }

    public void setItemsQty(String itemsQty) {
        mItemsQty = itemsQty;
    }

    public String getProductID() {
        return mProductID;
    }

    public void setProductID(String productID) {
        mProductID = productID;
    }
}
