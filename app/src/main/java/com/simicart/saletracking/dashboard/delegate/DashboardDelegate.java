package com.simicart.saletracking.dashboard.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.request.AppCollection;

/**
 * Created by Glenn on 12/6/2016.
 */

public interface DashboardDelegate extends AppDelegate {

    public void showChart(AppCollection collection);

    public void showLatestOrders(AppCollection collection);

    public void showLatestCustomers(AppCollection collection);

}
