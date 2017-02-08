package com.simicart.saletracking.customer.entity;

import com.simicart.saletracking.base.entity.AppEntity;
import com.simicart.saletracking.common.Utils;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerEntity extends AppEntity {

    protected String mID;
    protected String mEmail;
    protected String mCreatedAtDate;
    protected String mCreatedAtTime;
    protected String mUpdatedAt;
    protected boolean mIsActive;
    protected String mCreatedIn;
    protected String mPrefix;
    protected String mFirstName;
    protected String mMiddleName;
    protected String mLastName;
    protected String mSuffix;
    protected String mGender;
    protected String mDob;
    protected String mTaxVAT;
    protected AddressEntity mBillingAddress;
    protected AddressEntity mShippingAddress;

    private final String ENTITY_ID = "entity_id";
    private final String EMAIL = "email";
    private final String CREATED_AT = "created_at";
    private final String UPDATED_AT = "updated_at";
    private final String IS_ACTIVE = "is_active";
    private final String CREATED_IN = "created_in";
    private final String PREFIX = "prefix";
    private final String FIRST_NAME = "firstname";
    private final String MIDDLE_NAME = "middlename";
    private final String LAST_NAME = "lastname";
    private final String SUFFIX = "suffix";
    private final String GENDER = "gender";
    private final String DOB = "dob";
    private final String TAXVAT = "taxvat";
    private final String BILLING_ADDRESS_DATA = "billing_address_data";
    private final String SHIPPING_ADDRESS_DATA = "shipping_address_data";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mEmail = getString(EMAIL);

        String createdAt = getString(CREATED_AT);
        if (Utils.validateString(createdAt)) {
            String[] splits = null;
            if (createdAt.contains(" ")) {
                splits = createdAt.split(" ");
            } else if (createdAt.contains("T")) {
                splits = createdAt.split("T");
            }
            mCreatedAtDate = splits[0];
            mCreatedAtTime = splits[1];
        }

        mUpdatedAt = getString(UPDATED_AT);

        String active = getString(IS_ACTIVE);
        mIsActive = Utils.getBoolean(active);

        mCreatedIn = getString(CREATED_IN);

        mPrefix = getString(PREFIX);

        mFirstName = getString(FIRST_NAME);

        mMiddleName = getString(MIDDLE_NAME);

        mLastName = getString(LAST_NAME);

        mSuffix = getString(SUFFIX);

        mGender = getString(GENDER);

        mDob = getString(DOB);

        mTaxVAT = getString(TAXVAT);

        JSONObject billingObj = getJSONObjectWithKey(mJSON, BILLING_ADDRESS_DATA);
        if (billingObj != null) {
            mBillingAddress = new AddressEntity();
            mBillingAddress.parse(billingObj);
        }

        JSONObject shippingObj = getJSONObjectWithKey(mJSON, SHIPPING_ADDRESS_DATA);
        if (shippingObj != null) {
            mShippingAddress = new AddressEntity();
            mShippingAddress.parse(shippingObj);
        }

    }

    public String getCreatedAtDate() {
        return mCreatedAtDate;
    }

    public void setCreatedAtDate(String createdAtDate) {
        mCreatedAtDate = createdAtDate;
    }

    public String getCreatedAtTime() {
        return mCreatedAtTime;
    }

    public void setCreatedAtTime(String createdAtTime) {
        mCreatedAtTime = createdAtTime;
    }

    public String getCreatedIn() {
        return mCreatedIn;
    }

    public void setCreatedIn(String createdIn) {
        mCreatedIn = createdIn;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        mDob = dob;
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

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public boolean isActive() {
        return mIsActive;
    }

    public void setActive(boolean active) {
        mIsActive = active;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public AddressEntity getBillingAddress() {
        return mBillingAddress;
    }

    public void setBillingAddress(AddressEntity billingAddress) {
        mBillingAddress = billingAddress;
    }

    public AddressEntity getShippingAddress() {
        return mShippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        mShippingAddress = shippingAddress;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setSuffix(String suffix) {
        mSuffix = suffix;
    }

    public String getTaxVAT() {
        return mTaxVAT;
    }

    public void setTaxVAT(String taxVAT) {
        mTaxVAT = taxVAT;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }
}
