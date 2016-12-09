package com.simicart.saletracking.cart.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class ListAbandonedCartsRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("abandonedcarts")) {
                JSONArray cartsArr = mJSONResult.getJSONArray("abandonedcarts");
                ArrayList<AbandonedCartEntity> listCarts = new ArrayList<>();
                for (int i = 0; i < cartsArr.length(); i++) {
                    JSONObject cartObj = cartsArr.getJSONObject(i);
                    AbandonedCartEntity cartEntity = new AbandonedCartEntity();
                    cartEntity.parse(cartObj);
                    listCarts.add(cartEntity);
                }
                mCollection.putDataWithKey("abandonedcarts", listCarts);
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
