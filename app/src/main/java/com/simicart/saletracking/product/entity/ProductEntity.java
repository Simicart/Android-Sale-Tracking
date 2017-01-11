package com.simicart.saletracking.product.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Glenn on 11/29/2016.
 */

public class ProductEntity extends AppEntity {

    protected String mItemID;
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
    protected String mDescription;
    protected String mShortDescription;
    protected ArrayList<ProductAttributeEntity> mListAttributes;
    protected boolean mIsInStock;
    protected String mQuantity;

    private final String ITEM_ID = "item_id";
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
    private final String DESCRIPTION = "description";
    private final String SHORT_DESCRIPTION = "short_description";
    private final String ADDITIONAL = "additional";
    private final String IS_IN_STOCK = "is_in_stock";
    private final String STOCK_ITEM = "stock_item";
    private final String QTY = "qty";

    @Override
    public void parse() {

        mItemID = getString(ITEM_ID);

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
                    if (imageObj.has(URL)) {
                        mProductImages.add(imageObj.getString(URL));
                    }
                }
            }

            JSONObject additionalObj = getJSONObjectWithKey(mJSON, ADDITIONAL);
            if (additionalObj != null) {
                mListAttributes = new ArrayList<>();
                Iterator<String> iter = additionalObj.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    JSONObject attributeObj = additionalObj.getJSONObject(key);
                    ProductAttributeEntity attributeEntity = new ProductAttributeEntity();
                    attributeEntity.setJSONObject(attributeObj);
                    attributeEntity.parse();
                    mListAttributes.add(attributeEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mDescription = getString(DESCRIPTION);

        mShortDescription = getString(SHORT_DESCRIPTION);

        JSONObject stockItemObj = getJSONObjectWithKey(mJSON, STOCK_ITEM);
        if(stockItemObj != null) {
            if(stockItemObj.has(QTY)) {
                mQuantity = Utils.formatIntNumber(getStringWithKey(stockItemObj, QTY));
            }

            if(stockItemObj.has(IS_IN_STOCK)) {
                String inStock = getStringWithKey(stockItemObj, IS_IN_STOCK);
                mIsInStock = Utils.getBoolean(inStock);
            }
        }

    }

    public String getItemID() {
        return mItemID;
    }

    public void setItemID(String itemID) {
        mItemID = itemID;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ArrayList<ProductAttributeEntity> getListAttributes() {
        return mListAttributes;
    }

    public void setListAttributes(ArrayList<ProductAttributeEntity> listAttributes) {
        mListAttributes = listAttributes;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public boolean isInStock() {
        return mIsInStock;
    }

    public void setInStock(boolean inStock) {
        mIsInStock = inStock;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }
}
