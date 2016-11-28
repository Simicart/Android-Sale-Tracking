package com.simicart.saletracking.order.entity;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/28/2016.
 */

public class OrderSection {

    protected String mDate;
    protected ArrayList<OrderEntity> mListOrders;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public ArrayList<OrderEntity> getListOrders() {
        return mListOrders;
    }

    public void setListOrders(ArrayList<OrderEntity> listOrders) {
        mListOrders = listOrders;
    }
}
