package com.simicart.saletracking.order.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Martial on 1/24/2017.
 */

public class ActionEntity extends AppEntity {

    protected String mKey;
    protected String mValue;

    private final String KEY = "key";
    private final String VALUE = "value";

    @Override
    public void parse() {
        mKey = getString(KEY);

        mValue = getString(VALUE);
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
