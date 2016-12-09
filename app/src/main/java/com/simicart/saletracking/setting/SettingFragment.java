package com.simicart.saletracking.setting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Glenn on 12/8/2016.
 */

public class SettingFragment extends AppFragment {

    protected TextView tvPagingTitle, tvItemShownTitle;
    protected TextView tvPaging, tvPagingValue, tvItemSaleReport, tvItemLatestCustomers, tvLatestOrders;
    protected SwitchCompat swItemSaleReport, swItemLatestCustomers, swItemLatestOrders;
    protected RelativeLayout rlItemSaleReport, rlItemLatestCustomers, rlItemLatestOrders;

    protected int pagingPosition = 0;
    protected CharSequence[] pagingArr = {"20", "40", "60", "80", "100"};

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        initPaging();
        initItemsShown();

        return rootView;
    }

    protected void initPaging() {
        tvPagingTitle = (TextView) rootView.findViewById(R.id.tv_paging_title);
        tvPagingTitle.setTextColor(Color.BLACK);
        tvPagingTitle.setText("PAGING");

        tvPaging = (TextView) rootView.findViewById(R.id.tv_paging);
        tvPaging.setTextColor(Color.BLACK);
        tvPaging.setText("Items Per Pages");

        tvPagingValue = (TextView) rootView.findViewById(R.id.tv_paging_value);
        tvPagingValue.setTextColor(AppColor.getInstance().getThemeColor());
        tvPagingValue.setBackground(AppColor.getInstance().coloringIcon(R.drawable.border_line, "#fc9900"));
        int paging = AppPreferences.getPaging();
        pagingPosition = (paging/20) - 1;
        tvPagingValue.setText(String.valueOf(paging));

        tvPagingValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPagingChooser();
            }
        });
    }

    protected void initItemsShown() {
        tvItemShownTitle = (TextView) rootView.findViewById(R.id.tv_item_shown_title);
        tvItemShownTitle.setTextColor(Color.BLACK);
        tvItemShownTitle.setText("ITEMS SHOWN ON DASHBOARD");

        tvItemSaleReport = (TextView) rootView.findViewById(R.id.tv_item_sale_report);
        tvItemSaleReport.setTextColor(Color.BLACK);
        tvItemSaleReport.setText("Sales Reports");

        tvItemLatestCustomers = (TextView) rootView.findViewById(R.id.tv_item_latest_customer);
        tvItemLatestCustomers.setTextColor(Color.BLACK);
        tvItemLatestCustomers.setText("Latest Customers");

        tvLatestOrders = (TextView) rootView.findViewById(R.id.tv_item_latest_order);
        tvLatestOrders.setTextColor(Color.BLACK);
        tvLatestOrders.setText("Latest Orders");

        swItemSaleReport = (SwitchCompat) rootView.findViewById(R.id.sw_item_sale_report);
        swItemSaleReport.setChecked(AppPreferences.getShowSaleReport());

        swItemLatestCustomers = (SwitchCompat) rootView.findViewById(R.id.sw_item_latest_customer);
        swItemLatestCustomers.setChecked(AppPreferences.getShowLatestCustomer());

        swItemLatestOrders = (SwitchCompat) rootView.findViewById(R.id.sw_item_latest_order);
        swItemLatestOrders.setChecked(AppPreferences.getShowLatestOrder());

        rlItemSaleReport = (RelativeLayout) rootView.findViewById(R.id.rl_item_sale_report);
        rlItemSaleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemSaleReport.isChecked();

                if (isChecked) {
                    swItemSaleReport.setChecked(false);
                    AppPreferences.setShowSaleReport(false);
                } else {
                    swItemSaleReport.setChecked(true);
                    AppPreferences.setShowSaleReport(true);
                }
            }
        });

        rlItemLatestCustomers = (RelativeLayout) rootView.findViewById(R.id.rl_item_latest_customer);
        rlItemLatestCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemLatestCustomers.isChecked();

                if (isChecked) {
                    swItemLatestCustomers.setChecked(false);
                    AppPreferences.setShowLatestCustomer(false);
                } else {
                    swItemLatestCustomers.setChecked(true);
                    AppPreferences.setShowLatestCustomer(true);
                }
            }
        });

        rlItemLatestOrders = (RelativeLayout) rootView.findViewById(R.id.rl_item_latest_order);
        rlItemLatestOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemLatestOrders.isChecked();

                if (isChecked) {
                    swItemLatestOrders.setChecked(false);
                    AppPreferences.setShowLatestOrder(false);
                } else {
                    swItemLatestOrders.setChecked(true);
                    AppPreferences.setShowLatestOrder(true);
                }
            }
        });

    }

    protected void createPagingChooser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(pagingArr, pagingPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                pagingPosition = which;
                tvPagingValue.setText(pagingArr[which]);
                AppPreferences.setPaging(Integer.parseInt(pagingArr[which].toString()));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

}
