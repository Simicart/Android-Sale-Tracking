package com.simicart.saletracking.customer.block;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.component.AddressComponent;
import com.simicart.saletracking.customer.entity.AddressEntity;
import com.simicart.saletracking.customer.entity.CustomerEntity;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailBlock extends AppBlock {

    protected TextView tvSummaryTitle, tvInfoTitle, tvOrderTitle, tvAddressTitle, tvBillingTitle, tvShippingTitle;
    protected TextView tvCustomerIDLabel, tvCustomerEmailLabel, tvCustomerFirstNameLabel, tvCustomerLastNameLabel,
            tvCreatedAtLabel, tvUpdatedAtLabel, tvActiveLabel, tvDobLabel, tvCreatedInLabel, tvGenderLabel;
    protected TextView tvCustomerID, tvCustomerEmail, tvCustomerFirstName, tvCustomerLastName,
            tvCreatedAt, tvUpdatedAt, tvActive, tvDob, tvCreatedIn, tvGender, tvOrder, tvAddress;
    protected LinearLayout llBillingAddress, llShippingAddress;

    public CustomerDetailBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        initSummary();
        initInfo();
        initOrder();
        initAddress();
        initBillingAddress();
        initShippingAddress();

    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null) {
            CustomerEntity customerEntity = (CustomerEntity) collection.getDataWithKey("customer");
            if (customerEntity != null) {
                showSummary(customerEntity);
                showInfo(customerEntity);
                showBilling(customerEntity);
                showShipping(customerEntity);
            }
        } else {
            mView.setVisibility(View.INVISIBLE);
        }
    }

    protected void initSummary() {

        tvSummaryTitle = (TextView) mView.findViewById(R.id.tv_summary_title);
        tvSummaryTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvSummaryTitle.setText("CUSTOMER SUMMARY");

        tvCustomerIDLabel = (TextView) mView.findViewById(R.id.tv_customer_id_label);
        tvCustomerIDLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCustomerIDLabel.setText("Customer ID");

        tvCustomerEmailLabel = (TextView) mView.findViewById(R.id.tv_customer_email_label);
        tvCustomerEmailLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCustomerEmailLabel.setText("Customer Email");

        tvCustomerFirstNameLabel = (TextView) mView.findViewById(R.id.tv_first_name_label);
        tvCustomerFirstNameLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCustomerFirstNameLabel.setText("First Name");

        tvCustomerLastNameLabel = (TextView) mView.findViewById(R.id.tv_last_name_label);
        tvCustomerLastNameLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCustomerLastNameLabel.setText("Last Name");

        tvCustomerID = (TextView) mView.findViewById(R.id.tv_customer_id);
        tvCustomerID.setTextColor(AppColor.getInstance().getBlackColor());

        tvCustomerEmail = (TextView) mView.findViewById(R.id.tv_customer_email);
        tvCustomerEmail.setTextColor(AppColor.getInstance().getBlackColor());

        tvCustomerFirstName = (TextView) mView.findViewById(R.id.tv_first_name);
        tvCustomerFirstName.setTextColor(AppColor.getInstance().getBlackColor());

        tvCustomerLastName = (TextView) mView.findViewById(R.id.tv_last_name);
        tvCustomerLastName.setTextColor(AppColor.getInstance().getBlackColor());

    }

    protected void initInfo() {

        tvInfoTitle = (TextView) mView.findViewById(R.id.tv_info_title);
        tvInfoTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvInfoTitle.setText("CUSTOMER INFORMATION");

        tvCreatedAtLabel = (TextView) mView.findViewById(R.id.tv_created_at_label);
        tvCreatedAtLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCreatedAtLabel.setText("Created At");

        tvUpdatedAtLabel = (TextView) mView.findViewById(R.id.tv_updated_at_label);
        tvUpdatedAtLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvUpdatedAtLabel.setText("Last Updated At");

        tvActiveLabel = (TextView) mView.findViewById(R.id.tv_active_label);
        tvActiveLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvActiveLabel.setText("Is Active");

        tvDobLabel = (TextView) mView.findViewById(R.id.tv_dob_label);
        tvDobLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvDobLabel.setText("Date Of Birth");

        tvCreatedInLabel = (TextView) mView.findViewById(R.id.tv_created_in_label);
        tvCreatedInLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCreatedInLabel.setText("Created In");

        tvGenderLabel = (TextView) mView.findViewById(R.id.tv_gender_label);
        tvGenderLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvGenderLabel.setText("Gender");

        tvCreatedAt = (TextView) mView.findViewById(R.id.tv_created_at);
        tvCreatedAt.setTextColor(AppColor.getInstance().getBlackColor());

        tvUpdatedAt = (TextView) mView.findViewById(R.id.tv_updated_at);
        tvUpdatedAt.setTextColor(AppColor.getInstance().getBlackColor());

        tvActive = (TextView) mView.findViewById(R.id.tv_active);
        tvActive.setTextColor(AppColor.getInstance().getBlackColor());

        tvDob = (TextView) mView.findViewById(R.id.tv_dob);
        tvDob.setTextColor(AppColor.getInstance().getBlackColor());

        tvCreatedIn = (TextView) mView.findViewById(R.id.tv_created_in);
        tvCreatedIn.setTextColor(AppColor.getInstance().getBlackColor());

        tvGender = (TextView) mView.findViewById(R.id.tv_gender);
        tvGender.setTextColor(AppColor.getInstance().getBlackColor());

    }

    protected void initOrder() {

        tvOrderTitle = (TextView) mView.findViewById(R.id.tv_order_title);
        tvOrderTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvOrderTitle.setText("CUSTOMER ORDERS");

        tvOrder = (TextView) mView.findViewById(R.id.tv_order);
        tvOrder.setTextColor(AppColor.getInstance().getBlackColor());
        tvOrder.setText("VIEW CUSTOMER ORDERS");

    }

    protected void initAddress() {

        tvAddressTitle = (TextView) mView.findViewById(R.id.tv_address_title);
        tvAddressTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvAddressTitle.setText("CUSTOMER ADDRESSES");

        tvAddress = (TextView) mView.findViewById(R.id.tv_address);
        tvAddress.setTextColor(AppColor.getInstance().getBlackColor());
        tvAddress.setText("VIEW CUSTOMER ADDRESSES");

    }

    protected void initBillingAddress() {

        tvBillingTitle = (TextView) mView.findViewById(R.id.tv_billing_title);
        tvBillingTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvBillingTitle.setText("DEFAULT BILLING ADDRESS");

        llBillingAddress = (LinearLayout) mView.findViewById(R.id.ll_billing_address);

    }

    protected void initShippingAddress() {

        tvShippingTitle = (TextView) mView.findViewById(R.id.tv_shipping_title);
        tvShippingTitle.setTextColor(AppColor.getInstance().getBlackColor());
        tvShippingTitle.setText("DEFAULT SHIPPING ADDRESS");

        llShippingAddress = (LinearLayout) mView.findViewById(R.id.ll_shipping_address);

    }

    protected void showSummary(CustomerEntity customerEntity) {

        String id = customerEntity.getID();
        if (Utils.validateString(id)) {
            tvCustomerID.setText(id);
        }

        String email = customerEntity.getEmail();
        if (Utils.validateString(email)) {
            tvCustomerEmail.setText(email);
        }

        String firstName = customerEntity.getFirstName();
        if (Utils.validateString(firstName)) {
            tvCustomerFirstName.setText(firstName);
        }

        String lastName = customerEntity.getLastName();
        if (Utils.validateString(lastName)) {
            tvCustomerLastName.setText(lastName);
        }

    }

    protected void showInfo(CustomerEntity customerEntity) {

        String createdAtDate = customerEntity.getCreatedAtDate();
        String createdAtTime = customerEntity.getCreatedAtTime();
        if (Utils.validateString(createdAtDate) && Utils.validateString(createdAtTime)) {
            tvCreatedAt.setText(createdAtDate + " " + createdAtTime);
        }

        String updatedAt = customerEntity.getUpdatedAt();
        if (Utils.validateString(updatedAt)) {
            tvUpdatedAt.setText(updatedAt);
        }

        if (customerEntity.isActive()) {
            tvActive.setText("Yes");
        } else {
            tvActive.setText("No");
        }

        String dob = customerEntity.getDob();
        if (Utils.validateString(dob)) {
            tvDob.setText(dob);
        }

        String createdIn = customerEntity.getCreatedIn();
        if (Utils.validateString(createdIn)) {
            tvCreatedIn.setText(createdIn);
        }

        String gender = customerEntity.getGender();
        if (Utils.validateString(gender)) {
            tvGender.setText(gender);
        }

    }

    protected void showBilling(CustomerEntity customerEntity) {

        AddressEntity billingAddress = customerEntity.getBillingAddress();
        if(billingAddress != null) {
            AddressComponent billingAddressComponent = new AddressComponent();
            billingAddressComponent.setAddressEntity(billingAddress);
            billingAddressComponent.setShowEmail(false);

            llBillingAddress.removeAllViewsInLayout();
            llBillingAddress.addView(billingAddressComponent.createView());
        }

    }

    protected void showShipping(CustomerEntity customerEntity) {

        AddressEntity shippingAddress = customerEntity.getShippingAddress();
        if(shippingAddress != null) {
            AddressComponent shippingAddressComponent = new AddressComponent();
            shippingAddressComponent.setAddressEntity(shippingAddress);
            shippingAddressComponent.setShowEmail(false);

            llShippingAddress.removeAllViewsInLayout();
            llShippingAddress.addView(shippingAddressComponent.createView());
        }

    }

    public void setOnCustomerOrdersClick(View.OnClickListener listener) {
        tvOrder.setOnClickListener(listener);
    }

    public void setOnCustomerAddressesClick(View.OnClickListener listener) {
        tvAddress.setOnClickListener(listener);
    }

}
