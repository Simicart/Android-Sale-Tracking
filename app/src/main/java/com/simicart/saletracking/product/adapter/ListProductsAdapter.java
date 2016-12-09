package com.simicart.saletracking.product.adapter;

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
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.ProductViewHolder> {

    protected ArrayList<ProductEntity> listProducts;
    protected Context mContext;

    public ListProductsAdapter(ArrayList<ProductEntity> listProducts) {
        this.listProducts = listProducts;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_product_item, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(itemView);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        ProductEntity productEntity = listProducts.get(position);

        ArrayList<String> images = productEntity.getProductImages();
        if (images != null && images.size() > 0) {
            String image = images.get(0);
            if (Utils.validateString(image)) {
                Glide.with(mContext).load(image).into(holder.ivProduct);
            }
        }

        String productName = productEntity.getName();
        if (Utils.validateString(productName)) {
            holder.tvProductName.setText(productName);
        }

        final String productID = productEntity.getID();
        if (Utils.validateString(productID)) {
            holder.tvProductID.setText("Id: " + productID);
        }

        String productSku = productEntity.getSku();
        if (Utils.validateString(productSku)) {
            holder.tvProductSku.setText("Sku: " + productSku);
        }

        String price = productEntity.getPrice();
        if (Utils.validateString(price)) {
            holder.tvProductPrice.setText(Utils.getPrice(price, "USD"));
        }

        String productType = productEntity.getType();
        if (Utils.validateString(productType)) {
            holder.tvProductType.setText("Type: " + productType);
        }

        String productVisibility = productEntity.getVisibility();
        if (Utils.validateString(productVisibility)) {
            holder.tvProductVisibility.setText("Visibility: " + productVisibility);
        }

        holder.rlProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", productID);
                AppManager.getInstance().openProductDetail(hmData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProduct;
        protected TextView tvProductName, tvProductID, tvProductSku, tvProductPrice, tvProductType, tvProductVisibility;
        protected RelativeLayout rlProductItem;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductName.setTextColor(Color.BLUE);
            tvProductID = (TextView) itemView.findViewById(R.id.tv_product_id);
            tvProductID.setTextColor(Color.BLACK);
            tvProductSku = (TextView) itemView.findViewById(R.id.tv_product_sku);
            tvProductSku.setTextColor(Color.BLACK);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            tvProductPrice.setTextColor(Color.BLACK);
            tvProductType = (TextView) itemView.findViewById(R.id.tv_product_type);
            tvProductType.setTextColor(Color.BLACK);
            tvProductVisibility = (TextView) itemView.findViewById(R.id.tv_product_visibility);
            tvProductVisibility.setTextColor(Color.BLACK);
            rlProductItem = (RelativeLayout) itemView.findViewById(R.id.rl_product_item);
        }

    }

    public void setListProducts(ArrayList<ProductEntity> listProducts) {
        this.listProducts = listProducts;
    }
}
