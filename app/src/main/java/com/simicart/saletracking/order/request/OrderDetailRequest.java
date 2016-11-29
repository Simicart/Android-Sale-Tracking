package com.simicart.saletracking.order.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.order.entity.OrderEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if(mJSONResult.has("order")) {
                JSONObject orderObj = mJSONResult.getJSONObject("order");
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.parse(orderObj);
                mCollection.putDataWithKey("order", orderEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
