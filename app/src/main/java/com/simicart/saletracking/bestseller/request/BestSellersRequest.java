package com.simicart.saletracking.bestseller.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;
import com.simicart.saletracking.customer.entity.CustomerEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellersRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("bestsellers")) {
                JSONArray bestsellersArr = mJSONResult.getJSONArray("bestsellers");
                ArrayList<BestSellerEntity> listBestSellers = new ArrayList<>();
                for (int i = 0; i < bestsellersArr.length(); i++) {
                    JSONObject bestsellerObj = bestsellersArr.getJSONObject(i);
                    BestSellerEntity bestSellerEntity = new BestSellerEntity();
                    bestSellerEntity.parse(bestsellerObj);
                    listBestSellers.add(bestSellerEntity);
                }
                mCollection.putDataWithKey("bestsellers", listBestSellers);
            }
            if (mJSONResult.has("total")) {
                int total = mJSONResult.getInt("total");
                mCollection.putDataWithKey("total", total);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
