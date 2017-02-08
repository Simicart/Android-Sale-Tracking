package com.simicart.saletracking.customer.block;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.component.AddressComponent;
import com.simicart.saletracking.customer.entity.AddressEntity;
import com.simicart.saletracking.customer.entity.CustomerEntity;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailBlock extends AppBlock {

    protected TextView tvSummaryTitle, tvInfoTitle, tvOrderTitle, tvAddressTitle, tvBillingTitle, tvShippingTitle;
    protected ImageView ivEditSummary, ivEditInfo;
    protected TextView tvCustomerIDLabel, tvCustomerEmailLabel, tvCustomerPrefixLabel, tvCustomerFirstNameLabel, tvCustomerMiddleNameLabel, tvCustomerLastNameLabel, tvCustomerSuffixLabel,
            tvCreatedAtLabel, tvUpdatedAtLabel, tvActiveLabel, tvDobLabel, tvCreatedInLabel, tvGenderLabel, tvTaxVATLabel;
    protected TextView tvCustomerID, tvCustomerEmail, tvCustomerPrefix, tvCustomerFirstName, tvCustomerMiddleName, tvCustomerLastName, tvCustomerSuffix,
            tvCreatedAt, tvUpdatedAt, tvActive, tvDob, tvCreatedIn, tvGender, tvOrder, tvAddress, tvTaxVAT;
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
            if(collection.containKey("customer")) {
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
        } else {
            mView.setVisibility(View.INVISIBLE);
        }
    }

    protected void initSummary() {

        tvSummaryTitle = (TextView) mView.findViewById(R.id.tv_summary_title);
        tvSummaryTitle.setText("Customer Summary");

        ivEditSummary = (ImageView) mView.findViewById(R.id.iv_edit_summary);

        tvCustomerIDLabel = (TextView) mView.findViewById(R.id.tv_customer_id_label);
        tvCustomerIDLabel.setTextColor(Color.BLACK);
        tvCustomerIDLabel.setText("Customer ID");

        tvCustomerEmailLabel = (TextView) mView.findViewById(R.id.tv_customer_email_label);
        tvCustomerEmailLabel.setTextColor(Color.BLACK);
        tvCustomerEmailLabel.setText("Customer Email");

        tvCustomerPrefixLabel = (TextView) mView.findViewById(R.id.tv_prefix_label);
        tvCustomerPrefixLabel.setTextColor(Color.BLACK);
        tvCustomerPrefixLabel.setText("Prefix");

        tvCustomerFirstNameLabel = (TextView) mView.findViewById(R.id.tv_first_name_label);
        tvCustomerFirstNameLabel.setTextColor(Color.BLACK);
        tvCustomerFirstNameLabel.setText("First Name");

        tvCustomerMiddleNameLabel = (TextView) mView.findViewById(R.id.tv_middle_name_label);
        tvCustomerMiddleNameLabel.setTextColor(Color.BLACK);
        tvCustomerMiddleNameLabel.setText("Middle Name");

        tvCustomerLastNameLabel = (TextView) mView.findViewById(R.id.tv_last_name_label);
        tvCustomerLastNameLabel.setTextColor(Color.BLACK);
        tvCustomerLastNameLabel.setText("Last Name");

        tvCustomerSuffixLabel = (TextView) mView.findViewById(R.id.tv_suffix_label);
        tvCustomerSuffixLabel.setTextColor(Color.BLACK);
        tvCustomerSuffixLabel.setText("Suffix");

        tvCustomerID = (TextView) mView.findViewById(R.id.tv_customer_id);
        tvCustomerID.setTextColor(Color.BLACK);

        tvCustomerEmail = (TextView) mView.findViewById(R.id.tv_customer_email);
        tvCustomerEmail.setTextColor(Color.BLACK);

        tvCustomerPrefix = (TextView) mView.findViewById(R.id.tv_prefix);
        tvCustomerPrefix.setTextColor(Color.BLACK);

        tvCustomerFirstName = (TextView) mView.findViewById(R.id.tv_first_name);
        tvCustomerFirstName.setTextColor(Color.BLACK);

        tvCustomerMiddleName = (TextView) mView.findViewById(R.id.tv_middle_name);
        tvCustomerMiddleName.setTextColor(Color.BLACK);

        tvCustomerLastName = (TextView) mView.findViewById(R.id.tv_last_name);
        tvCustomerLastName.setTextColor(Color.BLACK);

        tvCustomerSuffix = (TextView) mView.findViewById(R.id.tv_suffix);
        tvCustomerSuffix.setTextColor(Color.BLACK);

    }

    protected void initInfo() {

        tvInfoTitle = (TextView) mView.findViewById(R.id.tv_info_title);
        tvInfoTitle.setText("Customer Information");

        ivEditInfo = (ImageView) mView.findViewById(R.id.iv_edit_info);

        tvCreatedAtLabel = (TextView) mView.findViewById(R.id.tv_created_at_label);
        tvCreatedAtLabel.setTextColor(Color.BLACK);
        tvCreatedAtLabel.setText("Created At");

        tvUpdatedAtLabel = (TextView) mView.findViewById(R.id.tv_updated_at_label);
        tvUpdatedAtLabel.setTextColor(Color.BLACK);
        tvUpdatedAtLabel.setText("Last Updated At");

        tvActiveLabel = (TextView) mView.findViewById(R.id.tv_active_label);
        tvActiveLabel.setTextColor(Color.BLACK);
        tvActiveLabel.setText("Is Active");

        tvDobLabel = (TextView) mView.findViewById(R.id.tv_dob_label);
        tvDobLabel.setTextColor(Color.BLACK);
        tvDobLabel.setText("Date Of Birth");

        tvCreatedInLabel = (TextView) mView.findViewById(R.id.tv_created_in_label);
        tvCreatedInLabel.setTextColor(Color.BLACK);
        tvCreatedInLabel.setText("Created In");

        tvGenderLabel = (TextView) mView.findViewById(R.id.tv_gender_label);
        tvGenderLabel.setTextColor(Color.BLACK);
        tvGenderLabel.setText("Gender");

        tvTaxVATLabel = (TextView) mView.findViewById(R.id.tv_taxvat_label);
        tvTaxVATLabel.setTextColor(Color.BLACK);
        tvTaxVATLabel.setText("Tax VAT");

        tvCreatedAt = (TextView) mView.findViewById(R.id.tv_created_at);
        tvCreatedAt.setTextColor(Color.BLACK);

        tvUpdatedAt = (TextView) mView.findViewById(R.id.tv_updated_at);
        tvUpdatedAt.setTextColor(Color.BLACK);

        tvActive = (TextView) mView.findViewById(R.id.tv_active);
        tvActive.setTextColor(Color.BLACK);

        tvDob = (TextView) mView.findViewById(R.id.tv_dob);
        tvDob.setTextColor(Color.BLACK);

        tvCreatedIn = (TextView) mView.findViewById(R.id.tv_created_in);
        tvCreatedIn.setTextColor(Color.BLACK);

        tvGender = (TextView) mView.findViewById(R.id.tv_gender);
        tvGender.setTextColor(Color.BLACK);

        tvTaxVAT = (TextView) mView.findViewById(R.id.tv_taxvat);
        tvTaxVAT.setTextColor(Color.BLACK);

    }

    protected void initOrder() {

        tvOrderTitle = (TextView) mView.findViewById(R.id.tv_order_title);
        tvOrderTitle.setText("Customer Orders");

        tvOrder = (TextView) mView.findViewById(R.id.tv_order);
        tvOrder.setTextColor(Color.BLACK);
        tvOrder.setText("View Customer Orders");

    }

    protected void initAddress() {

        tvAddressTitle = (TextView) mView.findViewById(R.id.tv_address_title);
        tvAddressTitle.setText("Customer Addresses");

        tvAddress = (TextView) mView.findViewById(R.id.tv_address);
        tvAddress.setTextColor(Color.BLACK);
        tvAddress.setText("View Customer Addresses");

    }

    protected void initBillingAddress() {

        tvBillingTitle = (TextView) mView.findViewById(R.id.tv_billing_title);
        tvBillingTitle.setText("Default Billing Address");

        llBillingAddress = (LinearLayout) mView.findViewById(R.id.ll_billing_address);

    }

    protected void initShippingAddress() {

        tvShippingTitle = (TextView) mView.findViewById(R.id.tv_shipping_title);
        tvShippingTitle.setText("Default Shipping Address");

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

        String prefix = customerEntity.getPrefix();
        if(Utils.validateString(prefix)) {
            tvCustomerPrefix.setText(prefix);
        }

        String firstName = customerEntity.getFirstName();
        if (Utils.validateString(firstName)) {
            tvCustomerFirstName.setText(firstName);
        }

        String middleName = customerEntity.getMiddleName();
        if (Utils.validateString(middleName)) {
            tvCustomerMiddleName.setText(middleName);
        }

        String lastName = customerEntity.getLastName();
        if (Utils.validateString(lastName)) {
            tvCustomerLastName.setText(lastName);
        }

        String suffix = customerEntity.getSuffix();
        if(Utils.validateString(suffix)) {
            tvCustomerSuffix.setText(suffix);
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

        String taxvat = customerEntity.getTaxVAT();
        if (Utils.validateString(taxvat)) {
            tvTaxVAT.setText(taxvat);
        }

    }

    protected void showBilling(CustomerEntity customerEntity) {

        AddressEntity billingAddress = customerEntity.getBillingAddress();
        if (billingAddress != null) {
            AddressComponent billingAddressComponent = new AddressComponent();
            billingAddressComponent.setAddressEntity(billingAddress);
            billingAddressComponent.setShowEmail(false);

            llBillingAddress.removeAllViewsInLayout();
            llBillingAddress.addView(billingAddressComponent.createView());
        }

    }

    protected void showShipping(CustomerEntity customerEntity) {

        AddressEntity shippingAddress = customerEntity.getShippingAddress();
        if (shippingAddress != null) {
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

    public void setOnEditSummaryClick(View.OnClickListener listener) {
        ivEditSummary.setOnClickListener(listener);
    }

    public void setOnEditInfoClick(View.OnClickListener listener) {
        ivEditInfo.setOnClickListener(listener);
    }

}
