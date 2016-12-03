package com.simicart.saletracking.customer.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 11/27/2016.
 */

public class AddressEntity extends AppEntity {

    protected String mID;
    protected String mFirstName;
    protected String mLastName;
    protected String mStreet;
    protected String mCity;
    protected String mRegion;
    protected String mRegionID;
    protected String mRegionCode;
    protected String mPostCode;
    protected String mCountryName;
    protected String mCountryCode;
    protected String mPhone;
    protected String mEmail;

    private final String ENTITY_ID = "entity_id";
    private final String FIRST_NAME = "firstname";
    private final String LAST_NAME = "lastname";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String REGION = "region";
    private final String REGION_ID = "region_id";
    private final String REGION_CODE = "region_code";
    private final String POST_CODE = "postcode";
    private final String COUNTRY_NAME = "country_name";
    private final String COUNTRY_CODE = "country_id";
    private final String PHONE = "telephone";
    private final String EMAIL = "email";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mFirstName = getString(FIRST_NAME);

        mLastName = getString(LAST_NAME);

        mStreet = getString(STREET);

        mCity = getString(CITY);

        mRegion = getString(REGION);

        mRegionID = getString(REGION_ID);

        mRegionCode = getString(REGION_CODE);

        mPostCode = getString(POST_CODE);

        mCountryName = getString(COUNTRY_NAME);

        mCountryCode = getString(COUNTRY_CODE);

        mPhone = getString(PHONE);

        mEmail = getString(EMAIL);

    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        mCountryName = countryName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPostCode() {
        return mPostCode;
    }

    public void setPostCode(String postCode) {
        mPostCode = postCode;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getRegionCode() {
        return mRegionCode;
    }

    public void setRegionCode(String regionCode) {
        mRegionCode = regionCode;
    }

    public String getRegionID() {
        return mRegionID;
    }

    public void setRegionID(String regionID) {
        mRegionID = regionID;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        mStreet = street;
    }
}
