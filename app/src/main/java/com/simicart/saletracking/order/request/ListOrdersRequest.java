package com.simicart.saletracking.order.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.order.entity.OrderEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/27/2016.
 */

public class ListOrdersRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("orders")) {
                JSONArray ordersArr = mJSONResult.getJSONArray("orders");
                ArrayList<OrderEntity> listOrders = new ArrayList<>();
                for(int i=0;i<ordersArr.length();i++) {
                    JSONObject orderObj = ordersArr.getJSONObject(i);
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.parse(orderObj);
                    listOrders.add(orderEntity);
                }
                mCollection.putDataWithKey("orders", listOrders);
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
