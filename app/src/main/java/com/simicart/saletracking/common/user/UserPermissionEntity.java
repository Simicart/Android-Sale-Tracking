package com.simicart.saletracking.common.user;

import com.simicart.saletracking.base.entity.AppEntity;

/**
 * Created by Glenn on 11/26/2016.
 */

public class UserPermissionEntity extends AppEntity {

    protected String mID;
    protected String mRoleID;
    protected String mPermissionID;
    protected String mPermissionTitle;

    private final String ENTITY_ID = "entity_id";
    private final String ROLE_ID = "role_id";
    private final String PERMISSION_ID = "permission_id";
    private final String PERMISSION_TITLE = "permission_title";

    @Override
    public void parse() {

        mID = getString(ENTITY_ID);

        mRoleID = getString(ROLE_ID);

        mPermissionID = getString(PERMISSION_ID);

        mPermissionTitle = getString(PERMISSION_TITLE);

    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getPermissionID() {
        return mPermissionID;
    }

    public void setPermissionID(String permissionID) {
        mPermissionID = permissionID;
    }

    public String getPermissionTitle() {
        return mPermissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        mPermissionTitle = permissionTitle;
    }

    public String getRoleID() {
        return mRoleID;
    }

    public void setRoleID(String roleID) {
        mRoleID = roleID;
    }
}
