package com.simicart.saletracking.base.manager;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.common.user.UserEntity;
import com.simicart.saletracking.customer.fragment.CustomerDetailFragment;
import com.simicart.saletracking.customer.fragment.ListAddressesFragment;
import com.simicart.saletracking.customer.fragment.ListCustomersFragment;
import com.simicart.saletracking.dashboard.fragment.DashboardFragment;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menu.top.MenuTopController;
import com.simicart.saletracking.layer.fragment.LayerFragment;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.order.fragment.OrderDetailFragment;
import com.simicart.saletracking.products.fragment.ListProductsFragment;
import com.simicart.saletracking.products.fragment.ProductDescriptionFragment;
import com.simicart.saletracking.products.fragment.ProductDetailFragment;
import com.simicart.saletracking.search.fragment.SearchFragment;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private boolean mIsDemo = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

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

    public boolean isDemo() {
        return mIsDemo;
    }

    public void setDemo(boolean demo) {
        mIsDemo = demo;
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

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        mNavigationView = navigationView;
    }

    public void initHeader() {
        View navHeader = mNavigationView.getHeaderView(0);
        CircleImageView ivAva = (CircleImageView) navHeader.findViewById(R.id.iv_ava);
        TextView tvName = (TextView) navHeader.findViewById(R.id.tv_name);
        tvName.setTextColor(AppColor.getInstance().getWhiteColor());
        TextView tvEmail = (TextView) navHeader.findViewById(R.id.tv_email);
        tvEmail.setTextColor(AppColor.getInstance().getWhiteColor());
        TextView tvRole = (TextView) navHeader.findViewById(R.id.tv_role);
        tvRole.setTextColor(AppColor.getInstance().getWhiteColor());

        if(mCurrentUser != null) {
            String image = mCurrentUser.getImage();
            if(Utils.validateString(image)) {
                Glide.with(mCurrentActivity).load(image).into(ivAva);
            }
            String name = mCurrentUser.getTitle();
            if(Utils.validateString(name)) {
                tvName.setText(name);
            }
            String email = mCurrentUser.getEmail();
            if(Utils.validateString(email)) {
                tvEmail.setText(email);
            }
            String role = mCurrentUser.getRoleTitle();
            if(Utils.validateString(role)) {
                tvRole.setText(role);
            }
        }

    }

    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED );
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
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
        mManager.popBackStackImmediate();
    }

    public void clearFragments() {
        while (mManager.getBackStackEntryCount() > 0){
            mManager.popBackStackImmediate();
        }
    }

    public void openLoginPage() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        replaceFragment(loginFragment);
        mMenuTopController.showMenuTop(false);
    }

    public void openDashboardPage() {
        DashboardFragment dashboardFragment = DashboardFragment.newInstance();
        dashboardFragment.setFragmentName("Dashboard");
        dashboardFragment.setDetail(false);
        replaceFragment(dashboardFragment);
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

    public void openListAddresses(HashMap<String,Object> hmData) {
        ListAddressesFragment listAddressesFragment = ListAddressesFragment.newInstance(new AppData(hmData));
        listAddressesFragment.setFragmentName("Addresses");
        listAddressesFragment.setDetail(true);
        replaceFragment(listAddressesFragment);
    }

    public void openListProducts(HashMap<String, Object> hmData) {
        ListProductsFragment listProductsFragment = ListProductsFragment.newInstance(new AppData(hmData));
        listProductsFragment.setFragmentName("Products");
        listProductsFragment.setDetail(false);
        replaceFragment(listProductsFragment);
    }

    public void openProductDetail(HashMap<String,Object> hmData) {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(new AppData(hmData));
        productDetailFragment.setFragmentName("product Detail");
        productDetailFragment.setDetail(true);
        replaceFragment(productDetailFragment);
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

    public void openDescription(HashMap<String,Object> hmData) {
        ProductDescriptionFragment descriptionFragment = ProductDescriptionFragment.newInstance(new AppData(hmData));
        descriptionFragment.setFragmentName("Description");
        if(hmData != null) {
            if(hmData.containsKey("title")) {
                String title = (String) hmData.get("title");
                if(Utils.validateString(title)) {
                    descriptionFragment.setFragmentName(title);
                }
            }
        }
        descriptionFragment.setDetail(true);
        replaceFragment(descriptionFragment);
    }

    public void navigateFirstFragment() {
        mNavigationView.getMenu().getItem(0).setChecked(true);
        openDashboardPage();
    }

}
