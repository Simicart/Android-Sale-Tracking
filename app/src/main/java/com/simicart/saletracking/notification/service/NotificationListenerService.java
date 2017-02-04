package com.simicart.saletracking.notification.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GcmListenerService;
import com.simicart.saletracking.MainActivity;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppEvent;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.notification.entity.NotificationEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Glenn on 12/15/2016.
 */

public class NotificationListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String s, Bundle data) {
        String message = data.getString("message");
        showNotification(message);
    }

    private void showNotification(String message) {
        if (Utils.validateString(message)) {
            try {
                JSONObject json = new JSONObject(message);
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.parse(json);
                if (MainActivity.isRunning) {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("notification_entity", notificationEntity);
                    AppEvent.getInstance().dispatchEvent("com.simitracking.notification", data);
                } else {
                    showNotification(notificationEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void showNotification(NotificationEntity entity) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NOTIFICATION_DATA", entity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                .setSound(defaultSoundUri)
                .setLights(0xff00ff00, 300, 100)
                .setContentIntent(pendingIntent);

        String title = entity.getTitle();
        if(Utils.validateString(title)) {
            notificationBuilder.setContentTitle(title);
        }
        String content = entity.getContent();
        if(Utils.validateString(content)) {
            notificationBuilder.setContentText(entity.getContent());
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notif = notificationBuilder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int smallIconViewId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());

            if (smallIconViewId != 0) {
                if (notif.contentIntent != null)
                    notif.contentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                if (notif.headsUpContentView != null)
                    notif.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                if (notif.bigContentView != null)
                    notif.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);
            }
        }

        notificationManager.notify(Utils.generateViewId(), notif);
    }

}
