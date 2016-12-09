package com.simicart.saletracking.base.manager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Glenn on 11/26/2016.
 */

public class AppNotify {

    public static AppNotify instance;

    public static AppNotify getInstance() {
        if (instance == null) {
            instance = new AppNotify();
        }
        return instance;
    }

    public void showToast(String mes) {
        Toast toast = Toast.makeText(AppManager.getInstance().getCurrentActivity(), mes, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppManager.getInstance().getCurrentActivity());
        builder.setTitle("Warning...");
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
