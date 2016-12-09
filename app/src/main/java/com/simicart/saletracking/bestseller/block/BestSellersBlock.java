package com.simicart.saletracking.bestseller.block;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.bestseller.adapter.BestSellersAdapter;
import com.simicart.saletracking.bestseller.delegate.BestSellersDelegate;
import com.simicart.saletracking.bestseller.entity.BestSellerEntity;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

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
        if(collection != null) {
            if(collection.containKey("bestsellers")) {
                ArrayList<BestSellerEntity> listBestSellers = (ArrayList<BestSellerEntity>) collection.getDataWithKey("bestsellers");
                if(listBestSellers != null) {
                    if(mAdapter == null) {
                        mAdapter = new BestSellersAdapter(listBestSellers);
                        rvBestSellers.setAdapter(mAdapter);
                    } else {
                        mAdapter.setListBestSellers(listBestSellers);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
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

    protected void initMenuBottom() {

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(Color.WHITE);

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

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
