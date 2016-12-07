package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.CustomerEntity;

import java.util.ArrayList;

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

        CustomerEntity customerEntity = listCustomers.get(position);

        String customerID = customerEntity.getID();
        if(Utils.validateString(customerID)) {
            holder.tvCustomerID.setText(customerID);
        }

        String customerFirstName = customerEntity.getFirstName();
        String customerLastName = customerEntity.getLastName();
        if(Utils.validateString(customerFirstName) && Utils.validateString(customerLastName)) {
            holder.tvCustomerName.setText(customerFirstName + " " + customerLastName);
        }

        String customerEmail = customerEntity.getEmail();
        if(Utils.validateString(customerEmail)) {
            holder.tvCustomerEmail.setText(customerEmail);
        }

    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }

    public class LatestCustomerHolder extends RecyclerView.ViewHolder {

        public TextView tvCustomerID, tvCustomerName, tvCustomerEmail;

        public LatestCustomerHolder(View itemView) {
            super(itemView);
            tvCustomerID = (TextView) itemView.findViewById(R.id.tv_customer_id);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);

            tvCustomerID.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerName.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerEmail.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerEmail.setGravity(Gravity.END);
            tvCustomerEmail.setTypeface(null, Typeface.ITALIC);
        }
    }
}
