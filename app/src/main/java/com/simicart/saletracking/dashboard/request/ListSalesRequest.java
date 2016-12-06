package com.simicart.saletracking.dashboard.request;

import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.dashboard.entity.SaleEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/5/2016.
 */

public class ListSalesRequest extends AppRequest {

    @Override
    protected void parseData() {
        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("sale")) {
                JSONObject saleObj = mJSONResult.getJSONObject("sale");
                SaleEntity saleEntity = new SaleEntity();
                saleEntity.parse(saleObj);
                mCollection.putDataWithKey("sale", saleEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
