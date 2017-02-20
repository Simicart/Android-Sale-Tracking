package com.simicart.saletracking.bestseller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;
import com.simicart.saletracking.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellersAdapter extends RecyclerView.Adapter<BestSellersAdapter.BestSellerHolder> {

    protected ArrayList<BestSellerEntity> mListBestSellers;
    protected Context mContext;

    public BestSellersAdapter(ArrayList<BestSellerEntity> listBestSellers) {
        mListBestSellers = listBestSellers;
    }

    @Override
    public BestSellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_bestseller_item, parent, false);
        BestSellerHolder holder = new BestSellerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BestSellerHolder holder, int position) {
        BestSellerEntity bestSellerEntity = mListBestSellers.get(position);

        String name = bestSellerEntity.getItemName();
        if(Utils.validateString(name)) {
            holder.tvName.setText(name);
        }

        final String id = bestSellerEntity.getID();
        if(Utils.validateString(id)) {
            holder.tvID.setText("Id: " + id);
        }

        String sku = bestSellerEntity.getSku();
        if(Utils.validateString(sku)) {
            holder.tvSku.setText("Sku: " + sku);
        }

        String qty = bestSellerEntity.getQtyOrdered();
        if(Utils.validateString(qty)) {
            holder.tvQty.setText("Ordered: " + Utils.formatIntNumber(qty));
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_product_detail");
                    AppManager.getInstance().trackWithMixPanel("best_bellers_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", id);
                AppManager.getInstance().openProductDetail(hmData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBestSellers.size();
    }

    public class BestSellerHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvID, tvSku, tvQty;
        private RelativeLayout rlItem;

        public BestSellerHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setTextColor(Color.BLUE);

            tvID = (TextView) itemView.findViewById(R.id.tv_id);
            tvID.setTextColor(Color.BLACK);

            tvSku = (TextView) itemView.findViewById(R.id.tv_sku);
            tvSku.setTextColor(Color.BLACK);

            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvQty.setTextColor(Color.BLACK);

            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_bestseller);
        }
    }

    public void setListBestSellers(ArrayList<BestSellerEntity> listBestSellers) {
        mListBestSellers = listBestSellers;
    }
}
