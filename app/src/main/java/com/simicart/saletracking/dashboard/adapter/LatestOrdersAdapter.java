package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.order.entity.OrderEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

        final OrderEntity orderEntity = listOrders.get(position);

        if(position % 2 == 0) {
            holder.llItem.setBackgroundColor(Color.WHITE);
        } else {
            holder.llItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
        }

        String orderID = orderEntity.getIncrementID();
        if (Utils.validateString(orderID)) {
            holder.tvOrderID.setText("#" + orderID);
        }

        String price = orderEntity.getGrandTotal();
        String currency = orderEntity.getOrderCurrencyCode();
        if (Utils.validateString(price) && Utils.validateString(currency)) {
            holder.tvOrderPrice.setText(Utils.getPrice(price, currency));
        }

        String customerFirstName = orderEntity.getCustomerFirstName();
        String customerLastName = orderEntity.getCustomerLastName();
        String name = "";
        if (Utils.validateString(customerFirstName)) {
            name += customerFirstName;
        }
        if (Utils.validateString(customerLastName)) {
            name += customerLastName;
        }
        holder.tvCustomerName.setText(name);

        String customerEmail = orderEntity.getCustomerEmail();
        if (Utils.validateString(customerEmail)) {
            holder.tvCustomerEmail.setText(customerEmail);
        }

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_latest_order_detail");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("dashboard_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("order_id", orderEntity.getID());
                AppManager.getInstance().openOrderDetail(hmData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class LatestOrderHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderID, tvOrderPrice, tvCustomerName, tvCustomerEmail;
        protected LinearLayout llItem;

        public LatestOrderHolder(View itemView) {
            super(itemView);
            tvOrderID = (TextView) itemView.findViewById(R.id.tv_order_id);
            tvOrderID.setTextColor(Color.BLACK);
            tvOrderPrice = (TextView) itemView.findViewById(R.id.tv_order_price);
            tvOrderPrice.setTextColor(AppColor.getInstance().getPriceColor());
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerName.setTextColor(Color.BLACK);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item_order);
        }
    }
}
