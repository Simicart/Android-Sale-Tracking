package com.simicart.saletracking.customer.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.customer.entity.AddressEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/3/2016.
 */

public class ListAddressesRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("addresses")) {
                JSONArray addressesArr = mJSONResult.getJSONArray("addresses");
                ArrayList<AddressEntity> listAddresses = new ArrayList<>();
                for(int i=0;i<addressesArr.length();i++) {
                    JSONObject addressObj = addressesArr.getJSONObject(i);
                    AddressEntity addressEntity = new AddressEntity();
                    addressEntity.parse(addressObj);
                    listAddresses.add(addressEntity);
                }
                mCollection.putDataWithKey("addresses", listAddresses);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
