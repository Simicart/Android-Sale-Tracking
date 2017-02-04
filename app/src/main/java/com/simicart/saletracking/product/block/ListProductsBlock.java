package com.simicart.saletracking.product.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.adapter.ListProductsAdapter;
import com.simicart.saletracking.product.delegate.ListProductsDelegate;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsBlock extends AppBlock implements ListProductsDelegate {

    protected SwipeRefreshLayout srlRefresh;
    protected RecyclerView rvProducts;
    protected FloatingActionButton fabSearch;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected ListProductsAdapter mAdapter;
    protected ArrayList<ProductEntity> listProducts;

    public ListProductsBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        srlRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);

        rvProducts = (RecyclerView) mView.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(Color.WHITE);

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

        fabSearch = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        Drawable searchDrawable = AppColor.getInstance().coloringIcon(R.drawable.ic_search, "#000000");
        fabSearch.setImageDrawable(searchDrawable);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams fabParams = (RelativeLayout.LayoutParams) fabSearch.getLayoutParams();
            fabParams.setMargins(0, 0, Utils.toPixel(15), Utils.toPixel(15));
        }
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null && collection.containKey("products")) {
            listProducts = (ArrayList<ProductEntity>) collection.getDataWithKey("products");
            if (listProducts != null && listProducts.size() > 0) {
                if (mAdapter == null) {
                    mAdapter = new ListProductsAdapter(listProducts);
                    rvProducts.setAdapter(mAdapter);
                } else {
                    mAdapter.setListProducts(listProducts);
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
        return listProducts.size();
    }

    @Override
    public void dismissRefresh() {
        srlRefresh.setRefreshing(false);
    }

    public void showEmptyMessage() {
//        ((ViewGroup) mView).removeAllViewsInLayout();
        rvProducts.setVisibility(View.GONE);
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("No products found");
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

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        srlRefresh.setOnRefreshListener(listener);
    }

}
