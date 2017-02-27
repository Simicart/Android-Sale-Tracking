package com.simicart.saletracking.base.manager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.simicart.saletracking.R;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;

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

    public void showPopupCustom(String message, String firstAction, final DialogCallBack firstListener,
                                String secondAction, final DialogCallBack secondListener,
                                final String thirdAction, final DialogCallBack thirdListener) {
        final Dialog dialog = new Dialog(AppManager.getInstance().getCurrentActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_custom);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        tvMessage.setText(message);

        TextView tvFirst = (TextView) dialog.findViewById(R.id.tv_first);
        if(Utils.validateString(firstAction)) {
            tvFirst.setTextColor(AppColor.getInstance().getThemeColor());
            tvFirst.setText(firstAction);
            tvFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if(firstListener != null) {
                        firstListener.onClick();
                    }
                }
            });
        } else {
            tvFirst.setVisibility(View.GONE);
        }

        TextView tvSecond = (TextView) dialog.findViewById(R.id.tv_second);
        if(Utils.validateString(secondAction)) {
            tvSecond.setTextColor(AppColor.getInstance().getThemeColor());
            tvSecond.setText(secondAction);
            tvSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if(secondListener != null) {
                        secondListener.onClick();
                    }
                }
            });
        } else {
            tvSecond.setVisibility(View.GONE);
        }

        TextView tvThird = (TextView) dialog.findViewById(R.id.tv_third);
        if(Utils.validateString(thirdAction)) {
            tvThird.setTextColor(AppColor.getInstance().getThemeColor());
            tvThird.setText(thirdAction);
            tvThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if(thirdListener != null) {
                        thirdListener.onClick();
                    }
                }
            });
        } else {
            tvThird.setVisibility(View.GONE);
        }

        dialog.show();
    }

}
