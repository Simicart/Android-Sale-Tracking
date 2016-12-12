package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;
import com.simicart.saletracking.store.entity.StoreViewEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/9/2016.
 */

public class TimeAdapter extends BaseAdapter {

    protected ArrayList<TimeLayerEntity> mListTimes;
    protected LayoutInflater inflater;
    protected Context mContext;

    public TimeAdapter(ArrayList<TimeLayerEntity> listTimes) {
        inflater = LayoutInflater.from(AppManager.getInstance().getCurrentActivity());
        mListTimes = listTimes;
        mContext = AppManager.getInstance().getCurrentActivity();
    }

    @Override
    public int getCount() {
        return mListTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return mListTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvTime = new TextView(mContext);
        tvTime.setPaddingRelative(Utils.toPixel(5), Utils.toPixel(5), Utils.toPixel(5), Utils.toPixel(5));
        tvTime.setTextColor(Color.BLACK);
        tvTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        TimeLayerEntity timeLayerEntity = mListTimes.get(position);
        String label = timeLayerEntity.getLabel();
        if (Utils.validateString(label)) {
            tvTime.setText(label);
        }

        return tvTime;
    }
}
