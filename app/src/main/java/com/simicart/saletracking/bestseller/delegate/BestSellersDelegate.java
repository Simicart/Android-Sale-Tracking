package com.simicart.saletracking.bestseller.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;

/**
 * Created by Glenn on 12/9/2016.
 */

public interface BestSellersDelegate extends AppDelegate {

    public void showBottom(boolean show);

    public void showPage(int current, int total);

    public int getPageSize();

}
