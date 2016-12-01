package com.simicart.saletracking.customer.controller;

import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.customer.request.CustomerDetailRequest;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.search.entity.SearchEntity;

import java.util.HashMap;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected String mCustomerID;
    protected View.OnClickListener mOnCustomerOrderClick;
    protected View.OnClickListener mOnCustomerAddressesClick;

    @Override
    public void onStart() {
        requestCustomerDetail();
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestCustomerDetail() {
        mDelegate.showLoading();
        CustomerDetailRequest customerDetailRequest = new CustomerDetailRequest();
        customerDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        customerDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        customerDetailRequest.setExtendUrl("customers/" + mCustomerID);
        customerDetailRequest.request();
    }

    protected void initListener() {
        mOnCustomerOrderClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hmData = new HashMap<>();
                if (mCollection != null) {
                    CustomerEntity customerEntity = (CustomerEntity) mCollection.getDataWithKey("customer");
                    SearchEntity searchEntity = new SearchEntity();
                    searchEntity.setKey("customer_email");
                    searchEntity.setQuery(customerEntity.getEmail());
                    hmData.put("search_entity", searchEntity);
                }
                ListOrdersFragment orderFragment = ListOrdersFragment.newInstance(new AppData(hmData));
                orderFragment.setFragmentName("Orders");
                orderFragment.setDetail(true);
                AppManager.getInstance().replaceFragment(orderFragment);
                AppManager.getInstance().getMenuTopController().setOnDetail(true);
            }
        };

        mOnCustomerAddressesClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setCustomerID(String customerID) {
        mCustomerID = customerID;
    }

    public View.OnClickListener getOnCustomerOrderClick() {
        return mOnCustomerOrderClick;
    }

    public View.OnClickListener getOnCustomerAddressesClick() {
        return mOnCustomerAddressesClick;
    }
}
