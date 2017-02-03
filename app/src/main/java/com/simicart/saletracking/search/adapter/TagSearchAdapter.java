package com.simicart.saletracking.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.search.entity.SearchEntity;

import java.util.ArrayList;

/**
 * Created by Martial on 2/3/2017.
 */

public class TagSearchAdapter extends RecyclerView.Adapter<TagSearchAdapter.TagSearchHolder> {

    protected ArrayList<SearchEntity> mListSearches;
    protected SearchEntity mSelectedSearchEntity;

    public TagSearchAdapter(ArrayList<SearchEntity> listSearches, SearchEntity selectedSearchEntity) {
        mListSearches = listSearches;
        mSelectedSearchEntity = selectedSearchEntity;
    }

    @Override
    public TagSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.adapter_layer_item, parent, false);
        TagSearchHolder holder = new TagSearchHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(TagSearchHolder holder, int position) {
        final SearchEntity searchEntity = mListSearches.get(position);

        String label = searchEntity.getLabel();
        if(Utils.validateString(label)) {
            holder.tvLayer.setText(label);
        }

        if (mSelectedSearchEntity != null && Utils.validateString(label)) {
            if (searchEntity.getKey().equals(mSelectedSearchEntity.getKey())) {
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.GONE);
            }
        } else {
            if (mListSearches.indexOf(searchEntity) == 0) {
                mSelectedSearchEntity = searchEntity;
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.GONE);
            }
        }

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedSearchEntity = searchEntity;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListSearches.size();
    }

    public class TagSearchHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlItem;
        private TextView tvLayer;
        private ImageView ivCheck;

        public TagSearchHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_layer_item);
            tvLayer = (TextView) itemView.findViewById(R.id.tv_layer);
            tvLayer.setTextColor(Color.BLACK);
            ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
        }
    }

    public SearchEntity getSelectedSearchEntity() {
        return mSelectedSearchEntity;
    }

}
