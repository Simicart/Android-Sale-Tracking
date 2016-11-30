package com.simicart.saletracking.store.request;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.store.entity.StoreViewEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class GetStoreRequest extends AppRequest {

    @Override
    protected void parseData() {

        mCollection = new AppCollection();
        try {
            if (mJSONResult.has("stores")) {
                JSONArray storesArr = mJSONResult.getJSONArray("stores");
                ArrayList<StoreViewEntity> listStores = new ArrayList<>();
                for(int i=0;i<storesArr.length();i++) {
                    JSONObject storeObj = storesArr.getJSONObject(i);
                    if(storeObj.has("storeviews")) {
                        JSONObject storeViewRootObj = storeObj.getJSONObject("storeviews");
                        if(storeViewRootObj.has("storeviews")) {
                            JSONArray storeViewsArr = storeViewRootObj.getJSONArray("storeviews");
                            for(int j=0;j<storeViewsArr.length();j++) {
                                JSONObject storeViewObj = storeViewsArr.getJSONObject(j);
                                StoreViewEntity storeViewEntity = new StoreViewEntity();
                                storeViewEntity.parse(storeViewObj);
                                listStores.add(storeViewEntity);
                            }
                        }
                    }
                }
                mCollection.putDataWithKey("stores", listStores);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
