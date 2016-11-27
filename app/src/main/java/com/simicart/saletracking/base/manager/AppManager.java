package com.simicart.saletracking.base.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.simicart.saletracking.R;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menu.top.MenuTopController;
import com.simicart.saletracking.order.fragment.OrderFragment;
import com.simicart.saletracking.common.user.UserEntity;
import com.simicart.saletracking.customer.fragment.CustomerDetailFragment;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppManager {

    private Activity mCurrentActivity;
    private FragmentManager mManager;
    private UserEntity mCurrentUser;
    private MenuTopController mMenuTopController;

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

    public void replaceFragment(Fragment fragment) {
        if(fragment instanceof OrderFragment) {
            mMenuTopController.showStorePicker(true);
        } else {
            mMenuTopController.showStorePicker(false);
        }

        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openLoginPage() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        replaceFragment(loginFragment);
        mMenuTopController.showMenuTop(false);
    }

    public void openOrderPage() {
        OrderFragment orderFragment = OrderFragment.newInstance();
        replaceFragment(orderFragment);
        mMenuTopController.setTitle("Orders");
    }

    public void openCustomerDetail() {
        CustomerDetailFragment customerDetailFragment = CustomerDetailFragment.newInstance();
        replaceFragment(customerDetailFragment);
        mMenuTopController.setTitle("Customer Detail");
    }

}
