package com.simicart.saletracking.base.component;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.popup.RowEntity;
import com.simicart.saletracking.common.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Martial on 1/9/2017.
 */

public class TimeRowComponent extends AppComponent {

    protected TextView tvTitle;
    protected DatePicker dpTime;

    protected RowEntity mRowEntity;

    public TimeRowComponent(RowEntity rowEntity) {
        super();
        mRowEntity = rowEntity;
    }

    @Override
    public View createView() {
        View rootView = mInflater.inflate(R.layout.component_time_row, null);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.BLACK);
        String title = mRowEntity.getTitle();
        if(Utils.validateString(title)) {
            tvTitle.setText(title);
        }

        dpTime = (DatePicker) rootView.findViewById(R.id.dp_time);
        String value = mRowEntity.getValue();
        if(value != null) {
            String[] splits = value.split("-");
            if(splits.length == 3) {
                String year = splits[0];
                String month = splits[1];
                String day = splits[2];

                int iDay = 0;
                int iMonth = 0;
                int iYear = 0;
                if(Utils.isInteger(day)) {
                    iDay = Integer.parseInt(day);
                }
                if(Utils.isInteger(month)) {
                    iMonth = Integer.parseInt(month);
                }
                if(Utils.isInteger(year)) {
                    iYear = Integer.parseInt(year);
                }

                dpTime.updateDate(iYear, iMonth, iDay);
            }
        }

        return rootView;
    }

    @Override
    public String getText() {
        Calendar cal = Calendar.getInstance();
        cal.set(dpTime.getYear(), dpTime.getMonth(), dpTime.getDayOfMonth());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }
}
