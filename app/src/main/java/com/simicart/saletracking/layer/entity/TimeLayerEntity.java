package com.simicart.saletracking.layer.entity;

/**
 * Created by Glenn on 12/2/2016.
 */

public class TimeLayerEntity extends LayerEntity {

    protected String mFromDateKey = "from_date";
    protected String mFromDate;
    protected String mToDateKey = "to_date";
    protected String mToDate;

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        mFromDate = fromDate;
    }

    public String getFromDateKey() {
        return mFromDateKey;
    }

    public void setFromDateKey(String fromDateKey) {
        mFromDateKey = fromDateKey;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        mToDate = toDate;
    }

    public String getToDateKey() {
        return mToDateKey;
    }

    public void setToDateKey(String toDateKey) {
        mToDateKey = toDateKey;
    }
}
