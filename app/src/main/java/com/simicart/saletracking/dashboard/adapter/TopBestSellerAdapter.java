package com.simicart.saletracking.dashboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/13/2016.
 */

public class TopBestSellerAdapter extends RecyclerView.Adapter<TopBestSellerAdapter.TopBestSellerHolder> {

    protected ArrayList<BestSellerEntity> listBestSellers;
    protected Context mContext;

    public TopBestSellerAdapter(ArrayList<BestSellerEntity> listBestSellers) {
        this.listBestSellers = listBestSellers;
    }

    @Override
    public TopBestSellerAdapter.TopBestSellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_top_best_seller_item, parent, false);
        TopBestSellerAdapter.TopBestSellerHolder holder = new TopBestSellerAdapter.TopBestSellerHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(TopBestSellerAdapter.TopBestSellerHolder holder, int position) {

        final BestSellerEntity bestSellerEntity = listBestSellers.get(position);

        if(position % 2 == 0) {
            holder.llItem.setBackgroundColor(Color.WHITE);
        } else {
            holder.llItem.setBackgroundColor(AppColor.getInstance().getSectionColor());
        }

        String id = bestSellerEntity.getID();
        if (Utils.validateString(id)) {
            holder.tvID.setText(id);
        }

        String qty = bestSellerEntity.getQtyOrdered();
        if (Utils.validateString(qty)) {
            holder.tvQty.setText("Ordered: " + Utils.formatIntNumber(qty));
        }

        String itemName = bestSellerEntity.getItemName();
        if (Utils.validateString(itemName)) {
            holder.tvProductName.setText(itemName);
        }

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", bestSellerEntity.getID());
                AppManager.getInstance().openProductDetail(hmData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBestSellers.size();
    }

    public class TopBestSellerHolder extends RecyclerView.ViewHolder {

        public TextView tvID, tvQty, tvProductName;
        protected LinearLayout llItem;

        public TopBestSellerHolder(View itemView) {
            super(itemView);
            tvID = (TextView) itemView.findViewById(R.id.tv_id);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);

            tvID.setTextColor(Color.BLACK);
            tvQty.setTextColor(Color.BLACK);
            tvProductName.setTextColor(AppColor.getInstance().getThemeColor());
        }
    }
}
