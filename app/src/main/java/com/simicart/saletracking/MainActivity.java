package com.simicart.saletracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menu.top.MenuTopController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.getInstance().setCurrentActivity(this);
        AppManager.getInstance().setManager(getSupportFragmentManager());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        MenuTopController menuTopController = new MenuTopController(toolbar);
        AppManager.getInstance().setMenuTopController(menuTopController);

        LoginFragment fragment = LoginFragment.newInstance();
        AppManager.getInstance().replaceFragment(fragment);
        AppManager.getInstance().getMenuTopController().showMenuTop(false);

    }
}
