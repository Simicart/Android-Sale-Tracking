package com.simicart.saletracking.product.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;

/**
 * Created by Glenn on 12/7/2016.
 */

public interface ListProductsDelegate extends AppDelegate {

    public void showBottom(boolean show);

    public void showPage(int current, int total);

    public int getPageSize();

    public void dismissRefresh();

}
