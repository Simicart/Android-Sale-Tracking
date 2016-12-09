package com.simicart.saletracking.cart.block;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;
import com.simicart.saletracking.cart.entity.QuoteItemEntity;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.order.component.OrderedItemsComponent;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartDetailBlock extends AppBlock {

    protected TextView tvSummaryTitle, tvItemsTitle;
    protected TextView tvCustomerEmailLabel, tvCustomerIpLabel, tvGrandTotalLabel, tvSubTotalLabel,
            tvCreatedAtLabel, tvUpdatedAtLabel;
    protected TextView tvCustomerEmail, tvCustomerIp, tvGrandTotal, tvSubTotal, tvCreatedAt, tvUpdatedAt;
    protected LinearLayout llCartItems;
    protected AbandonedCartEntity mAbandonedCart;

    public AbandonedCartDetailBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initSummary();
        initItems();
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("abandonedcart")) {
                mAbandonedCart = (AbandonedCartEntity) collection.getDataWithKey("abandonedcart");
                if (mAbandonedCart != null) {
                    showSummary();
                    showItems();
                }
            }
        }
    }

    protected void initSummary() {
        tvSummaryTitle = (TextView) mView.findViewById(R.id.tv_summary_title);
        tvSummaryTitle.setTextColor(Color.BLACK);
        tvSummaryTitle.setText("CART SUMMARY");

        tvCustomerEmailLabel = (TextView) mView.findViewById(R.id.tv_customer_email_label);
        tvCustomerEmailLabel.setTextColor(Color.BLACK);
        tvCustomerEmailLabel.setText("Customer Email");

        tvCustomerEmail = (TextView) mView.findViewById(R.id.tv_customer_email);
        tvCustomerEmail.setTextColor(Color.BLACK);

        tvCustomerIpLabel = (TextView) mView.findViewById(R.id.tv_customer_ip_label);
        tvCustomerIpLabel.setTextColor(Color.BLACK);
        tvCustomerIpLabel.setText("Customer Ip");

        tvCustomerIp = (TextView) mView.findViewById(R.id.tv_customer_ip);
        tvCustomerIp.setTextColor(Color.BLACK);

        tvGrandTotalLabel = (TextView) mView.findViewById(R.id.tv_grand_total_label);
        tvGrandTotalLabel.setTextColor(Color.BLACK);
        tvGrandTotalLabel.setText("Grandtotal");

        tvGrandTotal = (TextView) mView.findViewById(R.id.tv_grand_total);
        tvGrandTotal.setTextColor(Color.BLACK);

        tvSubTotalLabel = (TextView) mView.findViewById(R.id.tv_subtotal_label);
        tvSubTotalLabel.setTextColor(Color.BLACK);
        tvSubTotalLabel.setText("Subtotal");

        tvSubTotal = (TextView) mView.findViewById(R.id.tv_subtotal);
        tvSubTotal.setTextColor(Color.BLACK);

        tvCreatedAtLabel = (TextView) mView.findViewById(R.id.tv_created_at_label);
        tvCreatedAtLabel.setTextColor(Color.BLACK);
        tvCreatedAtLabel.setText("Created At");

        tvCreatedAt = (TextView) mView.findViewById(R.id.tv_created_at);
        tvCreatedAt.setTextColor(Color.BLACK);

        tvUpdatedAtLabel = (TextView) mView.findViewById(R.id.tv_updated_at_label);
        tvUpdatedAtLabel.setTextColor(Color.BLACK);
        tvUpdatedAtLabel.setText("Updated At");

        tvUpdatedAt = (TextView) mView.findViewById(R.id.tv_updated_at);
        tvUpdatedAt.setTextColor(Color.BLACK);
    }

    protected void initItems() {
        tvItemsTitle = (TextView) mView.findViewById(R.id.tv_items_title);
        tvItemsTitle.setTextColor(Color.BLACK);
        tvItemsTitle.setText("CART ITEMS");

        llCartItems = (LinearLayout) mView.findViewById(R.id.ll_cart_items);
    }

    protected void showSummary() {
        String grandTotal = mAbandonedCart.getGrandTotal();
        String currency = mAbandonedCart.getQuoteCurrencyCode();
        if (Utils.validateString(grandTotal)) {
            tvGrandTotal.setText(Utils.getPrice(grandTotal, currency));
        }

        String customerEmail = mAbandonedCart.getCustomerEmail();
        if (Utils.validateString(customerEmail)) {
            tvCustomerEmail.setText(customerEmail);
        }

        String ip = mAbandonedCart.getRemoteIP();
        if (Utils.validateString(ip)) {
            tvCustomerIp.setText(ip);
        }

        String updatedAt = mAbandonedCart.getUpdatedAt();
        if (Utils.validateString(updatedAt)) {
            tvUpdatedAt.setText(updatedAt);
        }

        String subTotal = mAbandonedCart.getSubTotal();
        if (Utils.validateString(subTotal)) {
            tvSubTotal.setText(Utils.getPrice(subTotal, currency));
        }

        String createdAt = mAbandonedCart.getCreatedAt();
        if (Utils.validateString(createdAt)) {
            tvCreatedAt.setText(createdAt);
        }
    }

    protected void showItems() {
        ArrayList<QuoteItemEntity> listQuotes = mAbandonedCart.getListQuotes();
        if (listQuotes != null) {
            OrderedItemsComponent orderedItemsComponent = new OrderedItemsComponent();
            orderedItemsComponent.setCart(true);
            orderedItemsComponent.setListQuotes(listQuotes);
            orderedItemsComponent.setBaseCurrency(mAbandonedCart.getBaseCurrencyCode());
            orderedItemsComponent.setOrderCurrency(mAbandonedCart.getQuoteCurrencyCode());
            View view = orderedItemsComponent.createView();
            if (view != null) {
                llCartItems.removeAllViewsInLayout();
                llCartItems.addView(view);
            }
        }
    }

}
