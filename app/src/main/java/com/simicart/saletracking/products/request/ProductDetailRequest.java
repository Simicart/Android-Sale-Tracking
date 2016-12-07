package com.simicart.saletracking.products.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.products.entity.ProductEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDetailRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if(mJSONResult.has("product")) {
                JSONObject productObj = mJSONResult.getJSONObject("product");
                ProductEntity productEntity = new ProductEntity();
                productEntity.parse(productObj);
                mCollection.putDataWithKey("product", productEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
