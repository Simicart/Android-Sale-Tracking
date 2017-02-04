package com.simicart.saletracking.base.manager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.bestseller.fragment.BestSellersFragment;
import com.simicart.saletracking.cart.fragment.AbandonedCartDetailFragment;
import com.simicart.saletracking.cart.fragment.ListAbandonedCartsFragment;
import com.simicart.saletracking.common.AppLogging;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.common.user.UserEntity;
import com.simicart.saletracking.customer.fragment.CustomerDetailFragment;
import com.simicart.saletracking.customer.fragment.ListAddressesFragment;
import com.simicart.saletracking.customer.fragment.ListCustomersFragment;
import com.simicart.saletracking.dashboard.fragment.DashboardFragment;
import com.simicart.saletracking.forecast.fragment.ForecastFragment;
import com.simicart.saletracking.layer.fragment.LayerFragment;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menutop.MenuTopController;
import com.simicart.saletracking.notification.entity.NotificationEntity;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.order.fragment.OrderDetailFragment;
import com.simicart.saletracking.product.fragment.ListProductsFragment;
import com.simicart.saletracking.product.fragment.ProductDescriptionFragment;
import com.simicart.saletracking.product.fragment.ProductDetailFragment;
import com.simicart.saletracking.search.fragment.SearchFragment;
import com.simicart.saletracking.setting.SettingFragment;

