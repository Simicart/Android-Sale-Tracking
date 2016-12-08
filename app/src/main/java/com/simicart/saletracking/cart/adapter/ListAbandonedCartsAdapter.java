package com.simicart.saletracking.cart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;
import com.simicart.saletracking.cart.entity.AbandonedCartSection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/8/2016.
 */

public class ListAbandonedCartsAdapter extends SectioningAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<AbandonedCartSection> mListSections;
    protected boolean isEvenItem = true;

    public ListAbandonedCartsAdapter(ArrayList<AbandonedCartSection> sections) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        mListSections = sections;
    }

    public void setListSections(ArrayList<AbandonedCartSection> listSections) {
        mListSections = listSections;
    }

    @Override
    public int getNumberOfSections() {
        return mListSections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mListSections.get(sectionIndex).getListCarts().size();
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
    public ListAbandonedCartsAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        View v = mInflater.inflate(R.layout.adapter_abandoned_cart_item, parent, false);
        return new ListAbandonedCartsAdapter.ItemViewHolder(v);
    }

    @Override
    public ListAbandonedCartsAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View v = mInflater.inflate(R.layout.adapter_header_item, parent, false);
        return new ListAbandonedCartsAdapter.HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {

        ListAbandonedCartsAdapter.ItemViewHolder itemViewHolder = (ListAbandonedCartsAdapter.ItemViewHolder) viewHolder;

        final AbandonedCartEntity cartEntity = mListSections.get(sectionIndex).getListCarts().get(itemIndex);

        if(isEvenItem) {
            itemViewHolder.rlCartItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
            isEvenItem = false;
        } else {
            itemViewHolder.rlCartItem.setBackgroundColor(AppColor.getInstance().getWhiteColor());
            isEvenItem = true;
        }

        final String cartID = cartEntity.getID();
        if(Utils.validateString(cartID)) {
            itemViewHolder.tvCartID.setText(cartID);
        }

        String grandTotal = cartEntity.getGrandTotal();
        String currency = cartEntity.getQuoteCurrencyCode();
        if(Utils.validateString(grandTotal)) {
            itemViewHolder.tvTotalPrice.setText(Utils.getPrice(grandTotal, currency));
        }

        String customerEmail = cartEntity.getCustomerEmail();
        if(Utils.validateString(customerEmail)) {
            itemViewHolder.tvCustomerEmail.setText(customerEmail);
        }

        String qty = cartEntity.getItemQty();
        if(Utils.validateString(qty)) {
            itemViewHolder.tvTotalItem.setText(Utils.formatNumber(qty));
        }

        String ip = cartEntity.getRemoteIP();
        if(Utils.validateString(ip)) {
            itemViewHolder.tvRemoteIP.setText(ip);
        }

        String updatedAt = cartEntity.getUpdatedAt();
        if(Utils.validateString(updatedAt)) {
            itemViewHolder.tvLastUpdated.setText(updatedAt);
        }

        itemViewHolder.rlCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("cart_id", cartID);
                AppManager.getInstance().openAbandonedCartDetail(hmData);
            }
        });

    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {

        ListAbandonedCartsAdapter.HeaderViewHolder headerViewHolder = (ListAbandonedCartsAdapter.HeaderViewHolder) viewHolder;

        AbandonedCartSection customerSection = mListSections.get(sectionIndex);

        String date = customerSection.getDate();
        if(Utils.validateString(date)) {
            headerViewHolder.tvTime.setText(date);
        }

    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        public TextView tvCartID, tvTotalPrice, tvCustomerEmail, tvTotalItem, tvRemoteIP,
                tvLastUpdatedLabel, tvLastUpdated;
        public RelativeLayout rlCartItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvCartID = (TextView) itemView.findViewById(R.id.tv_cart_id);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            tvTotalItem = (TextView) itemView.findViewById(R.id.tv_total_item);
            tvRemoteIP = (TextView) itemView.findViewById(R.id.tv_customer_ip);
            tvLastUpdatedLabel = (TextView) itemView.findViewById(R.id.tv_last_updated_label);
            tvLastUpdated = (TextView) itemView.findViewById(R.id.tv_last_updated);
            rlCartItem = (RelativeLayout) itemView.findViewById(R.id.rl_cart_item);

            tvCartID.setTextColor(Color.BLACK);
            tvTotalPrice.setTextColor(AppColor.getInstance().getPriceColor());
            tvCustomerEmail.setTextColor(Color.BLACK);
            tvTotalItem.setTextColor(Color.BLACK);
            tvRemoteIP.setTextColor(Color.BLACK);
            tvLastUpdatedLabel.setTextColor(Color.BLACK);
            tvLastUpdatedLabel.setText("Last Updated:");
            tvLastUpdated.setTextColor(Color.BLACK);
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
