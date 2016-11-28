package com.simicart.saletracking.order.block;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.AppColor;
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
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected ImageView ivNext, ivPrevious;
    protected FloatingActionButton fabSearch;
    protected ListOrdersAdapter mAdapter;

    public ListOrdersBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        initMenuTop();
        initMenuBottom();

        rvOrders = (RecyclerView) mView.findViewById(R.id.rv_orders);
        rvOrders.setLayoutManager(new StickyHeaderLayoutManager());

        fabSearch = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        Drawable searchDrawable = AppColor.getInstance().coloringIcon(R.drawable.ic_search, "#CACACA");
        fabSearch.setImageDrawable(searchDrawable);

    }

    @Override
    public void updateView(AppCollection collection) {

        if(collection != null) {
            if(collection.containKey("orders")) {
                ArrayList<OrderEntity> listOrders = (ArrayList<OrderEntity>) collection.getDataWithKey("orders");
                if(listOrders != null && listOrders.size() > 0) {
                    ArrayList<OrderSection> listSections = new ArrayList<>();
                    int i = 0;
                    while(i < listOrders.size()) {
                        OrderSection orderSection = new OrderSection();
                        orderSection.setDate("");
                        ArrayList<OrderEntity> listOrdersSection = new ArrayList<>();
                        for(int j=i;j<listOrders.size();j++) {
                            i++;
                            OrderEntity entity = listOrders.get(j);
                            String date = entity.getCreatedAtDate();
                            if(orderSection.getDate().equals("")) {
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
                    if(mAdapter == null) {
                        mAdapter = new ListOrdersAdapter(listSections);
                        rvOrders.setAdapter(mAdapter);
                    } else {
                        mAdapter.setListSections(listSections);
                        mAdapter.notifyAllSectionsDataSetChanged();
                    }
                }
            }
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
        tvPage.setTextColor(AppColor.getInstance().getWhiteColor());

        ivNext = (ImageView) mView.findViewById(R.id.iv_next);
        ivPrevious = (ImageView) mView.findViewById(R.id.iv_previous);

    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvOrders.setOnScrollListener(listener);
    }

    public void setOnPreviousPage(View.OnClickListener listener) {
        ivPrevious.setOnClickListener(listener);
    }

    public void setOnNextPage(View.OnClickListener listener) {
        ivNext.setOnClickListener(listener);
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
}
