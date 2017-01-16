package com.simicart.saletracking.dashboard.delegate;

import android.view.View;

import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.dashboard.entity.ChartEntity;
import com.simicart.saletracking.dashboard.entity.SaleEntity;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/6/2016.
 */

public interface DashboardDelegate extends AppDelegate {

    public void showChart(ArrayList<ChartEntity> listCharts);

    public void showTotal(SaleEntity saleEntity);

    public void showBestSellers(AppCollection collection);

    public void showLatestOrders(AppCollection collection);

    public void showLatestCustomers(AppCollection collection);

    public ArrayList<TimeLayerEntity> getListTimeLayers();

    public void setTimeLayer(TimeLayerEntity timeLayer);

}
