package com.simicart.saletracking.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Martial on 2/4/2017.
 */

public class AppEvent {

    public static AppEvent instance;
    protected static ArrayList<BroadcastReceiver> mListEvents = new ArrayList<>();

    public static AppEvent getInstance() {
        if(instance == null) {
            instance = new AppEvent();
        }
        return instance;
    }

    public void dispatchEvent(String eventName, HashMap<String, Object> hmData) {
        Context context = AppManager.getInstance().getCurrentActivity();
        Intent intent = new Intent(eventName);
        Bundle bundle = new Bundle();
        AppData data = new AppData(hmData);
        bundle.putParcelable("entity", data);
        intent.putExtra("data", bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);
    }

    public void registerEvent(String eventName, BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter(eventName);
        Context context = AppManager.getInstance().getCurrentActivity();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
        mListEvents.add(receiver);
    }

    public void unregisterAllEvents() {
        Context context = AppManager.getInstance().getCurrentActivity();
        for(BroadcastReceiver broadcastReceiver : mListEvents) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
        }
    }

}
