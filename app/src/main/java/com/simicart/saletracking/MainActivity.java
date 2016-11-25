package com.simicart.saletracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.login.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.getInstance().setCurrentActivity(this);
        AppManager.getInstance().setManager(getSupportFragmentManager());

        LoginFragment fragment = LoginFragment.newInstance();
        AppManager.getInstance().replaceFragment(fragment);

    }
}
