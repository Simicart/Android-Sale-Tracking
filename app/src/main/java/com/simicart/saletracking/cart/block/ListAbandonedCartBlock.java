package com.simicart.saletracking.cart.block;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.cart.adapter.ListAbandonedCartsAdapter;
import com.simicart.saletracking.cart.delegate.ListAbandonedCartsDelegate;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;
import com.simicart.saletracking.cart.entity.AbandonedCartSection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.customer.adapter.ListCustomersAdapter;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.customer.entity.CustomerSection;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class ListAbandonedCartBlock extends AppBlock implements ListAbandonedCartsDelegate {

    protected RecyclerView rvCarts;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected FloatingActionButton fabSearch;
    protected ListAbandonedCartsAdapter mAdapter;

    public ListAbandonedCartBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initMenuBottom();

        rvCarts = (RecyclerView) mView.findViewById(R.id.rv_carts);
        rvCarts.setLayoutManager(new StickyHeaderLayoutManager());

        fabSearch = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        Drawable searchDrawable = AppColor.getInstance().coloringIcon(R.drawable.ic_search, "#CACACA");
        fabSearch.setImageDrawable(searchDrawable);
    }

    @Override
    public void updateView(AppCollection collection) {
        if(collection != null) {
            if(collection.containKey("abandonedcarts")) {
                ArrayList<AbandonedCartEntity> listCarts =
                        (ArrayList<AbandonedCartEntity>) collection.getDataWithKey("abandonedcarts");
                if(listCarts != null && listCarts.size() > 0) {
                    ArrayList<AbandonedCartSection> listSections = new ArrayList<>();
                    int i = 0;
                    while(i < listCarts.size()) {
                        AbandonedCartSection cartSection = new AbandonedCartSection();
                        cartSection.setDate("");
                        ArrayList<AbandonedCartEntity> listCartsSection = new ArrayList<>();
                        for(int j=i;j<listCarts.size();j++) {
                            i++;
                            AbandonedCartEntity entity = listCarts.get(j);
                            String date = entity.getCreatedAtDate();
                            if(cartSection.getDate().equals("")) {
                                cartSection.setDate(date);
                                listCartsSection.add(entity);
                            } else {
                                if (date.equals(cartSection.getDate())) {
                                    listCartsSection.add(entity);
                                } else {
                                    i--;
                                    break;
                                }
                            }
                        }
                        cartSection.setListCarts(listCartsSection);
                        listSections.add(cartSection);
                    }
                    if(mAdapter == null) {
                        mAdapter = new ListAbandonedCartsAdapter(listSections);
                        rvCarts.setAdapter(mAdapter);
                    } else {
                        mAdapter.setListSections(listSections);
                        mAdapter.notifyAllSectionsDataSetChanged();
                    }
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

    protected void initMenuBottom() {

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(AppColor.getInstance().getWhiteColor());

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvCarts.setOnScrollListener(listener);
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
