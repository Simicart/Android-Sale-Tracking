package com.simicart.saletracking.products.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.order.entity.ProductEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("products")) {
                JSONArray productsArr = mJSONResult.getJSONArray("products");
                ArrayList<ProductEntity> listProducts = new ArrayList<>();
                for(int i=0;i<productsArr.length();i++) {
                    JSONObject productObj = productsArr.getJSONObject(i);
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.parse(productObj);
                    listProducts.add(productEntity);
                }
                mCollection.putDataWithKey("products", listProducts);
            }
            if(mJSONResult.has("total")) {
                int total = mJSONResult.getInt("total");
                mCollection.putDataWithKey("total", total);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
