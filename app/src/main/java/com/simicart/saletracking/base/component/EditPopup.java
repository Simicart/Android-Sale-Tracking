package com.simicart.saletracking.base.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.popup.RowEntity;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Martial on 1/7/2017.
 */

public class EditPopup {

    protected Context mContext;
    protected BottomSheetDialog bsdEdit;
    protected LayoutInflater mInflater;
    protected ArrayList<RowEntity> mListEntityRows;
    protected ArrayList<AppComponent> mListRows;
    protected EditCallback mEditCallback;
    protected String mTitle;

    public EditPopup(ArrayList<RowEntity> listEntityRows, String title) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        mListEntityRows = listEntityRows;
        mTitle = title;
        createPopup();
    }

    protected void createPopup() {
        bsdEdit = new BottomSheetDialog(mContext);
        View contentView = createView();
        bsdEdit.setContentView(contentView);
        bsdEdit.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        bsdEdit.setCanceledOnTouchOutside(true);
    }

    protected View createView() {
        View rootView = mInflater.inflate(R.layout.popup_edit, null);
        LinearLayout llFields = (LinearLayout) rootView.findViewById(R.id.ll_fields);

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        if(Utils.validateString(mTitle)) {
            tvTitle.setText(mTitle);
        }

        mListRows = new ArrayList<>();
        for(RowEntity rowEntity : mListEntityRows) {
            int type = rowEntity.getType();
            AppComponent row = null;
            if(type == Constants.RowType.TEXT || type == Constants.RowType.TEXT_NUMBER) {
                row = new EditTextRowComponent(rowEntity);
            } else if(type == Constants.RowType.SPINNER) {
                row = new SpinnerRowComponent(rowEntity);
            } else if(type == Constants.RowType.TIME) {
                row = new TimeRowComponent(rowEntity);
            }
            row.setKey(rowEntity.getKey());
            mListRows.add(row);
            View view = row.createView();
            if(view != null) {
                llFields.addView(view);
            }
        }

        AppCompatButton btSave = (AppCompatButton) rootView.findViewById(R.id.bt_save);
        btSave.setText("Save");
        btSave.setTextColor(AppColor.getInstance().getButtonColor());
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> hmData = new HashMap<String, String>();
                for(AppComponent row : mListRows) {
                    String text = row.getText();
                    if(Utils.validateString(text)) {
                        if(row.getKey().equals("dob")) {
                            String[] splits = text.split("-");
                            if(splits.length == 3) {
                                String year = splits[0];
                                String month = splits[1];
                                String day = splits[2];

                                if(Utils.validateString(year)) {
                                    hmData.put("year", year);
                                }
                                if(Utils.validateString(month)) {
                                    hmData.put("month", month);
                                }
                                if(Utils.validateString(day)) {
                                    hmData.put("day", day);
                                }
                            }
                        } else {
                            hmData.put(row.getKey(), text);
                        }
                    } else {
                        AppNotify.getInstance().showToast("Cannot leave blank field!");
                        break;
                    }
                    if(mListRows.indexOf(row) == mListRows.size() - 1 && mEditCallback != null) {
                        dismiss();
                        mEditCallback.onEditComplete(hmData);
                    }
                }
            }
        });

        return rootView;
    }

    public void show() {
        bsdEdit.show();
    }

    public void dismiss() {
        if (bsdEdit.isShowing()) {
            bsdEdit.dismiss();
        }
    }

    public void setEditCallback(EditCallback editCallback) {
        mEditCallback = editCallback;
    }
}
