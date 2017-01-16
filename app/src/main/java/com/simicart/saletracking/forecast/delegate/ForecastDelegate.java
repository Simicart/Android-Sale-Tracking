package com.simicart.saletracking.forecast.delegate;

import android.view.View;

import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.util.ArrayList;

/**
 * Created by Martial on 1/16/2017.
 */

public interface ForecastDelegate extends AppDelegate {

    public ArrayList<TimeLayerEntity> getListTimeLayers();

}
