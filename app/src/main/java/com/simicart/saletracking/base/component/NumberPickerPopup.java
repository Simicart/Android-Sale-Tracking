package com.simicart.saletracking.base.component;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;

import java.util.ArrayList;

/**
 * Created by Martial on 1/11/2017.
 */

public class NumberPickerPopup {

    protected Context mContext;
    protected BottomSheetDialog bsdChooser;
    protected LayoutInflater mInflater;
    protected ChooserCallback mChooserCallback;
    protected String mTitle;
    protected int minValue;
    protected int maxValue;
    protected int mOffset;
    protected int mCurrent;

    public NumberPickerPopup(int minValue, int maxValue, int offset, int current) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.mOffset = offset;
        this.mCurrent = current;
        createPopup();
    }

    protected void createPopup() {
        bsdChooser = new BottomSheetDialog(mContext);
        View contentView = createView();
        bsdChooser.setContentView(contentView);
        bsdChooser.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        bsdChooser.setCanceledOnTouchOutside(true);
    }

    protected View createView() {
        View rootView = mInflater.inflate(R.layout.popup_number_picker, null);

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        if(Utils.validateString(mTitle)) {
            tvTitle.setText(mTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        final NumberPicker npPicker = (NumberPicker) rootView.findViewById(R.id.np_picker);
        npPicker.setMinValue(minValue);
        npPicker.setMaxValue(maxValue);
        npPicker.setValue(mCurrent);
        npPicker.setWrapSelectorWheel(false);
        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value + mOffset;
                return "" + temp;
            }
        };
        npPicker.setFormatter(formatter);

        AppCompatButton btSave = (AppCompatButton) rootView.findViewById(R.id.bt_save);
        btSave.setText("Save");
        btSave.setTextColor(AppColor.getInstance().getButtonColor());
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(mChooserCallback != null) {
                    mChooserCallback.onClick(npPicker.getValue());
                }
            }
        });

        return rootView;
    }

    public void show() {
        bsdChooser.show();
    }

    public void dismiss() {
        if (bsdChooser.isShowing()) {
            bsdChooser.dismiss();
        }
    }

    public void setChooserCallback(ChooserCallback chooserCallback) {
        mChooserCallback = chooserCallback;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
