package com.simicart.saletracking.common;

/**
 * Created by Glenn on 11/27/2016.
 */

public class Constants {

    public static final String demoUrl = "http://magento19.jajahub.com/";
    public static final String demoEmail = "cody@simicart.com";
    public static final String demoPassword = "123456";
    public static final String permissionDeniedMessage = "You don't have permission to access this";
    public static final String urlOnStore = "https://play.google.com/store/apps/details?id=com.simicart.saletracking";

    public static class Search {
        public static final int ORDER = 1;
        public static final int CUSTOMER = 2;
        public static final int PRODUCT = 3;
        public static final int CART = 4;
    }

    public static class Layer {
        public static final int FILTER = 1;
        public static final int SORT = 2;
        public static final int TIME = 3;
    }

    public static class TimePeriod {
        public static final int DAY = 1;
        public static final int MONTH = 2;
        public static final int YEAR = 3;
    }

    public static class Permission {
        public static final String SALES_TRACKING = "1";
        public static final String TOTALS_DETAILS = "2";
        public static final String LIFETIME_SALES = "3";
        public static final String PRODUCT_LIST = "4";
        public static final String PRODUCT_DETAILS = "5";
        public static final String PRODUCT_EDIT = "6";
        public static final String CUSTOMER_LISTS = "7";
        public static final String CUSTOMER_DETAILS = "8";
        public static final String CUSTOMER_EDIT = "9";
        public static final String CUSTOMER_ADDRESS_LIST = "10";
        public static final String CUSTOMER_ADDRESS_EDIT = "11";
        public static final String CUSTOMER_ADDRESS_REMOVE = "12";
        public static final String ORDER_LIST = "13";
        public static final String ORDER_DETAIL = "14";
        public static final String INVOICE_ORDER = "15";
        public static final String SHIP_ORDER = "16";
        public static final String CANCEL_ORDER = "17";
        public static final String HOLD_ORDER = "18";
        public static final String UNHOLD_ORDER = "19";
        public static final String ABANDONED_CART_LIST = "20";
        public static final String ABANDONED_CART_DETAIL = "21";
    }

    public static class EditProductDescription {
        public static final int NONE = 1;
        public static final int SHORT_DESCRIPTION = 2;
        public static final int DESCRIPTION = 3;
    }

    public static class RowType {
        public static final int TEXT = 1;
        public static final int TEXT_NUMBER = 2;
        public static final int SPINNER = 3;
        public static final int TIME = 4;
    }

    public static class CurrencyPosition {
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
        public static final int LEFT_SPACE = 3;
        public static final int RIGHT_SPACE = 4;
    }

    public static class Separator {
        public static final int DOT_FIRST = 1;
        public static final int COMMA_FIRST = 2;
    }

    public static class Chart {
        public static final int DASHBOARD = 1;
        public static final int FORECAST = 2;
    }

    public static class TypeShowLoading {
        public static final int LOADING = 1;
        public static final int DIALOG = 2;
        public static final int REFRESH = 3;
    }

}
