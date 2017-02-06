package com.simicart.saletracking.common;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.cart.entity.QuoteItemEntity;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Glenn on 11/24/2016.
 */

public class Utils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

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

    public static String getFormattedPrice(String price) {
        StringBuilder builder = new StringBuilder();
        builder.append("#");
        String thousandSeparator = "";
        String decimalSeparator = "";
        int numberOfDecimals = AppPreferences.getNumberOfDecimals();
        if(AppPreferences.getSeparator() == Constants.Separator.COMMA_FIRST) {
            thousandSeparator = ",";
            decimalSeparator = ".";
        } else {
            thousandSeparator = ".";
            decimalSeparator = ",";
        }
        builder.append(",");
        builder.append("###");
        builder.append(",");
        builder.append("##0");
        if (numberOfDecimals > 0) {
            builder.append(".");
            for (int i = 0; i < numberOfDecimals; i++) {
                builder.append("0");
            }
        }
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(thousandSeparator.charAt(0));
        formatSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
        DecimalFormat mPriceFormat = new DecimalFormat(builder.toString(), formatSymbols);

        if (numberOfDecimals == 0) {
            mPriceFormat.setParseIntegerOnly(true);
        }

        return mPriceFormat.format(Double.parseDouble(price));
    }

    public static String getPrice(String price, String symbol) {
        int currencyPosition = AppPreferences.getCurrencyPosition();
        price = getFormattedPrice(price);
        switch (currencyPosition) {
            case Constants.CurrencyPosition.LEFT:
                price = symbol + price;
                break;
            case Constants.CurrencyPosition.RIGHT:
                price = price + symbol;
                break;
            case Constants.CurrencyPosition.LEFT_SPACE:
                price = symbol + " " + price;
                break;
            case Constants.CurrencyPosition.RIGHT_SPACE:
                price = price + " " + symbol;
                break;
            default:
                break;
        }
        return price;
    }

    public static String formatIntNumber(String number) {
        return "" + ((int)Double.parseDouble(number));
    }

    public static float parseFloat(String s) {
        if (Utils.validateString(s)) {
            if (Utils.isFloat(s)) {
                return Float.parseFloat(s);
            }
        }
        return 0;
    }

    public static int parseInt(String s) {
        if (Utils.isInteger(s)) {
            return Integer.parseInt(s);
        }
        return 0;
    }

    public static void setTextHtml(TextView textView, String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(html));
        }
    }

    public static ArrayList<String> getDatesFromPeriod(String fromDate, String toDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat newDateFormat = new SimpleDateFormat("dd/MM");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(fromDate);
            endDate = dateFormat.parse(toDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(newDateFormat.format(result));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static ArrayList<String> getMonthFromPeriod(String fromDate, String toDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat newDateFormat = new SimpleDateFormat("MM/yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(fromDate);
            endDate = dateFormat.parse(toDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(newDateFormat.format(result));
            calendar.add(Calendar.MONTH, 1);
        }
        return dates;
    }

    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1;
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }

    public static boolean isInternetAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) AppManager.getInstance().getCurrentActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null || !i.isConnected() || !i.isAvailable()) {
            return false;
        }
        return true;
    }

    public static String getDate(int a, int b, boolean add) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (add) {
            cal.add(a, b);
        } else {
            cal.set(a, b);
        }
        return dateFormat.format(cal.getTime());
    }

    public static String getFirstDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(cal.getTime());
    }

    public static String getLastDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String getFirstDayOfLast2Year() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.YEAR, -2);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(cal.getTime());
    }

    public static String getToDay() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

}
