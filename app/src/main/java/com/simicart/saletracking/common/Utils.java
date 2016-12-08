package com.simicart.saletracking.common;

import android.app.Service;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.cart.entity.QuoteItemEntity;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.text.DecimalFormat;

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

    public static void hideKeyboard() {
        try {
            if (AppManager.getInstance().getCurrentActivity() != null) {
                InputMethodManager imm = (InputMethodManager) AppManager.getInstance().getCurrentActivity()
                        .getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(AppManager.getInstance().getCurrentActivity().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public static int toDp(int value) {
        float unit = AppManager.getInstance().getCurrentActivity().getResources().getDisplayMetrics().density;
        int result = (int) (value * unit + 0.5f);
        return result;
    }

    public static int toPixel(int value) {
        DisplayMetrics metrics = AppManager.getInstance().getCurrentActivity().getResources().getDisplayMetrics();
        int px = value * ((int) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static String getPrice(ProductEntity productEntity, String baseCurrency, String orderCurrency) {
        String price = "";
        String productBasePrice = productEntity.getBasePrice();
        String productPrice = productEntity.getPrice();
        if (validateString(baseCurrency) && validateString(orderCurrency)) {
            if (baseCurrency.equals(orderCurrency)) {
                price = getPrice(productPrice, orderCurrency);
            } else {
                price = getPrice(productBasePrice, baseCurrency) + " [" + getPrice(productPrice, orderCurrency) + "]";
            }
        }
        return price;
    }

    public static String getPrice(QuoteItemEntity quoteItemEntity, String baseCurrency, String orderCurrency) {
        String price = "";
        String productBasePrice = quoteItemEntity.getBasePrice();
        String productPrice = quoteItemEntity.getPrice();
        if (validateString(baseCurrency) && validateString(orderCurrency)) {
            if (baseCurrency.equals(orderCurrency)) {
                price = getPrice(productPrice, orderCurrency);
            } else {
                price = getPrice(productBasePrice, baseCurrency) + " [" + getPrice(productPrice, orderCurrency) + "]";
            }
        }
        return price;
    }

    public static String getPrice(String price, String symbol) {
        price = formatNumber(price);
        if ((null == symbol) || (symbol.equals("null"))) {
            return "USD " + price;
        } else {
            return symbol + " " + price;
        }
    }

    public static String formatNumber(String number) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (number == null || number.equals("null")) {
            number = "0";
        }
        String formattedNumber = df.format(Float.parseFloat(number));
        if(formattedNumber.contains(".")) {
            int i = formattedNumber.length();
            while(true) {
                i--;
                char c = formattedNumber.charAt(i);
                if(c == '.') {
                    formattedNumber = formattedNumber.substring(0, formattedNumber.length()-1);
                    break;
                } else if(c != '0') {
                    break;
                } else {
                    formattedNumber = formattedNumber.substring(0, formattedNumber.length()-1);
                }
            }
            return formattedNumber;
        } else {
            return formattedNumber;
        }
    }

    public static void setTextHtml(TextView textView, String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(html));
        }
    }

}
