package com.simicart.saletracking;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.manager.DialogCallBack;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.AppRequest;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppEvent;
import com.simicart.saletracking.common.AppLogging;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.dashboard.fragment.DashboardFragment;
import com.simicart.saletracking.login.fragment.LoginFragment;
import com.simicart.saletracking.menutop.MenuTopController;
import com.simicart.saletracking.notification.entity.NotificationEntity;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected boolean doubleBackToExitPressedOnce = false;
    protected DrawerLayout mDrawer;
    protected NavigationView mNavigationView;
    public static boolean isRunning;

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

        // check whether have any notifications
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NotificationEntity notificationData = (NotificationEntity) extras
                    .getSerializable("NOTIFICATION_DATA");
            AppManager.getInstance().setNotificationEntity(notificationData);
        }

        AppManager.getInstance().openLoginPage();

        checkInternetConnection();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        AppManager.getInstance().setDrawerLayout(mDrawer);
        AppManager.getInstance().setNavigationView(mNavigationView);

        if (AppPreferences.isSignInNormal()) {
            AppManager.getInstance().enableDrawer();
        } else {
            AppManager.getInstance().disableDrawer();
        }

        isRunning = true;

        AppManager.getInstance().initMixPanelWithToken("286b4016149732004b4ebb2f2891ffec");

        new VersionAsync().execute();
    }

    public class VersionAsync extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=com.simicart.saletracking&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                AppLogging.logData("App Version", newVersion);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals(AppManager.getInstance().getCurrentAppVersion())) {
                showPopupUpdate();
            } else {
                AppPreferences.setAskUpdateApp(true);
                AppManager.getInstance().setNeedUpdate(false);
            }
        }
    }

    public void showPopupUpdate() {
        AppManager.getInstance().setNeedUpdate(true);
        if(AppPreferences.getAskUpdateApp()) {
            AppNotify.getInstance().showPopupCustom("New version available!", "Upgrade now", new DialogCallBack() {
                @Override
                public void onClick() {
                    AppManager.getInstance().openGooglePlay();
                }
            }, "Don't ask again", new DialogCallBack() {
                @Override
                public void onClick() {
                    AppPreferences.setAskUpdateApp(false);
                }
            }, "Cancel", new DialogCallBack() {
                @Override
                public void onClick() {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            AppFragment backFragment = (AppFragment) getSupportFragmentManager().getFragments().get(0);
            String fragmentName = backFragment.getFragmentName();
            if (fragmentName != null && (fragmentName.equals("Dashboard") || fragmentName.equals("Login"))) {
                if (doubleBackToExitPressedOnce) {
                    finish();
                }
                this.doubleBackToExitPressedOnce = true;
                AppNotify.getInstance().showToast("Press BACK again to exit");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                AppManager.getInstance().clearCurrentFragment();
                if(AppManager.getInstance().isDemo() || AppPreferences.isSignInNormal() || AppPreferences.isSignInQr()) {
                    AppManager.getInstance().navigateFirstFragment();
                }
            }
        } else {
            AppManager.getInstance().clearCurrentFragment();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123 && data != null) {
            Bundle extra = data.getExtras();
            String result = extra.getString("result");

            HashMap<String, Object> hmQRData = new HashMap<>();
            hmQRData.put("result", result);

            AppEvent.getInstance().dispatchEvent("login.qrcode", hmQRData);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        // Tracking with MixPanel
        try {
            JSONObject object = new JSONObject();
            object.put("click_on", id);
            AppManager.getInstance().trackWithMixPanel("slide_menu", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (id) {
            case R.id.nav_dashboard:
                AppManager.getInstance().clearFragments();
                AppManager.getInstance().openDashboardPage();
                break;
            case R.id.nav_forecast:
                AppManager.getInstance().clearFragments();
                AppManager.getInstance().openForecast();
                break;
            case R.id.nav_orders:
                if (AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.ORDER_LIST)) {
                    AppManager.getInstance().clearFragments();
                    AppManager.getInstance().openListOrders(null);
                } else {
                    AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
                }
                break;
            case R.id.nav_bestseller:
                AppManager.getInstance().clearFragments();
                AppManager.getInstance().openBestSellers();
                break;
            case R.id.nav_products:
                if (AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.PRODUCT_LIST)) {
                    AppManager.getInstance().clearFragments();
                    AppManager.getInstance().openListProducts(null);
                } else {
                    AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
                }
                break;
            case R.id.nav_customers:
                if (AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.CUSTOMER_LISTS)) {
                    AppManager.getInstance().clearFragments();
                    AppManager.getInstance().openListCustomers(null);
                } else {
                    AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
                }
                break;
            case R.id.nav_abandoned_cart:
                if (AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.ABANDONED_CART_LIST)) {
                    AppManager.getInstance().clearFragments();
                    AppManager.getInstance().openListAbandonedCarts(null);
                } else {
                    AppNotify.getInstance().showToast(Constants.permissionDeniedMessage);
                }
                break;
            case R.id.nav_settings:
                AppManager.getInstance().clearFragments();
                AppManager.getInstance().openSetting();
                break;
            case R.id.nav_logout:
                AppManager.getInstance().clearFragments();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        processLogout();
                    }
                }, 300);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void processLogout() {
        AppManager.getInstance().showDialogLoading();
        AppRequest logoutRequest = new AppRequest();
        logoutRequest.setExtendUrl("simitracking/rest/v2/staffs/logout");
        logoutRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                AppManager.getInstance().dismissDialogLoading();
                onLogoutSuccess();
            }
        });
        logoutRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                AppManager.getInstance().dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        logoutRequest.addParam("device_token", AppManager.getInstance().getDeviceToken());
        logoutRequest.request();
    }

    protected void onLogoutSuccess() {
        AppManager.getInstance().getMenuTopController().setFirstRun(true);
        AppManager.getInstance().setSessionID(null);
        AppManager.getInstance().removeHeader();
        mDrawer.closeDrawer(GravityCompat.START);
        if (AppManager.getInstance().isDemo()) {
            AppManager.getInstance().setDemo(false);
        } else if (AppPreferences.isSignInNormal()) {
            AppPreferences.setSignInNormal(false);
            AppPreferences.clearCustomerInfo();
        } else {
            AppPreferences.setSignInQr(false);
            AppPreferences.clearCustomerInfoForQr();
        }
        AppEvent.getInstance().unregisterAllEvents();
        AppManager.getInstance().openLoginPage();
        AppManager.getInstance().disableDrawer();
    }

    protected void checkInternetConnection() {
        if (!Utils.isInternetAvailable()) {
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

    @Override
    protected void onResume() {
        isRunning = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isRunning = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

}
