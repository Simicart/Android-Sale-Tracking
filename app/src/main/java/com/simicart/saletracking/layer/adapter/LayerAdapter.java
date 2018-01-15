package com.simicart.saletracking.layer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.layer.callback.LayerCallback;
import com.simicart.saletracking.layer.entity.LayerEntity;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Glenn on 12/2/2016.
 */

public class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.LayerHolder> {

    protected ArrayList<LayerEntity> mListLayers;
    protected HashMap<String, Object> hmData;
    protected LayerEntity mSelectedLayer;
    protected int mFrom;
    protected LayerCallback mLayerCallback;

    public LayerAdapter(HashMap<String, Object> hmData) {
        this.hmData = hmData;
        parseData();
    }

    @Override
    public LayerAdapter.LayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.adapter_layer_item, parent, false);
        LayerHolder holder = new LayerHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(LayerAdapter.LayerHolder holder, int position) {

        final LayerEntity layerEntity = mListLayers.get(position);

        String label = layerEntity.getLabel();
        if (Utils.validateString(label)) {
            holder.tvLayer.setText(label);
        }

        if (mSelectedLayer != null) {
            holder.ivCheck.setVisibility(View.GONE);
            switch (mFrom) {
                case Constants.Layer.FILTER:
                    if (mSelectedLayer.getValue().equals(layerEntity.getValue())) {
                        holder.ivCheck.setVisibility(View.VISIBLE);
                    }
                    break;
                case Constants.Layer.SORT:
                    if (mSelectedLayer.getKey().equals(layerEntity.getKey()) && mSelectedLayer.getValue().equals(layerEntity.getValue())) {
                        holder.ivCheck.setVisibility(View.VISIBLE);
                    }
                    break;
                case Constants.Layer.TIME:
                    if (mSelectedLayer.getKey().equals(layerEntity.getKey())) {
                        holder.ivCheck.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        } else {
            holder.ivCheck.setVisibility(View.GONE);
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().clearCurrentFragment();
                mSelectedLayer = layerEntity;
                if(mLayerCallback != null) {
                    mLayerCallback.onLayer(mSelectedLayer);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListLayers.size();
    }

    public class LayerHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlItem;
        private TextView tvLayer;
        private ImageView ivCheck;

        public LayerHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_layer_item);
            tvLayer = (TextView) itemView.findViewById(R.id.tv_layer);
            tvLayer.setTextColor(Color.BLACK);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
        }
    }

    protected void parseData() {
        if (hmData != null) {
            mFrom = (int) hmData.get("from");
            switch (mFrom) {
                case Constants.Layer.FILTER:
                    if (hmData.containsKey("status_layer")) {
                        mSelectedLayer = (LayerEntity) hmData.get("status_layer");
                    }
                    if (hmData.containsKey("list_status_layer")) {
                        mListLayers = (ArrayList<LayerEntity>) hmData.get("list_status_layer");
                    }
                    break;
                case Constants.Layer.SORT:
                    if (hmData.containsKey("sort_layer")) {
                        mSelectedLayer = (LayerEntity) hmData.get("sort_layer");
                    }
                    initListSortLayers();
                    break;
                case Constants.Layer.TIME:
                    if (hmData.containsKey("time_layer")) {
                        mSelectedLayer = (LayerEntity) hmData.get("time_layer");
                    }
                    initListTimeFilters();
                    break;
                default:
                    break;
            }

            mLayerCallback = (LayerCallback) hmData.get("callback");
        }
    }

    protected void initListSortLayers() {
        mListLayers = new ArrayList<>();

        LayerEntity entityIdAsc = new LayerEntity();
        entityIdAsc.setKey("entity_id");
        entityIdAsc.setValue("asc");
        entityIdAsc.setLabel("Id (Low To High)");
        mListLayers.add(entityIdAsc);

        LayerEntity entityIdDesc = new LayerEntity();
        entityIdDesc.setKey("entity_id");
        entityIdDesc.setValue("desc");
        entityIdDesc.setLabel("Id (High To Low)");
        mListLayers.add(entityIdDesc);

        LayerEntity incrementIdAsc = new LayerEntity();
        incrementIdAsc.setKey("increment_id");
        incrementIdAsc.setValue("asc");
        incrementIdAsc.setLabel("Increment Id (Low To High)");
        mListLayers.add(incrementIdAsc);

        LayerEntity incrementIdDesc = new LayerEntity();
        incrementIdDesc.setKey("increment_id");
        incrementIdDesc.setValue("desc");
        incrementIdDesc.setLabel("Increment Id (High To Low)");
        mListLayers.add(incrementIdDesc);

        LayerEntity customerFirstNameAsc = new LayerEntity();
        customerFirstNameAsc.setKey("customer_firstname");
        customerFirstNameAsc.setValue("asc");
        customerFirstNameAsc.setLabel("Firstname (A To Z)");
        mListLayers.add(customerFirstNameAsc);

        LayerEntity customerFirstNameDesc = new LayerEntity();
        customerFirstNameDesc.setKey("customer_firstname");
        customerFirstNameDesc.setValue("desc");
        customerFirstNameDesc.setLabel("Firstname (Z To A)");
        mListLayers.add(customerFirstNameDesc);

        LayerEntity customerLastNameAsc = new LayerEntity();
        customerLastNameAsc.setKey("customer_lastname");
        customerLastNameAsc.setValue("asc");
        customerLastNameAsc.setLabel("Lastname (A To Z)");
        mListLayers.add(customerLastNameAsc);

        LayerEntity customerLastNameDesc = new LayerEntity();
        customerLastNameDesc.setKey("customer_lastname");
        customerLastNameDesc.setValue("desc");
        customerLastNameDesc.setLabel("Lastname (Z To A)");
        mListLayers.add(customerLastNameDesc);

        LayerEntity baseGrandTotalAsc = new LayerEntity();
        baseGrandTotalAsc.setKey("base_grand_total");
        baseGrandTotalAsc.setValue("asc");
        baseGrandTotalAsc.setLabel("Grandtotal (Low To High)");
        mListLayers.add(baseGrandTotalAsc);

        LayerEntity baseGrandTotalDesc = new LayerEntity();
        baseGrandTotalDesc.setKey("base_grand_total");
        baseGrandTotalDesc.setValue("desc");
        baseGrandTotalDesc.setLabel("Grandtotal (High To Low)");
        mListLayers.add(baseGrandTotalDesc);
    }

    protected void initListTimeFilters() {
        mListLayers = new ArrayList<>();

        TimeLayerEntity allTime = new TimeLayerEntity();
        allTime.setLabel("All Time");
        allTime.setKey("all_time");
        mListLayers.add(allTime);

        TimeLayerEntity toDay = new TimeLayerEntity();
        toDay.setFromDate(getToDay());
        toDay.setToDate(getToDay());
        toDay.setLabel("Today");
        toDay.setKey("today");
        mListLayers.add(toDay);

        TimeLayerEntity yesterday = new TimeLayerEntity();
        yesterday.setFromDate(getDate(Calendar.DATE, -1, true));
        yesterday.setToDate(getDate(Calendar.DATE, -1, true));
        yesterday.setLabel("Yesterday");
        yesterday.setKey("yesterday");
        mListLayers.add(yesterday);

        TimeLayerEntity last7Days = new TimeLayerEntity();
        last7Days.setFromDate(getDate(Calendar.DATE, -6, true));
        last7Days.setToDate(getToDay());
        last7Days.setLabel("Last 7 Days");
        last7Days.setKey("7_days");
        mListLayers.add(last7Days);

        TimeLayerEntity currentMonth = new TimeLayerEntity();
        currentMonth.setFromDate(getDate(Calendar.DAY_OF_MONTH, 1, false));
        currentMonth.setToDate(getToDay());
        currentMonth.setLabel("Current Month");
        currentMonth.setKey("current_month");
        mListLayers.add(currentMonth);

        TimeLayerEntity lastMonth = new TimeLayerEntity();
        lastMonth.setFromDate(Utils.getFirstDayOfLastMonth());
        lastMonth.setToDate(Utils.getLastDayOfLastMonth());
        lastMonth.setLabel("Last Month");
        lastMonth.setKey("last_month");
        mListLayers.add(lastMonth);

        TimeLayerEntity threeMonths = new TimeLayerEntity();
        threeMonths.setFromDate(getDate(Calendar.DAY_OF_MONTH, -90, true));
        threeMonths.setToDate(getToDay());
        threeMonths.setLabel("Last 3 Months (90 Days)");
        threeMonths.setKey("3_months");
        mListLayers.add(threeMonths);

        TimeLayerEntity thisYear = new TimeLayerEntity();
        thisYear.setFromDate(getDate(Calendar.DAY_OF_YEAR, 1, false));
        thisYear.setToDate(getToDay());
        thisYear.setLabel("Year To Day");
        thisYear.setKey("this_year");
        mListLayers.add(thisYear);

        TimeLayerEntity twoYears = new TimeLayerEntity();
        twoYears.setFromDate(Utils.getFirstDayOfLastYear());
        twoYears.setToDate(getToDay());
        twoYears.setLabel("2 Years To Day");
        twoYears.setKey("2_years_to_day");
        mListLayers.add(twoYears);
    }

    protected String getDate(int a, int b, boolean add) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (add) {
            cal.add(a, b);
        } else {
            cal.set(a, b);
        }
        return dateFormat.format(cal.getTime());
    }

    protected String getToDay() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

}
