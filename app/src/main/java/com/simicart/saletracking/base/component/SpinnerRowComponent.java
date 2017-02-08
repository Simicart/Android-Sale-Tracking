package com.simicart.saletracking.base.component;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Martial on 1/7/2017.
 */

public class SpinnerRowComponent extends AppComponent {

    protected TextView tvTitle;
    protected Spinner spChooser;

    protected RowEntity mRowEntity;

    public SpinnerRowComponent(RowEntity rowEntity) {
        super();
        mRowEntity = rowEntity;
    }

    @Override
    public View createView() {
        rootView = mInflater.inflate(R.layout.component_spinner_row, null);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.BLACK);
        String title = mRowEntity.getTitle();
        if(Utils.validateString(title)) {
            if(mRowEntity.isRequired()) {
                title += "(*)";
            }
            tvTitle.setText(title);
        }

        spChooser = (Spinner) rootView.findViewById(R.id.sp_chooser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mRowEntity.getChooseValues());
        spChooser.setAdapter(adapter);
        spChooser.setSelection(mRowEntity.getCurrentSelected());

        return rootView;
    }

    @Override
    public String getText() {
        return (String) spChooser.getSelectedItem();
    }

    @Override
    public boolean isRequired() {
        return mRowEntity.isRequired();
    }

}
