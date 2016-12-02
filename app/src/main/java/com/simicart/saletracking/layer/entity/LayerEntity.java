package com.simicart.saletracking.layer.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 12/2/2016.
 */

public class LayerEntity extends AppEntity {

    protected String mKey;
    protected String mValue;
    protected String mLabel;

    private final String STATUS = "status";
    private final String LABEL = "label";

    @Override
    public void parse() {

        mValue = getString(STATUS);

        mLabel = getString(LABEL);
    }

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

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
