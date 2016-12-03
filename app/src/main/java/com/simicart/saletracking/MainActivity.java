package com.simicart.saletracking;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.menu.top.MenuTopController;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected boolean doubleBackToExitPressedOnce = false;
    protected DrawerLayout mDrawer;
    protected NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.getInstance().setCurrentActivity(this);
        AppManager.getInstance().setManager(getSupportFragmentManager());
        AppPreferences.init();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        MenuTopController menuTopController = new MenuTopController(toolbar);
        AppManager.getInstance().setMenuTopController(menuTopController);

        AppManager.getInstance().openLoginPage();

        checkInternetConnection();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        AppManager.getInstance().setDrawerLayout(mDrawer);
        AppManager.getInstance().setNavigationView(mNavigationView);

        if(AppPreferences.isSignInComplete()) {
            AppManager.getInstance().enableDrawer();
        } else {
            AppManager.getInstance().disableDrawer();
        }

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 1) {
            if(doubleBackToExitPressedOnce) {
                finish();
            }
            this.doubleBackToExitPressedOnce = true;
            AppNotify.getInstance().showToast("Press BACK again to exit");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        } else {
            AppManager.getInstance().backToPreviousFragment();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Drawable icon = item.getIcon();
        item.setIcon(AppColor.getInstance().coloringIcon(icon, "#FFFFFF"));

        AppManager.getInstance().clearFragments();

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_dashboard:
                AppManager.getInstance().openDashboardPage();
                break;
            case R.id.nav_bestseller:
                break;
            case R.id.nav_orders:
                AppManager.getInstance().openListOrders(null);
                break;
            case R.id.nav_products:
                break;
            case R.id.nav_customers:
                AppManager.getInstance().openListCustomers(null);
                break;
            case R.id.nav_abandoned_cart:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout:
                processLogout();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void processLogout() {
        if(AppManager.getInstance().isDemo()) {
            AppManager.getInstance().setDemo(false);
        } else {
            AppPreferences.setSignInComplete(false);
            AppPreferences.clearCustomerInfo();
        }
        AppManager.getInstance().openLoginPage();
        AppManager.getInstance().disableDrawer();
    }

    protected void checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null || !i.isConnected() || !i.isAvailable()) {
            AlertDialog.Builder alertboxDowload = new AlertDialog.Builder(
                    this);
            alertboxDowload.setTitle("WARNING");
            alertboxDowload.setMessage("Please check your internet connection!");
            alertboxDowload.setCancelable(false);
            alertboxDowload.setPositiveButton("OK".toUpperCase(),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            android.os.Process
                                    .killProcess(android.os.Process
                                            .myPid());
                            finish();
                        }
                    });
            alertboxDowload.show();
        }
    }

}
