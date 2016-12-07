package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class ProductEntity extends AppEntity {

    protected String mName;
    protected String mSku;
    protected String mBasePrice;
    protected String mPrice;
    protected String mQuantityOrdered;
    protected String mOrderImage;
    protected ArrayList<String> mProductImages;
    protected String mID;
    protected String mType;
    protected String mVisibility;

    private final String NAME = "name";
    private final String SKU = "sku";
    private final String BASE_PRICE = "base_price";
    private final String PRICE = "price";
    private final String QTY_ORDERED = "qty_ordered";
    private final String IMAGE = "image";
    private final String IMAGES = "images";
    private final String URL = "url";
    private final String ENTITY_ID = "entity_id";
    private final String TYPE_ID = "type_id";
    private final String VISIBILITY = "visibility";

    @Override
    public void parse() {

        mName = getString(NAME);

        mSku = getString(SKU);

        mBasePrice = getString(BASE_PRICE);

        mPrice = getString(PRICE);

        mQuantityOrdered = getString(QTY_ORDERED);

        mOrderImage = getString(IMAGE);

        mID = getString(ENTITY_ID);

        mType = getString(TYPE_ID);

        mVisibility = getString(VISIBILITY);

        try {
            JSONArray imagesArr = getJSONArrayWithKey(mJSON, IMAGES);
            if (imagesArr != null) {
                mProductImages = new ArrayList<>();
                for (int i = 0; i < imagesArr.length(); i++) {
                    JSONObject imageObj = imagesArr.getJSONObject(i);
                    if(imageObj.has(URL)) {
                        mProductImages.add(imageObj.getString(URL));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public String getOrderImage() {
        return mOrderImage;
    }

    public void setOrderImage(String orderImage) {
        mOrderImage = orderImage;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

    public ArrayList<String> getProductImages() {
        return mProductImages;
    }

    public void setProductImages(ArrayList<String> productImages) {
        mProductImages = productImages;
    }
}
