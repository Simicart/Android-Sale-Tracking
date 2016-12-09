package com.simicart.saletracking.cart.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartDetailRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("abandonedcart")) {
                JSONObject cartObj = mJSONResult.getJSONObject("abandonedcart");
                AbandonedCartEntity cartEntity = new AbandonedCartEntity();
                cartEntity.parse(cartObj);
                mCollection.putDataWithKey("abandonedcart", cartEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
