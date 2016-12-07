package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.order.entity.OrderEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/6/2016.
 */

public class LatestOrdersAdapter extends RecyclerView.Adapter<LatestOrdersAdapter.LatestOrderHolder> {

    protected ArrayList<OrderEntity> listOrders;
    protected Context mContext;

    public LatestOrdersAdapter(ArrayList<OrderEntity> listOrders) {
        this.listOrders = listOrders;
    }

    @Override
    public LatestOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_latest_order_item, parent, false);
        LatestOrderHolder holder = new LatestOrderHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(LatestOrderHolder holder, int position) {

        OrderEntity orderEntity = listOrders.get(position);

        String orderID = orderEntity.getIncrementID();
        if(Utils.validateString(orderID)) {
            holder.tvOrderID.setText("#" + orderID);
        }

        String price = orderEntity.getGrandTotal();
        String currency = orderEntity.getOrderCurrencyCode();
        if(Utils.validateString(price) && Utils.validateString(currency)) {
            holder.tvOrderPrice.setText(Utils.getPrice(price, currency));
        }

        String customerFirstName = orderEntity.getCustomerFirstName();
        String customerLastName = orderEntity.getCustomerLastName();
        String name = "";
        if(Utils.validateString(customerFirstName)) {
            name += customerFirstName;
        }
        if(Utils.validateString(customerLastName)) {
            name += customerLastName;
        }
        holder.tvCustomerName.setText(name);

        String customerEmail = orderEntity.getCustomerEmail();
        if(Utils.validateString(customerEmail)) {
            holder.tvCustomerEmail.setText(customerEmail);
        }

    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class LatestOrderHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderID, tvOrderPrice, tvCustomerName, tvCustomerEmail;

        public LatestOrderHolder(View itemView) {
            super(itemView);
            tvOrderID = (TextView) itemView.findViewById(R.id.tv_order_id);
            tvOrderID.setTextColor(AppColor.getInstance().getBlackColor());
            tvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            tvOrderPrice.setTextColor(AppColor.getInstance().getPriceColor());
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerName.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            tvCustomerEmail.setTextColor(AppColor.getInstance().getBlackColor());
        }
    }
}
