package com.simicart.saletracking.common;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;

import com.simicart.saletracking.base.manager.AppManager;

/**
 * Created by Glenn on 11/24/2016.
 */

public class Utils {

    public static boolean validateString(String content) {
        if (null == content) {
            return false;
        }

        if (content.equals("")) {
            return false;
        }

        if (content.equals("null")) {
            return false;
        }

        return true;
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static final boolean getBoolean(String data) {
        if (!Utils.validateString(data)) {
            return false;
        }

        data = data.toLowerCase();

        if (data.equals("no")) {
            return false;
        }

        if (data.equals("0")) {
            return false;
        }

        if (data.equals("false")) {
            return false;
        }


        return true;
    }

}
