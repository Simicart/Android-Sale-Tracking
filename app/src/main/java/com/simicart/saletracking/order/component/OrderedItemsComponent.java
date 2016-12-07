package com.simicart.saletracking.order.component;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simicart.saletracking.base.component.AppComponent;
import com.simicart.saletracking.order.adapter.OrderedItemsAdapter;
import com.simicart.saletracking.products.entity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderedItemsComponent extends AppComponent {

    protected ArrayList<ProductEntity> mListProducts;
    protected String mBaseCurrency;
    protected String mOrderCurrency;

    @Override
    public View createView() {
        RecyclerView rvProducts = new RecyclerView(mContext);
        rvProducts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvProducts.setNestedScrollingEnabled(false);
        OrderedItemsAdapter adapter = new OrderedItemsAdapter(mListProducts, mBaseCurrency, mOrderCurrency);
        rvProducts.setAdapter(adapter);
        return rvProducts;
    }

    public void setBaseCurrency(String baseCurrency) {
        mBaseCurrency = baseCurrency;
    }

    public void setListProducts(ArrayList<ProductEntity> listProducts) {
        mListProducts = listProducts;
    }

    public void setOrderCurrency(String orderCurrency) {
        mOrderCurrency = orderCurrency;
    }
}
