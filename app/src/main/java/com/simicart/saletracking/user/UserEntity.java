package com.simicart.saletracking.user;

import com.simicart.saletracking.base.entity.AppEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/26/2016.
 */

public class UserEntity extends AppEntity {

    protected String mID;
    protected String mStatus;
    protected String mImage;
    protected String mTitle;
    protected String mEmail;
    protected String mRoleID;
    protected String mBaseCurrency;
    protected String mRoleTitle;
    protected ArrayList<UserPermissionEntity> mListPermissions;

    private final String ENTITY_ID = "entity_id";
    private final String STATUS = "status";
    private final String USER_PROFILE_IMAGE = "user_profile_image";
    private final String USER_TITLE = "user_title";
    private final String USER_EMAIL = "user_email";
    private final String ROLE_ID = "role_id";
    private final String BASE_CURRENCY = "base_currency";
    private final String ROLE_TITLE = "role_title";
    private final String PERMISSIONS = "permissions";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mStatus = getString(STATUS);

        mImage = getString(USER_PROFILE_IMAGE);

        mTitle = getString(USER_TITLE);

        mEmail = getString(USER_EMAIL);

        mRoleID = getString(ROLE_ID);

        mRoleTitle = getString(ROLE_TITLE);

        mBaseCurrency = getString(BASE_CURRENCY);

        try {
            JSONArray permissionArr = getJSONArrayWithKey(mJSON, PERMISSIONS);
            if (permissionArr != null) {
                mListPermissions = new ArrayList<>();
                for (int i = 0; i < permissionArr.length(); i++) {
                    JSONObject permissionObj = permissionArr.getJSONObject(i);
                    UserPermissionEntity entity = new UserPermissionEntity();
                    entity.parse(permissionObj);
                    mListPermissions.add(entity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getBaseCurrency() {
        return mBaseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        mBaseCurrency = baseCurrency;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public ArrayList<UserPermissionEntity> getListPermissions() {
        return mListPermissions;
    }

    public void setListPermissions(ArrayList<UserPermissionEntity> listPermissions) {
        mListPermissions = listPermissions;
    }

    public String getRoleID() {
        return mRoleID;
    }

    public void setRoleID(String roleID) {
        mRoleID = roleID;
    }

    public String getRoleTitle() {
        return mRoleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        mRoleTitle = roleTitle;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
