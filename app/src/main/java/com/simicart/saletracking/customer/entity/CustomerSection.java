package com.simicart.saletracking.customer.entity;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/30/2016.
 */

public class CustomerSection {

    protected String mDate;
    protected ArrayList<CustomerEntity> mListCustomers;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public ArrayList<CustomerEntity> getListCustomers() {
        return mListCustomers;
    }

    public void setListCustomers(ArrayList<CustomerEntity> listCustomers) {
        mListCustomers = listCustomers;
    }
}
