package com.simicart.saletracking.login.delegate;

import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.login.entity.LoginEntity;

/**
 * Created by Glenn on 11/25/2016.
 */

public interface LoginDelegate extends AppDelegate {

    public LoginEntity getLoginInfo();

}
