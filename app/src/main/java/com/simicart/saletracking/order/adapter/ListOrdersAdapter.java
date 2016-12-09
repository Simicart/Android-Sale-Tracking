package com.simicart.saletracking.order.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.simicart.saletracking.order.entity.OrderSection;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/28/2016.
 */

public class ListOrdersAdapter extends SectioningAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<OrderSection> mListSections;
    protected boolean isEvenItem = true;

    public ListOrdersAdapter(ArrayList<OrderSection> sections) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        mListSections = sections;
    }

    public void setListSections(ArrayList<OrderSection> listSections) {
        mListSections = listSections;
    }

    @Override
    public int getNumberOfSections() {
        return mListSections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mListSections.get(sectionIndex).getListOrders().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        View v = mInflater.inflate(R.layout.adapter_order_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View v = mInflater.inflate(R.layout.adapter_header_item, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

        final OrderEntity orderEntity = mListSections.get(sectionIndex).getListOrders().get(itemIndex);

        if (isEvenItem) {
            itemViewHolder.llOrderItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
            isEvenItem = false;
        } else {
            itemViewHolder.llOrderItem.setBackgroundColor(Color.WHITE);
            isEvenItem = true;
        }

        String incrementID = orderEntity.getIncrementID();
        if (Utils.validateString(incrementID)) {
            itemViewHolder.tvIncrementID.setText("#" + incrementID);
        }

        String customerFirstName = orderEntity.getCustomerFirstName();
        String customerLastName = orderEntity.getCustomerLastName();
        if (Utils.validateString(customerFirstName) && Utils.validateString(customerLastName)) {
            itemViewHolder.tvCustomerName.setText(customerFirstName + " " + customerLastName);
        }

        String customerEmail = orderEntity.getCustomerEmail();
        if (Utils.validateString(customerEmail)) {
            itemViewHolder.tvCustomerEmail.setText(customerEmail);
        }

        String orderStatus = orderEntity.getStatus();
        if (Utils.validateString(orderStatus)) {
            itemViewHolder.tvOrderStatus.setText(orderStatus);
            switch (orderStatus) {
                case "N/A":
                    itemViewHolder.tvOrderStatus.setTextColor(AppColor.getInstance().getOrderNAColor());
                    itemViewHolder.vStatus.setBackgroundColor(AppColor.getInstance().getOrderNAColor());
                    break;
                case "pending":
                    itemViewHolder.tvOrderStatus.setTextColor(AppColor.getInstance().getOrderPendingColor());
                    itemViewHolder.vStatus.setBackgroundColor(AppColor.getInstance().getOrderPendingColor());
                    break;
                case "complete":
                    itemViewHolder.tvOrderStatus.setTextColor(AppColor.getInstance().getOrderCompletedColor());
                    itemViewHolder.vStatus.setBackgroundColor(AppColor.getInstance().getOrderCompletedColor());
                    break;
                case "canceled":
                    itemViewHolder.tvOrderStatus.setTextColor(AppColor.getInstance().getOrderCanceledColor());
                    itemViewHolder.vStatus.setBackgroundColor(AppColor.getInstance().getOrderCanceledColor());
                    break;
                case "processing":
                    itemViewHolder.tvOrderStatus.setTextColor(AppColor.getInstance().getOrderProcessingColor());
                    itemViewHolder.vStatus.setBackgroundColor(AppColor.getInstance().getOrderProcessingColor());
                    break;
                default:
                    itemViewHolder.tvOrderStatus.setTextColor(Color.BLACK);
                    itemViewHolder.vStatus.setBackgroundColor(Color.WHITE);
                    break;
            }
        }

        String createdAt = orderEntity.getCreatedAtTime();
        if (Utils.validateString(createdAt)) {
            itemViewHolder.tvCreatedAt.setText(createdAt);
        }

        String grandTotal = orderEntity.getGrandTotal();
        String currency = orderEntity.getOrderCurrencyCode();
        if (Utils.validateString(grandTotal) && Utils.validateString(currency)) {
            itemViewHolder.tvGrandTotal.setText(Utils.getPrice(grandTotal, currency));
        }

        itemViewHolder.llOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hmData = new HashMap<String, Object>();
                hmData.put("order_id", orderEntity.getID());
                AppManager.getInstance().openOrderDetail(hmData);
            }
        });

    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {

        HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

        OrderSection orderSection = mListSections.get(sectionIndex);

        String date = orderSection.getDate();
        if (Utils.validateString(date)) {
            headerViewHolder.tvTime.setText(date);
        }

    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        public TextView tvIncrementID, tvCustomerName, tvCustomerEmail, tvOrderStatus, tvCreatedAt, tvGrandTotal;
        public LinearLayout llOrderItem;
        public View vStatus;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvIncrementID = (TextView) itemView.findViewById(R.id.tv_increment_id);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tv_order_status);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tv_created_at);
            tvGrandTotal = (TextView) itemView.findViewById(R.id.tv_grand_total);
            llOrderItem = (LinearLayout) itemView.findViewById(R.id.ll_item_order);
            vStatus = (View) itemView.findViewById(R.id.view_status);

            tvIncrementID.setTextColor(Color.BLACK);
            tvCustomerName.setTextColor(Color.BLACK);
            tvCustomerEmail.setTextColor(Color.BLACK);
            tvCreatedAt.setTextColor(Color.BLACK);
            tvGrandTotal.setTextColor(Color.BLACK);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        public TextView tvTime;
        public LinearLayout llHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvTime.setTextColor(Color.WHITE);
            llHeader = (LinearLayout) itemView.findViewById(R.id.ll_header);
            llHeader.setBackgroundColor(AppColor.getInstance().getButtonColor());
        }
    }

}
