package com.simicart.saletracking.product.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simicart.saletracking.R;
import com.simicart.saletracking.common.Utils;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ImageHolder> {

    protected ArrayList<String> mListImages;
    protected Context mContext;

    public ProductImagesAdapter(ArrayList<String> listImages) {
        mListImages = listImages;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_product_image_item, parent, false);
        ImageHolder productViewHolder = new ImageHolder(itemView);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        String imageUrl = mListImages.get(position);

        if(Utils.validateString(imageUrl)) {
            Glide.with(mContext).load(imageUrl).into(holder.ivProduct);
            holder.tvUrl.setText(imageUrl);
        }
    }

    @Override
    public int getItemCount() {
        return mListImages.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView ivProduct;
        private TextView tvUrl;

        public ImageHolder(View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvUrl = (TextView) itemView.findViewById(R.id.tv_image_url);
            tvUrl.setTextColor(Color.BLACK);
        }
    }

}
