package com.simicart.saletracking.product.block;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.product.entity.ProductEntity;
import com.simicart.saletracking.product.adapter.ListProductsAdapter;
import com.simicart.saletracking.product.delegate.ListProductsDelegate;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsBlock extends AppBlock implements ListProductsDelegate {

    protected RecyclerView rvProducts;
    protected FloatingActionButton fabSearch;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected ListProductsAdapter mAdapter;

    public ListProductsBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        rvProducts = (RecyclerView) mView.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(AppColor.getInstance().getWhiteColor());

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

        fabSearch = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        Drawable searchDrawable = AppColor.getInstance().coloringIcon(R.drawable.ic_search, "#CACACA");
        fabSearch.setImageDrawable(searchDrawable);
    }

    @Override
    public void updateView(AppCollection collection) {
        if(collection != null) {
            ArrayList<ProductEntity> listProducts = (ArrayList<ProductEntity>) collection.getDataWithKey("products");
            if(listProducts != null && listProducts.size() > 0) {
                if(mAdapter == null) {
                    mAdapter = new ListProductsAdapter(listProducts);
                    rvProducts.setAdapter(mAdapter);
                } else {
                    mAdapter.setListProducts(listProducts);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void showBottom(boolean show) {
        if(show) {
            rlMenuBottom.setVisibility(View.VISIBLE);
        } else {
            rlMenuBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPage(int current, int total) {
        tvPage.setText(current + "/" + total);
    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvProducts.setOnScrollListener(listener);
    }

    public void setOnPreviousPage(View.OnClickListener listener) {
        llPrevious.setOnClickListener(listener);
    }

    public void setOnNextPage(View.OnClickListener listener) {
        llNext.setOnClickListener(listener);
    }

    public void setOnSearchClick(View.OnClickListener listener) {
        fabSearch.setOnClickListener(listener);
    }

}
