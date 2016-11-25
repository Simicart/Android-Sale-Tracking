package com.simicart.saletracking.common;

/**
 * Created by Glenn on 11/25/2016.
 */

public class Config {


    private final String demoUrl = "http://dev-magento19.jajahub.com/default/simitracking/rest/v2/";

    private boolean isDemo = false;
    private String customerUrl;

    public static Config instance;

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public boolean isDemo() {
        return isDemo;
    }

    public void setDemo(boolean demo) {
        isDemo = demo;
    }

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }

    public String getDemoUrl() {
        return demoUrl;
    }
}
