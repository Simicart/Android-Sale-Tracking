package com.simicart.saletracking.order.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.simicart.saletracking.order.adapter.ListOrdersAdapter;
import com.simicart.saletracking.order.delegate.ListOrdersDelegate;
import com.simicart.saletracking.order.entity.OrderEntity;
import com.simicart.saletracking.order.entity.OrderSection;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/28/2016.
 */

public class ListOrdersBlock extends AppBlock implements ListOrdersDelegate {

    protected TextView tvTime, tvSort, tvFilter;
    protected RecyclerView rvOrders;
    protected SwipeRefreshLayout srlRefresh;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected FloatingActionButton fabSearch;
    protected ListOrdersAdapter mAdapter;
    protected ArrayList<OrderEntity> listOrders;

    public ListOrdersBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        initMenuTop();
        initMenuBottom();

        srlRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);

        rvOrders = (RecyclerView) mView.findViewById(R.id.rv_orders);
        rvOrders.setLayoutManager(new StickyHeaderLayoutManager());

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

        if (collection != null && collection.containKey("orders")) {
            listOrders = (ArrayList<OrderEntity>) collection.getDataWithKey("orders");
            if (listOrders != null && listOrders.size() > 0) {
                ArrayList<OrderSection> listSections = new ArrayList<>();
                int i = 0;
                while (i < listOrders.size()) {
                    OrderSection orderSection = new OrderSection();
                    orderSection.setDate("");
                    ArrayList<OrderEntity> listOrdersSection = new ArrayList<>();
                    for (int j = i; j < listOrders.size(); j++) {
                        i++;
                        OrderEntity entity = listOrders.get(j);
                        String date = entity.getCreatedAtDate();
                        if (orderSection.getDate().equals("")) {
                            orderSection.setDate(date);
                            listOrdersSection.add(entity);
                        } else {
                            if (date.equals(orderSection.getDate())) {
                                listOrdersSection.add(entity);
                            } else {
                                i--;
                                break;
                            }
                        }
                    }
                    orderSection.setListOrders(listOrdersSection);
                    listSections.add(orderSection);
                }
                if (mAdapter == null) {
                    mAdapter = new ListOrdersAdapter(listSections);
                    rvOrders.setAdapter(mAdapter);
                } else {
                    mAdapter.setListSections(listSections);
                    mAdapter.notifyAllSectionsDataSetChanged();
                }
            } else {
                showEmptyMessage();
            }
        } else {
            showEmptyMessage();
        }

    }

    protected void initMenuTop() {

        tvTime = (TextView) mView.findViewById(R.id.tv_time);
        tvTime.setText("All Time");

        tvSort = (TextView) mView.findViewById(R.id.tv_sort);
        tvSort.setText("Sort By");

        tvFilter = (TextView) mView.findViewById(R.id.tv_filter);
        tvFilter.setText("All Status");

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
        rvOrders.setVisibility(View.GONE);
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("No orders found");
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
        return listOrders.size();
    }

    @Override
    public void dismissRefresh() {
        srlRefresh.setRefreshing(false);
    }

    @Override
    public void updateTimeFilterName(String filter) {
        if(Utils.validateString(filter)) {
            tvTime.setText(filter);
        }
    }

    @Override
    public void updateFilterName(String filter) {
        if(Utils.validateString(filter)) {
            tvFilter.setText(filter);
        }
    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvOrders.setOnScrollListener(listener);
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

    public void setOnStatusFilterClick(View.OnClickListener listener) {
        tvFilter.setOnClickListener(listener);
    }

    public void setOnSortCLick(View.OnClickListener listener) {
        tvSort.setOnClickListener(listener);
    }

    public void setOnTimeFilterClick(View.OnClickListener listener) {
        tvTime.setOnClickListener(listener);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        srlRefresh.setOnRefreshListener(listener);
    }

    public void hideSearchButton() {
        fabSearch.setVisibility(View.GONE);
    }
}
