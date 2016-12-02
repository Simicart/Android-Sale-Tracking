package com.simicart.saletracking.common;

/**
 * Created by Glenn on 11/27/2016.
 */

public class Constants {

    public static final String demoUrl = "http://dev-magento19.jajahub.com/default/simitracking/rest/v2/";
    public static final String demoEmail = "test@simicart.com";
    public static final String demoPassword = "123456";

    public static class Search {
        public static final int ORDER = 1;
        public static final int CUSTOMER = 2;
    }

    public static class Layer {
        public static final int FILTER = 1;
        public static final int SORT = 2;
        public static final int TIME = 3;
    }

}
