package com.simicart.saletracking.base.component;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.popup.RowEntity;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Martial on 1/7/2017.
 */

public class EditTextRowComponent extends AppComponent {

    protected TextInputLayout tilTitle;
    protected EditText etText;

    protected RowEntity mRowEntity;

    public EditTextRowComponent(RowEntity rowEntity) {
        super();
        mRowEntity = rowEntity;
    }

    @Override
    public View createView() {
        rootView = mInflater.inflate(R.layout.component_edittext_row, null);

        tilTitle = (TextInputLayout) rootView.findViewById(R.id.til_title);
        String title = mRowEntity.getTitle();
        if(Utils.validateString(title)) {
            tilTitle.setHint(title);
        }
        etText = (EditText) rootView.findViewById(R.id.et_text);
        etText.setTextColor(Color.BLACK);
        if(mRowEntity.getType() == Constants.RowType.TEXT_NUMBER) {
            etText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        String value = mRowEntity.getValue();
        if(value != null) {
            etText.setText(value);
        }

        return rootView;
    }

    @Override
    public String getText() {
        return etText.getText().toString();
    }

}
