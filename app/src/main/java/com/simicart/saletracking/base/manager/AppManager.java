package com.simicart.saletracking.base.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.common.user.UserEntity;
import com.simicart.saletracking.customer.fragment.CustomerDetailFragment;
import com.simicart.saletracking.customer.fragment.ListCustomersFragment;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menu.top.MenuTopController;
import com.simicart.saletracking.layer.fragment.LayerFragment;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.order.fragment.OrderDetailFragment;
import com.simicart.saletracking.search.fragment.SearchFragment;

import java.util.HashMap;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppManager {

    private Activity mCurrentActivity;
    private FragmentManager mManager;
    private UserEntity mCurrentUser;
    private MenuTopController mMenuTopController;
    private String mSessionID;
    private String mStoreID = "0";

    public static AppManager instance;

    public static AppManager getInstance() {
        if(instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        mCurrentActivity = currentActivity;
    }

    public FragmentManager getManager() {
        return mManager;
    }

    public void setManager(FragmentManager manager) {
        mManager = manager;
    }

    public UserEntity getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(UserEntity currentUser) {
        mCurrentUser = currentUser;
    }

    public MenuTopController getMenuTopController() {
        return mMenuTopController;
    }

    public void setMenuTopController(MenuTopController menuTopController) {
        mMenuTopController = menuTopController;
    }

    public String getSessionID() {
        return mSessionID;
    }

    public void setSessionID(String sessionID) {
        mSessionID = sessionID;
    }

    public String getStoreID() {
        return mStoreID;
    }

    public void setStoreID(String storeID) {
        mStoreID = storeID;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        String fragmentName = ((AppFragment)fragment).getFragmentName();
        mManager.popBackStack(fragmentName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public void backToPreviousFragment() {
        mManager.popBackStack();
    }

    public void openLoginPage() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        replaceFragment(loginFragment);
        mMenuTopController.showMenuTop(false);
    }

    public void openListOrders(HashMap<String,Object> hmData) {
        ListOrdersFragment orderFragment = ListOrdersFragment.newInstance(new AppData(hmData));
        orderFragment.setFragmentName("Orders");
        orderFragment.setDetail(false);
        replaceFragment(orderFragment);
    }

    public void openListCustomers(HashMap<String,Object> hmData) {
        ListCustomersFragment customersFragment = ListCustomersFragment.newInstance(new AppData(hmData));
        customersFragment.setFragmentName("Customers");
        customersFragment.setDetail(false);
        replaceFragment(customersFragment);
    }

    public void openCustomerDetail(HashMap<String,Object> hmData) {
        CustomerDetailFragment customerDetailFragment = CustomerDetailFragment.newInstance(new AppData(hmData));
        customerDetailFragment.setFragmentName("Customer Detail");
        customerDetailFragment.setDetail(true);
        replaceFragment(customerDetailFragment);
    }

    public void openOrderDetail(HashMap<String,Object> hmData) {
        OrderDetailFragment orderDetailFragment = OrderDetailFragment.newInstance(new AppData(hmData));
        orderDetailFragment.setFragmentName("Order Detail");
        orderDetailFragment.setDetail(true);
        replaceFragment(orderDetailFragment);
    }

    public void openSearch(HashMap<String,Object> hmData) {
        SearchFragment searchFragment = SearchFragment.newInstance(new AppData(hmData));
        searchFragment.setFragmentName("Search");
        searchFragment.setDetail(true);
        replaceFragment(searchFragment);
    }

    public void openLayer(HashMap<String,Object> hmData) {
        LayerFragment layerFragment = LayerFragment.newInstance(new AppData(hmData));
        layerFragment.setFragmentName("Layer");
        layerFragment.setDetail(true);
        replaceFragment(layerFragment);
    }

    public void removeFragment(String tag) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.remove(mManager.findFragmentByTag(tag)).commit();
    }

}
