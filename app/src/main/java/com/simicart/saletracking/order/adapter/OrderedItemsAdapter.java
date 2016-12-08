package com.simicart.saletracking.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simicart.saletracking.R;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderedItemsAdapter extends RecyclerView.Adapter<OrderedItemsAdapter.ProductCheckoutHolder> {

    protected ArrayList<ProductEntity> listProducts;
    protected Context mContext;
    protected String mBaseCurrency;
    protected String mOrderCurrency;

    public OrderedItemsAdapter(ArrayList<ProductEntity> listProducts, String baseCurrency, String orderCurrency) {
        this.listProducts = listProducts;
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

        ProductEntity productEntity = listProducts.get(position);

        String name = productEntity.getName();
        if(Utils.validateString(name)) {
            holder.tvName.setText(name);
        }

        String sku = productEntity.getSku();
        if(Utils.validateString(sku)) {
            holder.tvSku.setText("Sku: " + sku);
        }

        String price = Utils.getPrice(productEntity, mBaseCurrency, mOrderCurrency);
        holder.tvPrice.setText(price);

        String qty = productEntity.getQuantityOrdered();
        if(Utils.validateString(qty)) {
            holder.tvQty.setText("Quantity Ordered: " + Utils.formatNumber(qty));
        }

        String image = productEntity.getOrderImage();
        if(Utils.validateString(image)) {
            Glide.with(mContext).load(image).into(holder.ivImage);
        }

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public static class ProductCheckoutHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvSku, tvQty, tvPrice;
        private ImageView ivImage;

        public ProductCheckoutHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setTextColor(AppColor.getInstance().getBlackColor());
            tvSku = (TextView) itemView.findViewById(R.id.tv_sku);
            tvSku.setTextColor(AppColor.getInstance().getBlackColor());
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvQty.setTextColor(AppColor.getInstance().getBlackColor());
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvPrice.setTextColor(AppColor.getInstance().getPriceColor());
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
