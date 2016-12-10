package com.simicart.saletracking.login.entity;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 11/26/2016.
 */

public class LoginEntity extends AppEntity {

    protected String mUrl;
    protected String mEmail;
    protected String mPassword;
    protected String mSessionID;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getSessionID() {
        return mSessionID;
    }

    public void setSessionID(String sessionID) {
        mSessionID = sessionID;
    }
}
