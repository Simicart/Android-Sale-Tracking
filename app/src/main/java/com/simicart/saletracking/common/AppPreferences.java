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
    private static final String SIGN_IN_COMPLETE = "signInComplete";
    private static final String IS_DEMO = "isDemo";
    private static final String CUSTOMER_URL = "customerUrl";
    private static final String CUSTOMER_EMAIL = "customerEmail";
    private static final String CUSTOMER_PASSWORD = "customerPassword";

    public static void init() {
        mContext = AppManager.getInstance().getCurrentActivity();
        mSharedPre = mContext.getSharedPreferences(NAME_REFERENCE,
                Context.MODE_PRIVATE);
    }

    public static void setSignInComplete(boolean signIn) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putBoolean(SIGN_IN_COMPLETE, signIn);
        editor.commit();
    }

    public static boolean isSignInComplete() {
        boolean isSignInComplete = false;
        if (mSharedPre != null) {
            isSignInComplete = mSharedPre.getBoolean(SIGN_IN_COMPLETE, false);
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
        if(mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_URL, "");
        }
        return url;
    }

    public static void setCustomerEmail(String url) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_EMAIL, url);
        editor.commit();
    }

    public static String getCustomerEmail() {
        String url = null;
        if(mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_EMAIL, "");
        }
        return url;
    }

    public static void setCustomerPassword(String url) {
        SharedPreferences.Editor editor = mSharedPre.edit();
        editor.putString(CUSTOMER_PASSWORD, url);
        editor.commit();
    }

    public static String getCustomerPassword() {
        String url = null;
        if(mSharedPre != null) {
            url = mSharedPre.getString(CUSTOMER_PASSWORD, "");
        }
        return url;
    }

}
