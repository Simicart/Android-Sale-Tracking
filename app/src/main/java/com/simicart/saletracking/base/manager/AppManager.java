package com.simicart.saletracking.base.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.simicart.saletracking.R;
import com.simicart.saletracking.user.UserEntity;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppManager {

    private Activity mCurrentActivity;
    private FragmentManager mManager;
    private UserEntity mCurrentUser;

    public static AppManager instance;

    public static AppManager getInstance() {
        if(instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        mCurrentActivity = currentActivity;
    }

    public FragmentManager getManager() {
        return mManager;
    }

    public void setManager(FragmentManager manager) {
        mManager = manager;
    }

    public UserEntity getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(UserEntity currentUser) {
        mCurrentUser = currentUser;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
