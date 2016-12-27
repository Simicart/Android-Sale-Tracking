package com.simicart.saletracking.cart.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
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
import com.simicart.saletracking.cart.adapter.ListAbandonedCartsAdapter;
import com.simicart.saletracking.cart.delegate.ListAbandonedCartsDelegate;
import com.simicart.saletracking.cart.entity.AbandonedCartEntity;
import com.simicart.saletracking.cart.entity.AbandonedCartSection;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;

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
    protected ArrayList<AbandonedCartEntity> listCarts;

    public ListAbandonedCartBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initMenuBottom();

        rvCarts = (RecyclerView) mView.findViewById(R.id.rv_carts);
        rvCarts.setLayoutManager(new StickyHeaderLayoutManager());

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
        if (collection != null && collection.containKey("abandonedcarts")) {
            listCarts = (ArrayList<AbandonedCartEntity>) collection.getDataWithKey("abandonedcarts");
            if (listCarts != null && listCarts.size() > 0) {
                ArrayList<AbandonedCartSection> listSections = new ArrayList<>();
                int i = 0;
                while (i < listCarts.size()) {
                    AbandonedCartSection cartSection = new AbandonedCartSection();
                    cartSection.setDate("");
                    ArrayList<AbandonedCartEntity> listCartsSection = new ArrayList<>();
                    for (int j = i; j < listCarts.size(); j++) {
                        i++;
                        AbandonedCartEntity entity = listCarts.get(j);
                        String date = entity.getCreatedAtDate();
                        if (cartSection.getDate().equals("")) {
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
                if (mAdapter == null) {
                    mAdapter = new ListAbandonedCartsAdapter(listSections);
                    rvCarts.setAdapter(mAdapter);
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
        return listCarts.size();
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
        rvCarts.setVisibility(View.GONE);
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("No abandoned carts found");
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
