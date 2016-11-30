package com.simicart.saletracking.customer.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.order.entity.OrderEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/30/2016.
 */

public class ListCustomersRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("customers")) {
                JSONArray customersArr = mJSONResult.getJSONArray("customers");
                ArrayList<CustomerEntity> listCustomers = new ArrayList<>();
                for(int i=0;i<customersArr.length();i++) {
                    JSONObject customerObj = customersArr.getJSONObject(i);
                    CustomerEntity customerEntity = new CustomerEntity();
                    customerEntity.parse(customerObj);
                    listCustomers.add(customerEntity);
                }
                mCollection.putDataWithKey("customers", listCustomers);
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
