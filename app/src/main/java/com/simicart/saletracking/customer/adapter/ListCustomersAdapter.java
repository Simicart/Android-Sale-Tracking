package com.simicart.saletracking.customer.adapter;

import android.content.Context;
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
import com.simicart.saletracking.customer.entity.CustomerSection;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/30/2016.
 */

public class ListCustomersAdapter extends SectioningAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ArrayList<CustomerSection> mListSections;
    protected boolean isEvenItem = true;

    public ListCustomersAdapter(ArrayList<CustomerSection> sections) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        mListSections = sections;
    }

    public void setListSections(ArrayList<CustomerSection> listSections) {
        mListSections = listSections;
    }

    @Override
    public int getNumberOfSections() {
        return mListSections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mListSections.get(sectionIndex).getListCustomers().size();
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
    public ListCustomersAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        View v = mInflater.inflate(R.layout.adapter_customer_item, parent, false);
        return new ListCustomersAdapter.ItemViewHolder(v);
    }

    @Override
    public ListCustomersAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View v = mInflater.inflate(R.layout.adapter_item_header, parent, false);
        return new ListCustomersAdapter.HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {

        ListCustomersAdapter.ItemViewHolder itemViewHolder = (ListCustomersAdapter.ItemViewHolder) viewHolder;

        final CustomerEntity customerEntity = mListSections.get(sectionIndex).getListCustomers().get(itemIndex);

        if(isEvenItem) {
            itemViewHolder.llCustomerItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
            isEvenItem = false;
        } else {
            itemViewHolder.llCustomerItem.setBackgroundColor(AppColor.getInstance().getWhiteColor());
            isEvenItem = true;
        }

        String customerID = customerEntity.getID();
        if(Utils.validateString(customerID)) {
            itemViewHolder.tvCustomerID.setText(customerID);
        }

        String customerFirstName = customerEntity.getFirstName();
        String customerLastName = customerEntity.getLastName();
        if(Utils.validateString(customerFirstName) && Utils.validateString(customerLastName)) {
            itemViewHolder.tvCustomerName.setText(customerFirstName + " " + customerLastName);
        }

        String customerEmail = customerEntity.getEmail();
        if(Utils.validateString(customerEmail)) {
            itemViewHolder.tvCustomerEmail.setText(customerEmail);
        }

        itemViewHolder.llCustomerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("customer_id", customerEntity.getID());
                AppManager.getInstance().openCustomerDetail(hmData);
            }
        });

    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {

        ListCustomersAdapter.HeaderViewHolder headerViewHolder = (ListCustomersAdapter.HeaderViewHolder) viewHolder;

        CustomerSection customerSection = mListSections.get(sectionIndex);

        String date = customerSection.getDate();
        if(Utils.validateString(date)) {
            headerViewHolder.tvTime.setText(date);
        }

    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        public TextView tvCustomerID, tvCustomerName, tvCustomerEmail;
        public LinearLayout llCustomerItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvCustomerID = (TextView) itemView.findViewById(R.id.tv_customer_id);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tv_customer_email);
            llCustomerItem = (LinearLayout) itemView.findViewById(R.id.ll_item_customer);

            tvCustomerID.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerName.setTextColor(AppColor.getInstance().getBlackColor());
            tvCustomerEmail.setTextColor(AppColor.getInstance().getBlackColor());
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        public TextView tvTime;
        public LinearLayout llHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvTime.setTextColor(AppColor.getInstance().getWhiteColor());
            llHeader = (LinearLayout) itemView.findViewById(R.id.ll_header);
            llHeader.setBackgroundColor(AppColor.getInstance().getButtonColor());
        }
    }

}
