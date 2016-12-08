package com.simicart.saletracking.product.entity;

import com.simicart.saletracking.base.entity.AppEntity;

import java.io.Serializable;

/**
 * Created by Glenn on 8/10/2016.
 */
public class ProductAttributeEntity extends AppEntity implements Serializable {

    protected String label;
    protected String value;
    protected String code;

    private final String LABEL = "label";
    private final String VALUE = "value";
    private final String CODE = "code";

    @Override
    public void parse() {

        label = getString(LABEL);

        value = getString(VALUE);

        code = getString(CODE);

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
