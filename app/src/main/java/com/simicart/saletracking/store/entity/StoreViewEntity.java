package com.simicart.saletracking.store.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Glenn on 11/29/2016.
 */

public class StoreViewEntity extends AppEntity {

    protected String mStoreID;
    protected String mGroupID;
    protected String mStoreName;
    protected boolean mIsActive;
    protected String mBaseUrl;

    private final String STORE_ID = "store_id";
    private final String GROUP_ID = "group_id";
    private final String NAME = "name";
    private final String IS_ACTIVE = "is_active";
    private final String BASE_URL = "base_url";

    @Override
    public void parse() {

        mStoreID = getString(STORE_ID);

        mGroupID = getString(GROUP_ID);

        mStoreName = getString(NAME);

        String active = getString(IS_ACTIVE);
        if (Utils.validateString(active)) {
            mIsActive = Utils.getBoolean(active);
        }

        mBaseUrl = getString(BASE_URL);

    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public String getGroupID() {
        return mGroupID;
    }

    public void setGroupID(String groupID) {
        mGroupID = groupID;
    }

    public boolean isActive() {
        return mIsActive;
    }

    public void setActive(boolean active) {
        mIsActive = active;
    }

    public String getStoreID() {
        return mStoreID;
    }

    public void setStoreID(String storeID) {
        mStoreID = storeID;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String storeName) {
        mStoreName = storeName;
    }
}
