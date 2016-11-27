package com.simicart.saletracking.customer.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.customer.entity.CustomerEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("customer")) {
                JSONObject customerObj = mJSONResult.getJSONObject("customer");
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.parse(customerObj);
                mCollection.putDataWithKey("customer", customerEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
