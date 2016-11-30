package com.simicart.saletracking.base.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.customer.fragment.ListCustomersFragment;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menu.top.MenuTopController;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.common.user.UserEntity;
import com.simicart.saletracking.customer.fragment.CustomerDetailFragment;
import com.simicart.saletracking.order.fragment.OrderDetailFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppManager {

    private Activity mCurrentActivity;
    private FragmentManager mManager;
    private UserEntity mCurrentUser;
    private MenuTopController mMenuTopController;
    private String mSessionID;

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

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void backToPreviousFragment() {
        if(mMenuTopController.isOnDetail()) {
            mMenuTopController.setOnDetail(false);
        }
        mManager.popBackStack();
    }

    public void openLoginPage() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        replaceFragment(loginFragment);
        mMenuTopController.showMenuTop(false);
    }

    public void openListOrders() {
        ListOrdersFragment orderFragment = ListOrdersFragment.newInstance();
        orderFragment.setFragmentName("Orders");
        replaceFragment(orderFragment);
    }

    public void openListCustomers() {
        ListCustomersFragment customersFragment = ListCustomersFragment.newInstance();
        customersFragment.setFragmentName("Customers");
        replaceFragment(customersFragment);
    }

    public void openCustomerDetail() {
        CustomerDetailFragment customerDetailFragment = CustomerDetailFragment.newInstance();
        customerDetailFragment.setFragmentName("Customer Detail");
        replaceFragment(customerDetailFragment);
        mMenuTopController.setOnDetail(true);
    }

    public void openOrderDetail(HashMap<String,Object> hmData) {
        OrderDetailFragment orderDetailFragment = OrderDetailFragment.newInstance(new AppData(hmData));
        orderDetailFragment.setFragmentName("Order Detail");
        replaceFragment(orderDetailFragment);
        mMenuTopController.setOnDetail(true);
    }

}
