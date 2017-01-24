package com.simicart.saletracking.order.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.order.entity.ActionEntity;
import com.simicart.saletracking.order.entity.OrderEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("order")) {
                JSONObject orderObj = mJSONResult.getJSONObject("order");
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.parse(orderObj);
                mCollection.putDataWithKey("order", orderEntity);

                if(orderObj.has("action")) {
                    JSONArray actionArr = orderObj.getJSONArray("action");
                    if(actionArr != null) {
                        ArrayList<ActionEntity> listActions = new ArrayList<>();
                        for(int i=0;i<actionArr.length();i++) {
                            JSONObject object = actionArr.getJSONObject(i);
                            ActionEntity actionEntity = new ActionEntity();
                            actionEntity.parse(object);
                            listActions.add(actionEntity);
                        }
                        mCollection.putDataWithKey("action", listActions);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
