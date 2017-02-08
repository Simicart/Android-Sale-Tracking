package com.simicart.saletracking.base.component;

/**
 * Created by Martial on 1/7/2017.
 */

public class RowEntity {

    protected int mType;
    protected String mTitle;
    protected String mValue;
    protected String[] mChooseValues;
    protected int mCurrentSelected;
    protected String mKey;
    protected boolean mIsRequired;

    public RowEntity(int type, String title, String key, String value, boolean isRequired) {
        mType = type;
        mTitle = title;
        mKey = key;
        mValue = value;
        mIsRequired = isRequired;
    }

    public RowEntity(int type, String title, String key, String[] chooseValues, int currentSelected) {
        mType = type;
        mTitle = title;
        mKey = key;
        mChooseValues = chooseValues;
        mCurrentSelected = currentSelected;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String[] getChooseValues() {
        return mChooseValues;
    }

    public void setChooseValues(String[] chooseValues) {
        mChooseValues = chooseValues;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public int getCurrentSelected() {
        return mCurrentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        mCurrentSelected = currentSelected;
    }

    public boolean isRequired() {
        return mIsRequired;
    }

    public void setRequired(boolean required) {
        mIsRequired = required;
    }
}
