package com.simicart.saletracking.order.block;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.component.AddressComponent;
import com.simicart.saletracking.customer.entity.AddressEntity;
import com.simicart.saletracking.order.component.OrderedItemsComponent;
import com.simicart.saletracking.order.entity.FeeEntity;
import com.simicart.saletracking.order.entity.OrderEntity;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailBlock extends AppBlock {

    protected TextView tvOrderSummaryTitle, tvCustomerTitle, tvItemsTitle, tvShippingAddressTitle,
            tvBillingAddressTitle, tvPaymentMethodTitle, tvShippingMethodTitle, tvTotalFeeTitle;
    protected TextView tvIncrementIDLabel, tvCreatedAtLabel, tvUpdatedAtLabel, tvSummaryGrandTotalLabel,
            tvStoreViewLabel, tvStatusLabel, tvCustomerIDLabel, tvCustomerNameLabel, tvCustomerEmailLabel,
            tvPaymentMethodCodeLabel, tvPaymentMethodDescriptionLabel, tvShippingMethodCodeLabel,
            tvShippingMethodDescriptionLabel, tvSubTotalExclLabel, tvSubTotalInclLabel, tvTaxLabel, tvShippingExclLabel,
            tvFeeGrandTotalLabel;
    protected TextView tvIncrementID, tvCreatedAt, tvUpdatedAt, tvSummaryGrandTotal,
            tvStoreView, tvStatus, tvCustomerID, tvCustomerName, tvCustomerEmail,
            tvPaymentMethodCode, tvPaymentMethodDescription, tvShippingMethodCode,
            tvShippingMethodDescription, tvSubTotalExcl, tvSubTotalIncl, tvTax, tvShippingExcl,
            tvFeeGrandTotal;
    protected LinearLayout llItems, llShippingAddress, llBillingAddress;
    protected RelativeLayout rlCustomer;

    public OrderDetailBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initSummary();
        initCustomer();
        initItems();
        initShippingAddress();
        initBillingAddress();
        initPaymentMethod();
        initShippingMethod();
        initTotalFee();
    }

    @Override
    public void updateView(AppCollection collection) {

        if (collection != null) {
            if (collection.containKey("order")) {
                OrderEntity orderEntity = (OrderEntity) collection.getDataWithKey("order");
                showSummary(orderEntity);
                showCustomer(orderEntity);
                showItems(orderEntity);
                showShippingAddress(orderEntity);
                showBillingAddress(orderEntity);
                showPaymentMethod(orderEntity);
                showShippingMethod(orderEntity);
                showTotalFee(orderEntity);
            } else {
                mView.setVisibility(View.INVISIBLE);
            }
        } else {
            mView.setVisibility(View.INVISIBLE);
        }

    }

    protected void initSummary() {

        tvOrderSummaryTitle = (TextView) mView.findViewById(R.id.tv_order_summary_title);
        tvOrderSummaryTitle.setTextColor(Color.BLACK);
        tvOrderSummaryTitle.setText("ORDER SUMMARY");

        tvIncrementIDLabel = (TextView) mView.findViewById(R.id.tv_increment_id_label);
        tvIncrementIDLabel.setTextColor(Color.BLACK);
        tvIncrementIDLabel.setText("Order Increment Id");
        tvIncrementID = (TextView) mView.findViewById(R.id.tv_increment_id);
        tvIncrementID.setTextColor(Color.BLACK);

        tvCreatedAtLabel = (TextView) mView.findViewById(R.id.tv_created_at_label);
        tvCreatedAtLabel.setTextColor(Color.BLACK);
        tvCreatedAtLabel.setText("Created At");
        tvCreatedAt = (TextView) mView.findViewById(R.id.tv_created_at);
        tvCreatedAt.setTextColor(Color.BLACK);

        tvUpdatedAtLabel = (TextView) mView.findViewById(R.id.tv_updated_at_label);
        tvUpdatedAtLabel.setTextColor(Color.BLACK);
        tvUpdatedAtLabel.setText("Last Updated At");
        tvUpdatedAt = (TextView) mView.findViewById(R.id.tv_updated_at);
        tvUpdatedAt.setTextColor(Color.BLACK);

        tvSummaryGrandTotalLabel = (TextView) mView.findViewById(R.id.tv_summary_grand_total_label);
        tvSummaryGrandTotalLabel.setTextColor(Color.BLACK);
        tvSummaryGrandTotalLabel.setText("Grand Total");
        tvSummaryGrandTotal = (TextView) mView.findViewById(R.id.tv_summary_grand_total);
        tvSummaryGrandTotal.setTextColor(Color.BLACK);

        tvStoreViewLabel = (TextView) mView.findViewById(R.id.tv_store_view_label);
        tvStoreViewLabel.setTextColor(Color.BLACK);
        tvStoreViewLabel.setText("Store View");
        tvStoreView = (TextView) mView.findViewById(R.id.tv_store_view);
        tvStoreView.setTextColor(Color.BLACK);

        tvStatusLabel = (TextView) mView.findViewById(R.id.tv_status_label);
        tvStatusLabel.setTextColor(Color.BLACK);
        tvStatusLabel.setText("Status");
        tvStatus = (TextView) mView.findViewById(R.id.tv_status);
        tvStatus.setTextColor(Color.BLACK);

    }

    protected void initCustomer() {

        rlCustomer = (RelativeLayout) mView.findViewById(R.id.rl_customer);

        tvCustomerTitle = (TextView) mView.findViewById(R.id.tv_customer_title);
        tvCustomerTitle.setTextColor(Color.BLACK);
        tvCustomerTitle.setText("CUSTOMER");

        tvCustomerIDLabel = (TextView) mView.findViewById(R.id.tv_customer_id_label);
        tvCustomerIDLabel.setTextColor(Color.BLACK);
        tvCustomerIDLabel.setText("Customer Id");
        tvCustomerID = (TextView) mView.findViewById(R.id.tv_customer_id);
        tvCustomerID.setTextColor(Color.BLACK);

        tvCustomerNameLabel = (TextView) mView.findViewById(R.id.tv_customer_name_label);
        tvCustomerNameLabel.setTextColor(Color.BLACK);
        tvCustomerNameLabel.setText("Customer Name");
        tvCustomerName = (TextView) mView.findViewById(R.id.tv_customer_name);
        tvCustomerName.setTextColor(Color.BLACK);

        tvCustomerEmailLabel = (TextView) mView.findViewById(R.id.tv_customer_email_label);
        tvCustomerEmailLabel.setTextColor(Color.BLACK);
        tvCustomerEmailLabel.setText("Customer Email");
        tvCustomerEmail = (TextView) mView.findViewById(R.id.tv_customer_email);
        tvCustomerEmail.setTextColor(Color.BLACK);

    }

    protected void initItems() {

        tvItemsTitle = (TextView) mView.findViewById(R.id.tv_items_title);
        tvItemsTitle.setTextColor(Color.BLACK);
        tvItemsTitle.setText("ITEMS");

        llItems = (LinearLayout) mView.findViewById(R.id.ll_items);

    }

    protected void initShippingAddress() {

        tvShippingAddressTitle = (TextView) mView.findViewById(R.id.tv_shipping_address_title);
        tvShippingAddressTitle.setTextColor(Color.BLACK);
        tvShippingAddressTitle.setText("SHIPPING ADDRESS");

        llShippingAddress = (LinearLayout) mView.findViewById(R.id.ll_shipping_address);

    }

    protected void initBillingAddress() {

        tvBillingAddressTitle = (TextView) mView.findViewById(R.id.tv_billing_address_title);
        tvBillingAddressTitle.setTextColor(Color.BLACK);
        tvBillingAddressTitle.setText("BILLING ADDRESS");

        llBillingAddress = (LinearLayout) mView.findViewById(R.id.ll_billing_address);

    }

    protected void initPaymentMethod() {

        tvPaymentMethodTitle = (TextView) mView.findViewById(R.id.tv_payment_method_title);
        tvPaymentMethodTitle.setTextColor(Color.BLACK);
        tvPaymentMethodTitle.setText("PAYMENT METHOD");

        tvPaymentMethodCodeLabel = (TextView) mView.findViewById(R.id.tv_payment_method_code_label);
        tvPaymentMethodCodeLabel.setTextColor(Color.BLACK);
        tvPaymentMethodCodeLabel.setText("Method Code");
        tvPaymentMethodCode = (TextView) mView.findViewById(R.id.tv_payment_method_code);
        tvPaymentMethodCode.setTextColor(Color.BLACK);

        tvPaymentMethodDescriptionLabel = (TextView) mView.findViewById(R.id.tv_payment_method_description_label);
        tvPaymentMethodDescriptionLabel.setTextColor(Color.BLACK);
        tvPaymentMethodDescriptionLabel.setText("Description");
        tvPaymentMethodDescription = (TextView) mView.findViewById(R.id.tv_payment_method_description);
        tvPaymentMethodDescription.setTextColor(Color.BLACK);

    }

    protected void initShippingMethod() {

        tvShippingMethodTitle = (TextView) mView.findViewById(R.id.tv_shipping_method_title);
        tvShippingMethodTitle.setTextColor(Color.BLACK);
        tvShippingMethodTitle.setText("SHIPPING METHOD");

        tvShippingMethodCodeLabel = (TextView) mView.findViewById(R.id.tv_shipping_method_code_label);
        tvShippingMethodCodeLabel.setTextColor(Color.BLACK);
        tvShippingMethodCodeLabel.setText("Method Code");
        tvShippingMethodCode = (TextView) mView.findViewById(R.id.tv_shipping_method_code);
        tvShippingMethodCode.setTextColor(Color.BLACK);

        tvShippingMethodDescriptionLabel = (TextView) mView.findViewById(R.id.tv_shipping_method_description_label);
        tvShippingMethodDescriptionLabel.setTextColor(Color.BLACK);
        tvShippingMethodDescriptionLabel.setText("Description");
        tvShippingMethodDescription = (TextView) mView.findViewById(R.id.tv_shipping_method_description);
        tvShippingMethodDescription.setTextColor(Color.BLACK);

    }

    protected void initTotalFee() {

        tvTotalFeeTitle = (TextView) mView.findViewById(R.id.tv_total_fee_title);
        tvTotalFeeTitle.setTextColor(Color.BLACK);
        tvTotalFeeTitle.setText("TOTAL FEE");

        tvSubTotalExclLabel = (TextView) mView.findViewById(R.id.tv_subtotal_excl_label);
        tvSubTotalExclLabel.setTextColor(Color.BLACK);
        tvSubTotalExclLabel.setText("Subtotal (Encl. Tax)");
        tvSubTotalExcl = (TextView) mView.findViewById(R.id.tv_subtotal_excl);
        tvSubTotalExcl.setTextColor(Color.BLACK);

        tvSubTotalInclLabel = (TextView) mView.findViewById(R.id.tv_subtotal_incl_label);
        tvSubTotalInclLabel.setTextColor(Color.BLACK);
        tvSubTotalInclLabel.setText("Subtotal (Incl. Tax)");
        tvSubTotalIncl = (TextView) mView.findViewById(R.id.tv_subtotal_incl);
        tvSubTotalIncl.setTextColor(Color.BLACK);

        tvTaxLabel = (TextView) mView.findViewById(R.id.tv_tax_label);
        tvTaxLabel.setTextColor(Color.BLACK);
        tvTaxLabel.setText("Tax");
        tvTax = (TextView) mView.findViewById(R.id.tv_tax);
        tvTax.setTextColor(Color.BLACK);

        tvShippingExclLabel = (TextView) mView.findViewById(R.id.tv_shipping_excl_label);
        tvShippingExclLabel.setTextColor(Color.BLACK);
        tvShippingExclLabel.setText("Shipping (Excl. Tax)");
        tvShippingExcl = (TextView) mView.findViewById(R.id.tv_shipping_excl);
        tvShippingExcl.setTextColor(Color.BLACK);

        tvFeeGrandTotalLabel = (TextView) mView.findViewById(R.id.tv_fee_grand_total_label);
        tvFeeGrandTotalLabel.setTextColor(Color.BLACK);
        tvFeeGrandTotalLabel.setText("Grandtotal");
        tvFeeGrandTotal = (TextView) mView.findViewById(R.id.tv_fee_grand_total);
        tvFeeGrandTotal.setTextColor(Color.BLACK);

    }

    public void showSummary(OrderEntity orderEntity) {

        String incrementID = orderEntity.getIncrementID();
        if (Utils.validateString(incrementID)) {
            tvIncrementID.setText("#" + incrementID);
        }

        String createdDate = orderEntity.getCreatedAtDate();
        String createdTime = orderEntity.getCreatedAtTime();
        if (Utils.validateString(createdDate) && Utils.validateString(createdTime)) {
            tvCreatedAt.setText(createdDate + " " + createdTime);
        }

        String updatedAt = orderEntity.getUpdatedAt();
        if (Utils.validateString(updatedAt)) {
            tvUpdatedAt.setText(updatedAt);
        }

        String grandTotal = orderEntity.getGrandTotal();
        String currency = orderEntity.getOrderCurrencyCode();
        if (Utils.validateString(grandTotal) && Utils.validateString(currency)) {
            tvSummaryGrandTotal.setText(Utils.getPrice(grandTotal, currency));
        }

        String storeView = orderEntity.getStoreView();
        if (Utils.validateString(storeView)) {
            tvStoreView.setText(storeView);
        }

        String status = orderEntity.getStatus();
        if (Utils.validateString(status)) {
            tvStatus.setText(status);
        }

    }

    protected void showCustomer(OrderEntity orderEntity) {

        String customerID = orderEntity.getCustomerID();
        if (Utils.validateString(customerID)) {
            tvCustomerID.setText("#" + customerID);
        }

        String customerFirstName = orderEntity.getCustomerFirstName();
        String customerLastName = orderEntity.getCustomerLastName();
        if (Utils.validateString(customerFirstName) && Utils.validateString(customerLastName)) {
            tvCustomerName.setText(customerFirstName + " " + customerLastName);
        }

        String customerEmail = orderEntity.getCustomerEmail();
        if (Utils.validateString(customerEmail)) {
            tvCustomerEmail.setText(customerEmail);
        }

    }

    protected void showItems(OrderEntity orderEntity) {

        OrderedItemsComponent orderedItemsComponent = new OrderedItemsComponent();
        orderedItemsComponent.setCart(false);
        orderedItemsComponent.setListProducts(orderEntity.getListProducts());
        orderedItemsComponent.setBaseCurrency(orderEntity.getBaseCurrencyCode());
        orderedItemsComponent.setOrderCurrency(orderEntity.getOrderCurrencyCode());
        View view = orderedItemsComponent.createView();
        if (view != null) {
            llItems.removeAllViewsInLayout();
            llItems.addView(view);
        }

    }

    protected void showShippingAddress(OrderEntity orderEntity) {

        AddressEntity shippingAddress = orderEntity.getShippingAddress();
        if (shippingAddress != null) {
            AddressComponent shippingComponent = new AddressComponent();
            shippingComponent.setAddressEntity(shippingAddress);
            shippingComponent.setShowEmail(true);
            View shippingView = shippingComponent.createView();
            if (shippingView != null) {
                llShippingAddress.removeAllViewsInLayout();
                llShippingAddress.addView(shippingView);
            }
        }

    }

    protected void showBillingAddress(OrderEntity orderEntity) {

        AddressEntity billingAddress = orderEntity.getBillingAddress();
        if (billingAddress != null) {
            AddressComponent billingComponent = new AddressComponent();
            billingComponent.setAddressEntity(billingAddress);
            billingComponent.setShowEmail(true);
            View billingView = billingComponent.createView();
            if (billingView != null) {
                llBillingAddress.removeAllViewsInLayout();
                llBillingAddress.addView(billingView);
            }
        }

    }

    protected void showPaymentMethod(OrderEntity orderEntity) {

        String methodCode = orderEntity.getPaymentMethodCode();
        if (Utils.validateString(methodCode)) {
            tvPaymentMethodCode.setText(methodCode);
        }

        String description = orderEntity.getPaymentMethodDescription();
        if (Utils.validateString(description)) {
            tvPaymentMethodDescription.setText(description);
        }

    }

    protected void showShippingMethod(OrderEntity orderEntity) {

        String methodCode = orderEntity.getShippingMethodCode();
        if (Utils.validateString(methodCode)) {
            tvShippingMethodCode.setText(methodCode);
        }

        String description = orderEntity.getShippingMethodDescription();
        if (Utils.validateString(description)) {
            tvShippingMethodDescription.setText(description);
        }

    }

    protected void showTotalFee(OrderEntity orderEntity) {

        FeeEntity feeEntity = orderEntity.getFee();
        String orderCurrency = orderEntity.getOrderCurrencyCode();
        if (feeEntity != null && Utils.validateString(orderCurrency)) {
            String subtotalExcl = feeEntity.getSubTotalExcl();
            if (Utils.validateString(subtotalExcl)) {
                tvSubTotalExcl.setText(Utils.getPrice(subtotalExcl, orderCurrency));
            }

            String subtotalIncl = feeEntity.getSubTotalIncl();
            if (Utils.validateString(subtotalIncl)) {
                tvSubTotalIncl.setText(Utils.getPrice(subtotalIncl, orderCurrency));
            }

            String tax = feeEntity.getTax();
            if (Utils.validateString(tax)) {
                tvTax.setText(Utils.getPrice(tax, orderCurrency));
            }

            String shippingExcl = feeEntity.getShippingExcl();
            if (Utils.validateString(shippingExcl)) {
                tvShippingExcl.setText(Utils.getPrice(shippingExcl, orderCurrency));
            }

            String grandTotal = feeEntity.getGrandTotal();
            if (Utils.validateString(grandTotal)) {
                tvFeeGrandTotal.setText(Utils.getPrice(grandTotal, orderCurrency));
            }
        }

    }

    public void setOnCustomerClick(View.OnClickListener listener) {
        rlCustomer.setOnClickListener(listener);
    }

}
