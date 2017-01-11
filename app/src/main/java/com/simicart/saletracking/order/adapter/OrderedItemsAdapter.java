package com.simicart.saletracking.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.cart.entity.QuoteItemEntity;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderedItemsAdapter extends RecyclerView.Adapter<OrderedItemsAdapter.ProductCheckoutHolder> {

    protected ArrayList<ProductEntity> listProducts;
    protected ArrayList<QuoteItemEntity> listQuotes;
    protected Context mContext;
    protected String mBaseCurrency;
    protected String mOrderCurrency;
    protected boolean isCart;

    public OrderedItemsAdapter(String baseCurrency, String orderCurrency) {
        this.mBaseCurrency = baseCurrency;
        this.mOrderCurrency = orderCurrency;
    }

    @Override
    public ProductCheckoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_order_product_item, parent, false);
        ProductCheckoutHolder holder = new ProductCheckoutHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProductCheckoutHolder holder, int position) {

        if (isCart) {
            showQuoteItem(holder, position);
        } else {
            showProductItem(holder, position);
        }

    }

    protected void showProductItem(ProductCheckoutHolder holder, int position) {
        final ProductEntity productEntity = listProducts.get(position);

        String name = productEntity.getName();
        if (Utils.validateString(name)) {
            holder.tvName.setText(name);
        }

        String sku = productEntity.getSku();
        if (Utils.validateString(sku)) {
            holder.tvSku.setText("Sku: " + sku);
        }

        String price = Utils.getPrice(productEntity, mBaseCurrency, mOrderCurrency);
        holder.tvPrice.setText(price);

        String qty = productEntity.getQuantityOrdered();
        if (Utils.validateString(qty)) {
            holder.tvQty.setText("Quantity Ordered: " + Utils.formatIntNumber(qty));
        }

        String image = productEntity.getOrderImage();
        if (Utils.validateString(image)) {
            Glide.with(mContext).load(image).into(holder.ivImage);
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", productEntity.getItemID());
                AppManager.getInstance().openProductDetail(hmData);
            }
        });

    }

    protected void showQuoteItem(ProductCheckoutHolder holder, int position) {
        final QuoteItemEntity quoteItemEntity = listQuotes.get(position);

        String name = quoteItemEntity.getName();
        if (Utils.validateString(name)) {
            holder.tvName.setText(name);
        }

        String sku = quoteItemEntity.getSku();
        if (Utils.validateString(sku)) {
            holder.tvSku.setText("Sku: " + sku);
        }

        String price = Utils.getPrice(quoteItemEntity, mBaseCurrency, mOrderCurrency);
        holder.tvPrice.setText(price);

        String qty = quoteItemEntity.getItemsQty();
        if (Utils.validateString(qty)) {
            holder.tvQty.setText("Quantity: " + Utils.formatIntNumber(qty));
        }

        String image = quoteItemEntity.getOrderImage();
        if (Utils.validateString(image)) {
            Glide.with(mContext).load(image).into(holder.ivImage);
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", quoteItemEntity.getProductID());
                AppManager.getInstance().openProductDetail(hmData);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isCart) {
            return listQuotes.size();
        } else {
            return listProducts.size();
        }
    }

    public static class ProductCheckoutHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvSku, tvQty, tvPrice;
        private ImageView ivImage;
        private RelativeLayout rlItem;

        public ProductCheckoutHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setTextColor(Color.BLACK);
            tvSku = (TextView) itemView.findViewById(R.id.tv_sku);
            tvSku.setTextColor(Color.BLACK);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvQty.setTextColor(Color.BLACK);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvPrice.setTextColor(AppColor.getInstance().getPriceColor());
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    public void setListProducts(ArrayList<ProductEntity> listProducts) {
        this.listProducts = listProducts;
    }

    public void setListQuotes(ArrayList<QuoteItemEntity> listQuotes) {
        this.listQuotes = listQuotes;
    }

    public void setCart(boolean cart) {
        isCart = cart;
    }
}
