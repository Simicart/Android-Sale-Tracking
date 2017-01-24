package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.CustomerEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/6/2016.
 */

public class LatestCustomerAdapter extends RecyclerView.Adapter<LatestCustomerAdapter.LatestCustomerHolder> {

    protected ArrayList<CustomerEntity> listCustomers;
    protected Context mContext;

    public LatestCustomerAdapter(ArrayList<CustomerEntity> listCustomers) {
        this.listCustomers = listCustomers;
    }

    @Override
    public LatestCustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_customer_item, parent, false);
        LatestCustomerHolder holder = new LatestCustomerHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(LatestCustomerHolder holder, int position) {

        final CustomerEntity customerEntity = listCustomers.get(position);

        if(position % 2 == 0) {
            holder.llItem.setBackgroundColor(Color.WHITE);
        } else {
            holder.llItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
        }

        String customerID = customerEntity.getID();
        if (Utils.validateString(customerID)) {
            holder.tvCustomerID.setText(customerID);
        }

        String customerFirstName = customerEntity.getFirstName();
        String customerLastName = customerEntity.getLastName();
        if (Utils.validateString(customerFirstName) && Utils.validateString(customerLastName)) {
            holder.tvCustomerName.setText(customerFirstName + " " + customerLastName);
        }

        String customerEmail = customerEntity.getEmail();
        if (Utils.validateString(customerEmail)) {
            holder.tvCustomerEmail.setText(customerEmail);
        }

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_latest_customer_detail");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("dashboard_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("customer_id", customerEntity.getID());
                AppManager.getInstance().openCustomerDetail(hmData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }

    public class LatestCustomerHolder extends RecyclerView.ViewHolder {

        public TextView tvCustomerID, tvCustomerName, tvCustomerEmail;
        protected LinearLayout llItem;

        public LatestCustomerHolder(View itemView) {
            super(itemView);
            tvCustomerID = (TextView) itemView.findViewById(R.id.tv_customer_id);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item_customer);

            tvCustomerID.setTextColor(Color.BLACK);
            tvCustomerName.setTextColor(Color.BLACK);
            tvCustomerName.setGravity(Gravity.CENTER);
            tvCustomerName.setTypeface(null, Typeface.NORMAL);
            tvCustomerEmail.setTextColor(Color.BLACK);
            tvCustomerEmail.setGravity(Gravity.END);
            tvCustomerEmail.setTypeface(null, Typeface.ITALIC);
            llItem.setPaddingRelative(Utils.toPixel(10), Utils.toPixel(10), Utils.toPixel(10), Utils.toPixel(10));
        }
    }
}
