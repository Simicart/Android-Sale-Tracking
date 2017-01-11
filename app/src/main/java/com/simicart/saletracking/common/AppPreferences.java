package com.simicart.saletracking.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.simicart.saletracking.base.manager.AppManager;

/**
 * Created by Glenn on 11/27/2016.
 */

public class AppPreferences {

    public static Context mContext;
    public static SharedPreferences mSharedPre;

    public static final String NAME_REFERENCE = "saleTracking";
    private static final String SIGN_IN_NORMAL = "signInNormal";
    private static final String SIGN_IN_QR = "signInQr";
    private static final String IS_DEMO = "isDemo";
    private static final String CUSTOMER_URL = "customerUrl";
    private static final String CUSTOMER_EMAIL = "customerEmail";
    private static final String CUSTOMER_PASSWORD = "customerPassword";
    private static final String CUSTOMER_QR_SESSION = "customerQrSession";
    private static final String PAGING = "paging";
    private static final String DASHBOARD_SHOW_SALE_REPORT = "dashboardShowSaleReport";
    private static final String DASHBOARD_SHOW_BEST_SELLERS = "dashboardShowBestSellers";
    private static final String DASHBOARD_SHOW_LATEST_CUSTOMERS = "dashboardShowLatestCustomers";
    private static final String DASHBOARD_SHOW_LATEST_ORDERS = "dashboardShowLatestOrders";
    private static final String CURRENCY_POSITION = "currencyPosition";
    private static final String SEPARATOR = "separator";
    private static final String NUMBER_OF_DECIMALS = "numberOfDecimals";

    public static void init() {
        mContext = AppManager.getInstance().getCurrentActivity();
        mSharedPre = mContext.getSharedPreferences(NAME_REFERENCE,
                Context.MODE_PRIVATE);
    }

    public static void setSignInNormal(boolean signIn) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(SIGN_IN_NORMAL, signIn);
        editor.commit();
    }

    public static boolean isSignInNormal() {
        boolean isSignInComplete = false;
        if (mSharedPre != null) {
            isSignInComplete = mSharedPre.getBoolean(SIGN_IN_NORMAL, false);
        }
        return isSignInComplete;
    }

    public static void setSignInQr(boolean signIn) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(SIGN_IN_QR, signIn);
        editor.commit();
    }

    public static boolean isSignInQr() {
        boolean isSignInComplete = false;
        if (mSharedPre != null) {
            isSignInComplete = mSharedPre.getBoolean(SIGN_IN_QR, false);
        }
        return isSignInComplete;
    }

    public static void setIsDemo(boolean demo) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(IS_DEMO, demo);
        editor.commit();
    }

    public static boolean isDemo() {
        boolean isSignInComplete = false;
        if (mSharedPre != null) {
            isSignInComplete = mSharedPre.getBoolean(IS_DEMO, false);
        }
        return isSignInComplete;
    }

    public static void setCustomerUrl(String url) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_URL, url);
        editor.commit();
    }

    public static String getCustomerUrl() {
        String url = null;
        if (mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_URL, "");
        }
        return url;
    }

    public static void setCustomerEmail(String email) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_EMAIL, email);
        editor.commit();
    }

    public static String getCustomerEmail() {
        String url = null;
        if (mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_EMAIL, "");
        }
        return url;
    }

    public static void setCustomerPassword(String pass) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_PASSWORD, pass);
        editor.commit();
    }

    public static String getCustomerPassword() {
        String url = null;
        if (mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_PASSWORD, "");
        }
        return url;
    }

    public static void setCustomerQrSession(String session) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_QR_SESSION, session);
        editor.commit();
    }

    public static String getCustomerQrSession() {
        String url = null;
        if (mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_QR_SESSION, "");
        }
        return url;
    }

    public static void saveCustomerInfo(String url, String email, String pass) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_URL, url);
        editor.putString(CUSTOMER_EMAIL, email);
        editor.putString(CUSTOMER_PASSWORD, pass);
        editor.commit();
    }

    public static void clearCustomerInfo() {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_URL, null);
        editor.putString(CUSTOMER_EMAIL, null);
        editor.putString(CUSTOMER_PASSWORD, null);
        editor.commit();
    }

    public static void saveCustomerInfoForQr(String url, String email, String session) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_URL, url);
        editor.putString(CUSTOMER_EMAIL, email);
        editor.putString(CUSTOMER_QR_SESSION, session);
        editor.commit();
    }

    public static void clearCustomerInfoForQr() {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_URL, null);
        editor.putString(CUSTOMER_EMAIL, null);
        editor.putString(CUSTOMER_QR_SESSION, null);
        editor.commit();
    }

    public static void setShowSaleReport(boolean show) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(DASHBOARD_SHOW_SALE_REPORT, show);
        editor.commit();
    }

    public static boolean getShowSaleReport() {
        boolean show = true;
        if (mSharedPre != null) {
            show = mSharedPre.getBoolean(DASHBOARD_SHOW_SALE_REPORT, true);
        }
        return show;
    }

    public static void setShowBestSellers(boolean show) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(DASHBOARD_SHOW_BEST_SELLERS, show);
        editor.commit();
    }

    public static boolean getShowBestSellers() {
        boolean show = true;
        if (mSharedPre != null) {
            show = mSharedPre.getBoolean(DASHBOARD_SHOW_BEST_SELLERS, true);
        }
        return show;
    }

    public static void setShowLatestCustomer(boolean show) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(DASHBOARD_SHOW_LATEST_CUSTOMERS, show);
        editor.commit();
    }

    public static boolean getShowLatestCustomer() {
        boolean show = true;
        if (mSharedPre != null) {
            show = mSharedPre.getBoolean(DASHBOARD_SHOW_LATEST_CUSTOMERS, true);
        }
        return show;
    }

    public static void setShowLatestOrder(boolean show) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(DASHBOARD_SHOW_LATEST_ORDERS, show);
        editor.commit();
    }

    public static boolean getShowLatestOrder() {
        boolean show = true;
        if (mSharedPre != null) {
            show = mSharedPre.getBoolean(DASHBOARD_SHOW_LATEST_ORDERS, true);
        }
        return show;
    }

    public static void setPaging(int paging) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putInt(PAGING, paging);
        editor.commit();
    }

    public static int getPaging() {
        int paging = 40;
        if (mSharedPre != null) {
            paging = mSharedPre.getInt(PAGING, 40);
        }
        return paging;
    }

    public static void setCurrencyPosition(int currencyPosition) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putInt(CURRENCY_POSITION, currencyPosition);
        editor.commit();
    }

    public static int getCurrencyPosition() {
        int currencyPosition = 1;
        if (mSharedPre != null) {
            currencyPosition = mSharedPre.getInt(CURRENCY_POSITION, 1);
        }
        return currencyPosition;
    }

    public static void setSeparator(int separator) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putInt(SEPARATOR, separator);
        editor.commit();
    }

    public static int getSeparator() {
        int separator = 1;
        if (mSharedPre != null) {
            separator = mSharedPre.getInt(SEPARATOR, 1);
        }
        return separator;
    }

    public static void setNumberOfDecimals(int numberOfDecimals) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putInt(NUMBER_OF_DECIMALS, numberOfDecimals);
        editor.commit();
    }

    public static int getNumberOfDecimals() {
        int numberOfDecimals = 2;
        if (mSharedPre != null) {
            numberOfDecimals = mSharedPre.getInt(NUMBER_OF_DECIMALS, 2);
        }
        return numberOfDecimals;
    }

}
