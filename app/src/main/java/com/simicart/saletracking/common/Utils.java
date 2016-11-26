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

    public static final String BUTTON_COLOR = "#F08002";
    public static final String BUTTON_TEXT_COLOR = "#FFFFFF";

    public static void initButton(AppCompatButton button, String text) {
        button.setSupportBackgroundTintList(new ColorStateList(new int[][]{{0}}, new int[]{Color.parseColor(BUTTON_COLOR)}));
        button.setTextColor(Color.parseColor(BUTTON_TEXT_COLOR));
        button.setText(text);
    }

    public static Drawable coloringIcon(int iconID, String color) {
        Drawable drawable = AppManager.getInstance().getCurrentActivity().getResources().getDrawable(iconID);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

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
