package com.simicart.saletracking.cart.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;

/**
 * Created by Glenn on 12/8/2016.
 */

public interface ListAbandonedCartsDelegate extends AppDelegate {

    public void showBottom(boolean show);

    public void showPage(int current, int total);

    public int getPageSize();

    public void dismissRefresh();

}