import org.json.JSONException;
import org.json.JSONObject;

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
    private MixpanelAPI mMixPanel;
    private ProgressDialog mLoading;
    protected boolean isInitDialogLoading = false;
    protected NotificationEntity mNotificationEntity;

    public static AppManager instance;

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    protected void initDialogLoading() {
        mLoading = ProgressDialog.show(mCurrentActivity, null, null, true, false);
        LayoutInflater inflater = LayoutInflater.from(mCurrentActivity);
        View loadingView = inflater.inflate(R.layout.view_loading, null);
        mLoading.setContentView(loadingView);
        mLoading.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mLoading.setCanceledOnTouchOutside(false);
        mLoading.setCancelable(false);
        mLoading.dismiss();
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

    public NotificationEntity getNotificationEntity() {
        return mNotificationEntity;
    }

    public void setNotificationEntity(NotificationEntity notificationEntity) {
        mNotificationEntity = notificationEntity;
    }

    public void initHeader() {
        View navHeader = mNavigationView.getHeaderView(0);
        CircleImageView ivAva = (CircleImageView) navHeader.findViewById(R.id.iv_ava);
        TextView tvName = (TextView) navHeader.findViewById(R.id.tv_name);
        tvName.setTextColor(Color.WHITE);
        TextView tvEmail = (TextView) navHeader.findViewById(R.id.tv_email);
        tvEmail.setTextColor(Color.WHITE);
        TextView tvRole = (TextView) navHeader.findViewById(R.id.tv_role);
        tvRole.setTextColor(Color.WHITE);

        if (mCurrentUser != null) {
            String image = mCurrentUser.getImage();
            if (Utils.validateString(image)) {
                Glide.with(mCurrentActivity).load(image).into(ivAva);
            }
            String name = mCurrentUser.getTitle();
            if (Utils.validateString(name)) {
                tvName.setText(name);
            }
            String email = mCurrentUser.getEmail();
            if (Utils.validateString(email)) {
                tvEmail.setText(email);
            }
            String role = mCurrentUser.getRoleTitle();
            if (Utils.validateString(role)) {
                tvRole.setText(role);
            }
        }

    }

    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        String fragmentName = ((AppFragment) fragment).getFragmentName();
        mManager.popBackStackImmediate(fragmentName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public void clearCurrentFragment() {
        mManager.popBackStackImmediate();
    }

    public void clearFragments() {
        while (mManager.getBackStackEntryCount() > 0) {
            mManager.popBackStackImmediate();
        }
    }

    public void removeFragment(String fragmentName) {
        mManager.beginTransaction().remove(mManager.findFragmentByTag(fragmentName)).commit();
    }

    public void openLoginPage() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setFragmentName("Login");
        replaceFragment(loginFragment);
        mMenuTopController.showMenuTop(false);
    }

    public void openDashboardPage() {
        DashboardFragment dashboardFragment = DashboardFragment.newInstance();
        dashboardFragment.setFragmentName("Dashboard");
        dashboardFragment.setDetail(false);
        dashboardFragment.setShowStore(true);
        replaceFragment(dashboardFragment);
    }

    public void openForecast() {
        ForecastFragment forecastFragment = ForecastFragment.newInstance();
        forecastFragment.setFragmentName("Forecast");
        forecastFragment.setDetail(false);
        forecastFragment.setShowStore(true);
        replaceFragment(forecastFragment);
    }

    public void openListOrders(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.ORDER_LIST)) {
            ListOrdersFragment orderFragment = ListOrdersFragment.newInstance(new AppData(hmData));
            orderFragment.setFragmentName("Orders");
            orderFragment.setDetail(false);
            orderFragment.setShowStore(true);
            replaceFragment(orderFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openListCustomers(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.CUSTOMER_LISTS)) {
            ListCustomersFragment customersFragment = ListCustomersFragment.newInstance(new AppData(hmData));
            customersFragment.setFragmentName("Customers");
            customersFragment.setDetail(false);
            customersFragment.setShowStore(false);
            replaceFragment(customersFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openCustomerDetail(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.CUSTOMER_DETAILS)) {
            CustomerDetailFragment customerDetailFragment = CustomerDetailFragment.newInstance(new AppData(hmData));
            customerDetailFragment.setFragmentName("Customer Detail");
            customerDetailFragment.setDetail(true);
            customerDetailFragment.setShowStore(false);
            replaceFragment(customerDetailFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openOrderDetail(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.ORDER_DETAIL)) {
            OrderDetailFragment orderDetailFragment = OrderDetailFragment.newInstance(new AppData(hmData));
            orderDetailFragment.setFragmentName("Order Detail");
            orderDetailFragment.setDetail(true);
            orderDetailFragment.setShowStore(false);
            replaceFragment(orderDetailFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openListAddresses(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.CUSTOMER_ADDRESS_LIST)) {
            ListAddressesFragment listAddressesFragment = ListAddressesFragment.newInstance(new AppData(hmData));
            listAddressesFragment.setFragmentName("Addresses");
            listAddressesFragment.setDetail(true);
            listAddressesFragment.setShowStore(false);
            replaceFragment(listAddressesFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openListProducts(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.PRODUCT_LIST)) {
            ListProductsFragment listProductsFragment = ListProductsFragment.newInstance(new AppData(hmData));
            listProductsFragment.setFragmentName("Products");
            listProductsFragment.setDetail(false);
            listProductsFragment.setShowStore(true);
            replaceFragment(listProductsFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openProductDetail(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.PRODUCT_DETAILS)) {
            ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(new AppData(hmData));
            productDetailFragment.setFragmentName("Product Detail");
            productDetailFragment.setDetail(true);
            productDetailFragment.setShowStore(false);
            replaceFragment(productDetailFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openListAbandonedCarts(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.ABANDONED_CART_LIST)) {
            ListAbandonedCartsFragment listAbandonedCartsFragment = ListAbandonedCartsFragment.newInstance(new AppData(hmData));
            listAbandonedCartsFragment.setFragmentName("Abandoned Carts");
            listAbandonedCartsFragment.setDetail(false);
            listAbandonedCartsFragment.setShowStore(false);
            replaceFragment(listAbandonedCartsFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openAbandonedCartDetail(HashMap<String, Object> hmData) {
        if (mCurrentUser.hasPermission(Constants.Permission.ABANDONED_CART_DETAIL)) {
            AbandonedCartDetailFragment abandonedCartDetailFragment = AbandonedCartDetailFragment.newInstance(new AppData(hmData));
            abandonedCartDetailFragment.setFragmentName("Cart Detail");
            abandonedCartDetailFragment.setDetail(true);
            abandonedCartDetailFragment.setShowStore(false);
            replaceFragment(abandonedCartDetailFragment);
        } else {
            AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
        }
    }

    public void openBestSellers() {
        BestSellersFragment bestSellersFragment = BestSellersFragment.newInstance();
        bestSellersFragment.setFragmentName("Best Sellers");
        bestSellersFragment.setDetail(false);
        bestSellersFragment.setShowStore(true);
        replaceFragment(bestSellersFragment);
    }

    public void openSetting() {
        SettingFragment settingFragment = SettingFragment.newInstance();
        settingFragment.setFragmentName("Setting");
        settingFragment.setDetail(false);
        replaceFragment(settingFragment);
    }

    public void openSearch(HashMap<String, Object> hmData) {
        SearchFragment searchFragment = SearchFragment.newInstance(new AppData(hmData));
        searchFragment.setFragmentName("Search");
        searchFragment.setDetail(true);
        replaceFragment(searchFragment);
    }

    public void openLayer(HashMap<String, Object> hmData) {
        LayerFragment layerFragment = LayerFragment.newInstance(new AppData(hmData));
        layerFragment.setFragmentName("Layer");
        layerFragment.setDetail(true);
        replaceFragment(layerFragment);
    }

    public void openDescription(HashMap<String, Object> hmData) {
        ProductDescriptionFragment descriptionFragment = ProductDescriptionFragment.newInstance(new AppData(hmData));
        descriptionFragment.setFragmentName("Description");
        if (hmData != null) {
            if (hmData.containsKey("title")) {
                String title = (String) hmData.get("title");
                if (Utils.validateString(title)) {
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

    public String getCurrentAppVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = mCurrentActivity.getPackageManager().getPackageInfo(mCurrentActivity.getPackageName(), 0);
            String version = pInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0.1.0";
    }

    public void initMixPanelWithToken(String token) {
        if (Utils.validateString(token)) {
            mMixPanel = MixpanelAPI.getInstance(mCurrentActivity.getApplicationContext(), token);
        }
    }

    public void trackWithMixPanel(String eventName, String property, String value) {
        JSONObject props = null;
        if (Utils.validateString(property) && Utils.validateString(value)) {
            props = new JSONObject();
            try {
                props.put(property, value);
            } catch (JSONException e) {
                props = null;
            }
        }
        trackWithMixPanel(eventName, props);
    }

    public void trackWithMixPanel(String eventName, JSONObject property) {
        if (null == mMixPanel) {
            return;
        }

        if (null != property) {
            AppLogging.logData("SimiManager ", "trackWithMixpanel eventName " + eventName + " PROPERTY " + property.toString());
            mMixPanel.track(eventName, property);
        } else {
            AppLogging.logData("SimiManager ", "trackWithMixpanel eventName " + eventName);
            mMixPanel.track(eventName);
        }
    }

    public void showDialogLoading() {
        if (!isInitDialogLoading) {
            isInitDialogLoading = true;
            initDialogLoading();
        }

        mLoading.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mLoading.show();
    }

    public void dismissDialogLoading() {
        if (!isInitDialogLoading) {
            isInitDialogLoading = true;
            initDialogLoading();
        }
        mLoading.dismiss();
    }

}
