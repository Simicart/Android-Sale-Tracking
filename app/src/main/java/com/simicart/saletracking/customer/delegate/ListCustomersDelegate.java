package com.simicart.saletracking.customer.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;

/**
 * Created by Glenn on 11/30/2016.
 */

public interface ListCustomersDelegate extends AppDelegate {

    public void showBottom(boolean show);

    public void showPage(int current, int total);

    public int getPageSize();

}
