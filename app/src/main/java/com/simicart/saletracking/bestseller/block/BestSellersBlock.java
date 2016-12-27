package com.simicart.saletracking.bestseller.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.bestseller.adapter.BestSellersAdapter;
import com.simicart.saletracking.bestseller.delegate.BestSellersDelegate;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellersBlock extends AppBlock implements BestSellersDelegate {

    protected RecyclerView rvBestSellers;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected BestSellersAdapter mAdapter;
    protected ArrayList<BestSellerEntity> listBestSellers;

    public BestSellersBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initMenuBottom();

        rvBestSellers = (RecyclerView) mView.findViewById(R.id.rv_bestsellers);
        rvBestSellers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null && collection.containKey("bestsellers")) {
            listBestSellers = (ArrayList<BestSellerEntity>) collection.getDataWithKey("bestsellers");
            if (listBestSellers != null && listBestSellers.size() > 0) {
                if (mAdapter == null) {
                    mAdapter = new BestSellersAdapter(listBestSellers);
                    rvBestSellers.setAdapter(mAdapter);
                } else {
                    mAdapter.setListBestSellers(listBestSellers);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                showEmptyMessage();
            }
        } else {
            showEmptyMessage();
        }
    }

    @Override
    public void showBottom(boolean show) {
        if (show) {
            rlMenuBottom.setVisibility(View.VISIBLE);
        } else {
            rlMenuBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPage(int current, int total) {
        tvPage.setText(current + "/" + total);
    }

    @Override
    public int getPageSize() {
        return listBestSellers.size();
    }

    protected void initMenuBottom() {

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(Color.WHITE);

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

    }

    public void showEmptyMessage() {
//        ((ViewGroup) mView).removeAllViewsInLayout();
        rvBestSellers.setVisibility(View.GONE);
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("No best sellers found");
        tvEmpty.setTypeface(null, Typeface.BOLD);
        tvEmpty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvEmpty.setGravity(Gravity.CENTER);
        tvEmpty.setLayoutParams(params);
        ((ViewGroup) mView).addView(tvEmpty);
    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvBestSellers.setOnScrollListener(listener);
    }

    public void setOnPreviousPage(View.OnClickListener listener) {
        llPrevious.setOnClickListener(listener);
    }

    public void setOnNextPage(View.OnClickListener listener) {
        llNext.setOnClickListener(listener);
    }
}
