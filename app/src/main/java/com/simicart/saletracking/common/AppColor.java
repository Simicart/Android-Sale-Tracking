package com.simicart.saletracking.common;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;

import com.simicart.saletracking.base.manager.AppManager;

/**
 * Created by Glenn on 11/26/2016.
 */

public class AppColor {

    private final String mThemeColor = "#fc9900";
    private final String mButtonColor = "#F08002";
    private final String mButtonTextColor = "#FFFFFF";
    private final String mSectionColor = "#CACACA";
    private final String mPriceColor = "#ff0000";

    private final String mOrderPendingColor = "#00b200";
    private final String mOrderNAColor = "#333333";
    private final String mOrderProcessingColor = "#fc9900";
    private final String mOrderCompletedColor = "#3399ff";
    private final String mOrderCanceledColor = "#ff0000";

    public static AppColor instance;

    public static AppColor getInstance() {
        if (instance == null) {
            instance = new AppColor();
        }
        return instance;
    }

    public void initButton(AppCompatButton button, String text) {
        button.setSupportBackgroundTintList(new ColorStateList(new int[][]{{0}}, new int[]{Color.parseColor(mButtonColor)}));
        button.setTextColor(Color.parseColor(mButtonTextColor));
        button.setText(text);
    }

    public Drawable coloringIcon(int iconID, String color) {
        Drawable drawable = AppManager.getInstance().getCurrentActivity().getResources().getDrawable(iconID);
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public Drawable coloringIcon(Drawable drawable, String color) {
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public int getButtonColor() {
        return Color.parseColor(mButtonColor);
    }

    public String getButtonTextColor() {
        return mButtonTextColor;
    }

    public int getThemeColor() {
        return Color.parseColor(mThemeColor);
    }

    public int getSectionColor() {
        return Color.parseColor(mSectionColor);
    }

    public int getOrderNAColor() {
        return Color.parseColor(mOrderNAColor);
    }

    public int getOrderPendingColor() {
        return Color.parseColor(mOrderPendingColor);
    }

    public int getOrderCanceledColor() {
        return Color.parseColor(mOrderCanceledColor);
    }

    public int getOrderCompletedColor() {
        return Color.parseColor(mOrderCompletedColor);
    }

    public int getOrderProcessingColor() {
        return Color.parseColor(mOrderProcessingColor);
    }

    public int getPriceColor() {
        return Color.parseColor(mPriceColor);
    }
}
