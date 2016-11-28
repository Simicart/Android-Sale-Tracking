package com.simicart.saletracking.order.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;

/**
 * Created by Glenn on 11/28/2016.
 */

public interface ListOrdersDelegate extends AppDelegate {

    public void showBottom(boolean show);

    public void showPage(int current, int total);

}
