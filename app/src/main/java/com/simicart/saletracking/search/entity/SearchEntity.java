package com.simicart.saletracking.search.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 12/1/2016.
 */

public class SearchEntity extends AppEntity {

    protected String mKey;
    protected String mLabel;
    protected String mQuery;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }
}
