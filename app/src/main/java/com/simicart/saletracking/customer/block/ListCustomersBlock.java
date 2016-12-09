package com.simicart.saletracking.customer.block;

import android.graphics.Color;
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
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.customer.adapter.ListCustomersAdapter;
import com.simicart.saletracking.customer.delegate.ListCustomersDelegate;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.customer.entity.CustomerSection;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/30/2016.
 */

public class ListCustomersBlock extends AppBlock implements ListCustomersDelegate {

    protected RecyclerView rvCustomers;
    protected RelativeLayout rlMenuBottom;
    protected TextView tvPage;
    protected LinearLayout llNext, llPrevious;
    protected FloatingActionButton fabSearch;
    protected ListCustomersAdapter mAdapter;
    protected ArrayList<CustomerEntity> listCustomers;

    public ListCustomersBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initMenuBottom();

        rvCustomers = (RecyclerView) mView.findViewById(R.id.rv_customers);
        rvCustomers.setLayoutManager(new StickyHeaderLayoutManager());

        fabSearch = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        Drawable searchDrawable = AppColor.getInstance().coloringIcon(R.drawable.ic_search, "#CACACA");
        fabSearch.setImageDrawable(searchDrawable);
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("customers")) {
                listCustomers = (ArrayList<CustomerEntity>) collection.getDataWithKey("customers");
                if (listCustomers != null && listCustomers.size() > 0) {
                    ArrayList<CustomerSection> listSections = new ArrayList<>();
                    int i = 0;
                    while (i < listCustomers.size()) {
                        CustomerSection customerSection = new CustomerSection();
                        customerSection.setDate("");
                        ArrayList<CustomerEntity> listCustomersSection = new ArrayList<>();
                        for (int j = i; j < listCustomers.size(); j++) {
                            i++;
                            CustomerEntity entity = listCustomers.get(j);
                            String date = entity.getCreatedAtDate();
                            if (customerSection.getDate().equals("")) {
                                customerSection.setDate(date);
                                listCustomersSection.add(entity);
                            } else {
                                if (date.equals(customerSection.getDate())) {
                                    listCustomersSection.add(entity);
                                } else {
                                    i--;
                                    break;
                                }
                            }
                        }
                        customerSection.setListCustomers(listCustomersSection);
                        listSections.add(customerSection);
                    }
                    if (mAdapter == null) {
                        mAdapter = new ListCustomersAdapter(listSections);
                        rvCustomers.setAdapter(mAdapter);
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
        return listCustomers.size();
    }

    protected void initMenuBottom() {

        rlMenuBottom = (RelativeLayout) mView.findViewById(R.id.rl_menu_bottom);

        tvPage = (TextView) mView.findViewById(R.id.tv_page);
        tvPage.setTextColor(Color.WHITE);

        llNext = (LinearLayout) mView.findViewById(R.id.ll_next);
        llPrevious = (LinearLayout) mView.findViewById(R.id.ll_previous);

    }

    public void setOnListScroll(RecyclerView.OnScrollListener listener) {
        rvCustomers.setOnScrollListener(listener);
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
