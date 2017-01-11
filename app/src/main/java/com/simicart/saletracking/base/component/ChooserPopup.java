package com.simicart.saletracking.base.component;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.Utils;

import java.util.ArrayList;

/**
 * Created by Martial on 1/11/2017.
 */

public class ChooserPopup {

    protected Context mContext;
    protected BottomSheetDialog bsdChooser;
    protected LayoutInflater mInflater;
    protected ChooserCallback mChooserCallback;
    ArrayList<String> mListValues;
    protected String mTitle;
    protected int mSelectedPosition;

    public ChooserPopup(ArrayList<String> listValues, int selectedPosition) {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        mListValues = listValues;
        mSelectedPosition = selectedPosition;
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
        View rootView = mInflater.inflate(R.layout.popup_chooser, null);

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        if(Utils.validateString(mTitle)) {
            tvTitle.setText(mTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        RadioGroup rgChooser = (RadioGroup) rootView.findViewById(R.id.rg_chooser);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        for(String value : mListValues) {
            RadioButton rbValue = new RadioButton(mContext);
            rbValue.setLayoutParams(params);
            rbValue.setPaddingRelative(Utils.toPixel(10), Utils.toPixel(10), Utils.toPixel(10), Utils.toPixel(10));
            rbValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            rbValue.setText(value);
            final int position = mListValues.indexOf(value);
            if(position == mSelectedPosition) {
                rbValue.setChecked(true);
            }
            rbValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if(mChooserCallback != null) {
                        mChooserCallback.onClick(position);
                    }
                }
            });
            rgChooser.addView(rbValue);
        }

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
