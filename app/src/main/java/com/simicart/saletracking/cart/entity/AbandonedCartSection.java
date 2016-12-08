package com.simicart.saletracking.cart.entity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartSection {

    protected String mDate;
    protected ArrayList<AbandonedCartEntity> mListCarts;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public ArrayList<AbandonedCartEntity> getListCarts() {
        return mListCarts;
    }

    public void setListCarts(ArrayList<AbandonedCartEntity> listCarts) {
        mListCarts = listCarts;
    }
}
