package com.simicart.saletracking.common;

import android.util.Log;

import com.simicart.saletracking.BuildConfig;

/**
 * Created by Crabby on 1/22/2017.
 */

public class AppLogging {

    public static void logData(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

}
