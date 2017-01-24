package com.simicart.saletracking.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.ChooserCallback;
import com.simicart.saletracking.base.component.ChooserPopup;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/8/2016.
 */

public class SettingFragment extends AppFragment {

    protected TextView tvPagingTitle, tvItemShownTitle, tvCurrencyTitle;
    protected TextView tvPaging, tvPagingValue, tvItemSaleReport, tvItemLatestCustomers, tvItemLatestOrders, tvItemBestSellers, tvCurrencyPosition,
            tvCurrencyPositionValue, tvSeparator, tvSeparatorValue, tvNumberDecimals, tvNumberDecimalsValue;
    protected SwitchCompat swItemSaleReport, swItemLatestCustomers, swItemLatestOrders, swItemBestSellers;
    protected RelativeLayout rlItemSaleReport, rlItemLatestCustomers, rlItemLatestOrders, rlItemBestSellers;

    protected int pagingPosition = 0;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        initPaging();
        initItemsShown();
        initCurrency();

        return rootView;
    }

    protected void initPaging() {
        tvPagingTitle = (TextView) rootView.findViewById(R.id.tv_paging_title);
        tvPagingTitle.setText("Paging");

        tvPaging = (TextView) rootView.findViewById(R.id.tv_paging);
        tvPaging.setText("Items Per Pages");

        tvPagingValue = (TextView) rootView.findViewById(R.id.tv_paging_value);
        tvPagingValue.setTextColor(AppColor.getInstance().getThemeColor());
        tvPagingValue.setBackground(AppColor.getInstance().coloringIcon(R.drawable.border_line, "#fc9900"));
        int paging = AppPreferences.getPaging();
        pagingPosition = (paging / 20) - 1;
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
        tvItemShownTitle.setText("Items Shown On Dashboard");

        tvItemSaleReport = (TextView) rootView.findViewById(R.id.tv_item_sale_report);
        tvItemSaleReport.setText("Sales Reports");

        tvItemBestSellers = (TextView) rootView.findViewById(R.id.tv_item_best_seller);
        tvItemBestSellers.setText("Best Sellers");

        tvItemLatestCustomers = (TextView) rootView.findViewById(R.id.tv_item_latest_customer);
        tvItemLatestCustomers.setText("Latest Customers");

        tvItemLatestOrders = (TextView) rootView.findViewById(R.id.tv_item_latest_order);
        tvItemLatestOrders.setText("Latest Orders");

        swItemSaleReport = (SwitchCompat) rootView.findViewById(R.id.sw_item_sale_report);
        swItemSaleReport.setChecked(AppPreferences.getShowSaleReport());

        swItemBestSellers = (SwitchCompat) rootView.findViewById(R.id.sw_item_best_seller);
        swItemBestSellers.setChecked(AppPreferences.getShowBestSellers());

        swItemLatestCustomers = (SwitchCompat) rootView.findViewById(R.id.sw_item_latest_customer);
        swItemLatestCustomers.setChecked(AppPreferences.getShowLatestCustomer());

        swItemLatestOrders = (SwitchCompat) rootView.findViewById(R.id.sw_item_latest_order);
        swItemLatestOrders.setChecked(AppPreferences.getShowLatestOrder());

        rlItemSaleReport = (RelativeLayout) rootView.findViewById(R.id.rl_item_sale_report);
        rlItemSaleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemSaleReport.isChecked();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    if (isChecked) {
                        object.put("show_reports_on_dashboard", "enable");
                        swItemSaleReport.setChecked(false);
                        AppPreferences.setShowSaleReport(false);
                    } else {
                        object.put("show_reports_on_dashboard", "disable");
                        swItemSaleReport.setChecked(true);
                        AppPreferences.setShowSaleReport(true);
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rlItemBestSellers = (RelativeLayout) rootView.findViewById(R.id.rl_item_best_seller);
        rlItemBestSellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemBestSellers.isChecked();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    if (isChecked) {
                        object.put("show_bestsellers_on_dashboard", "enable");
                        swItemBestSellers.setChecked(false);
                        AppPreferences.setShowBestSellers(false);
                    } else {
                        object.put("show_bestsellers_on_dashboard", "disable");
                        swItemBestSellers.setChecked(true);
                        AppPreferences.setShowBestSellers(true);
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rlItemLatestCustomers = (RelativeLayout) rootView.findViewById(R.id.rl_item_latest_customer);
        rlItemLatestCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemLatestCustomers.isChecked();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    if (isChecked) {
                        object.put("show_latest_customers_on_dashboard", "enable");
                        swItemLatestCustomers.setChecked(false);
                        AppPreferences.setShowLatestCustomer(false);
                    } else {
                        object.put("show_latest_customers_on_dashboard", "disable");
                        swItemLatestCustomers.setChecked(true);
                        AppPreferences.setShowLatestCustomer(true);
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rlItemLatestOrders = (RelativeLayout) rootView.findViewById(R.id.rl_item_latest_order);
        rlItemLatestOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = swItemLatestOrders.isChecked();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    if (isChecked) {
                        object.put("show_last_orders_on_dashboard", "enable");
                        swItemLatestOrders.setChecked(false);
                        AppPreferences.setShowLatestOrder(false);
                    } else {
                        object.put("show_lastest_orders_on_dashboard", "disable");
                        swItemLatestOrders.setChecked(true);
                        AppPreferences.setShowLatestOrder(true);
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void initCurrency() {
        tvCurrencyTitle = (TextView) rootView.findViewById(R.id.tv_currency_title);
        tvCurrencyTitle.setText("Currency");

        tvCurrencyPosition = (TextView) rootView.findViewById(R.id.tv_currency_position);
        tvCurrencyPosition.setText("Currency Position");

        tvCurrencyPositionValue = (TextView) rootView.findViewById(R.id.tv_currency_position_value);
        tvCurrencyPositionValue.setTextColor(AppColor.getInstance().getThemeColor());
        tvCurrencyPositionValue.setBackground(AppColor.getInstance().coloringIcon(R.drawable.border_line, "#fc9900"));
        showCurrencyPositionValue();
        tvCurrencyPositionValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCurrencyPositionChooser();
            }
        });

        tvSeparator = (TextView) rootView.findViewById(R.id.tv_separator_position);
        tvSeparator.setText("Separator");

        tvSeparatorValue = (TextView) rootView.findViewById(R.id.tv_separator_position_value);
        tvSeparatorValue.setTextColor(AppColor.getInstance().getThemeColor());
        tvSeparatorValue.setBackground(AppColor.getInstance().coloringIcon(R.drawable.border_line, "#fc9900"));
        showSeparator();
        tvSeparatorValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSeparatorChooser();
            }
        });

        tvNumberDecimals = (TextView) rootView.findViewById(R.id.tv_number_decimals);
        tvNumberDecimals.setText("Number of Decimals");

        tvNumberDecimalsValue = (TextView) rootView.findViewById(R.id.tv_number_decimals_value);
        tvNumberDecimalsValue.setTextColor(AppColor.getInstance().getThemeColor());
        tvNumberDecimalsValue.setBackground(AppColor.getInstance().coloringIcon(R.drawable.border_line, "#fc9900"));
        tvNumberDecimalsValue.setText("" + AppPreferences.getNumberOfDecimals());
        tvNumberDecimalsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNumberOfDecimalsChooser();
            }
        });
    }

    protected void createPagingChooser() {
        ArrayList<String> listPaging = new ArrayList<>();
        listPaging.add("20");
        listPaging.add("40");
        listPaging.add("60");
        listPaging.add("80");
        listPaging.add("100");

        ChooserPopup chooserPopup = new ChooserPopup(listPaging, pagingPosition);
        chooserPopup.setChooserCallback(new ChooserCallback() {
            @Override
            public void onClick(int position) {
                AppPreferences.setPaging((position + 1) * 20);
                showPagingValue();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("paging", (position + 1) * 20);
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        chooserPopup.show();
    }

    protected void createCurrencyPositionChooser() {
        ArrayList<String> listCurrencyPosition = new ArrayList<>();
        listCurrencyPosition.add("Left ($99.00)");
        listCurrencyPosition.add("Right (99.00$)");
        listCurrencyPosition.add("Left Space ($ 99.00)");
        listCurrencyPosition.add("Right Space ($ 99.00)");

        ChooserPopup chooserPopup = new ChooserPopup(listCurrencyPosition, AppPreferences.getCurrencyPosition() - 1);
        chooserPopup.setChooserCallback(new ChooserCallback() {
            @Override
            public void onClick(int position) {
                AppPreferences.setCurrencyPosition(position + 1);
                showCurrencyPositionValue();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    switch (position+1) {
                        case Constants.CurrencyPosition.LEFT:
                            object.put("currency_position", "left");
                            break;
                        case Constants.CurrencyPosition.RIGHT:
                            object.put("currency_position", "right");
                            break;
                        case Constants.CurrencyPosition.LEFT_SPACE:
                            object.put("currency_position", "left_space");
                            break;
                        case Constants.CurrencyPosition.RIGHT_SPACE:
                            object.put("currency_position", "right_space");
                            break;
                        default:
                            break;
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        chooserPopup.show();
    }

    protected void createSeparatorChooser() {
        ArrayList<String> listSeparator = new ArrayList<>();
        listSeparator.add("Type 1: 1.000,00");
        listSeparator.add("Type 2: 1,000.00");

        ChooserPopup chooserPopup = new ChooserPopup(listSeparator, AppPreferences.getSeparator() - 1);
        chooserPopup.setChooserCallback(new ChooserCallback() {
            @Override
            public void onClick(int position) {
                AppPreferences.setSeparator(position + 1);
                showSeparator();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    switch (position+1) {
                        case Constants.Separator.DOT_FIRST:
                            object.put("currency_separator", "type_1");
                            break;
                        case Constants.Separator.COMMA_FIRST:
                            object.put("currency_separator", "type_2");
                            break;
                        default:
                            break;
                    }
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        chooserPopup.show();
    }

    protected void createNumberOfDecimalsChooser() {
        ArrayList<String> listNumberOfDecimals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listNumberOfDecimals.add("" + i);
        }

        ChooserPopup chooserPopup = new ChooserPopup(listNumberOfDecimals, AppPreferences.getNumberOfDecimals());
        chooserPopup.setChooserCallback(new ChooserCallback() {
            @Override
            public void onClick(int position) {
                AppPreferences.setNumberOfDecimals(position);
                showNumberOfDecimals();

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("number_of_decimals", position);
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("setting", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        chooserPopup.show();
    }

    protected void showPagingValue() {
        int paging = AppPreferences.getPaging();
        pagingPosition = (paging / 20) - 1;
        tvPagingValue.setText(String.valueOf(paging));
    }

    protected void showCurrencyPositionValue() {
        int currencyPosition = AppPreferences.getCurrencyPosition();
        String currencyPositionValue = "";
        switch (currencyPosition) {
            case Constants.CurrencyPosition.LEFT:
                currencyPositionValue = "Left ($99.00)";
                break;
            case Constants.CurrencyPosition.RIGHT:
                currencyPositionValue = "Right (99.00$)";
                break;
            case Constants.CurrencyPosition.LEFT_SPACE:
                currencyPositionValue = "Left Space ($ 99.00)";
                break;
            case Constants.CurrencyPosition.RIGHT_SPACE:
                currencyPositionValue = "Right Space (99.00 $)";
                break;
            default:
                break;
        }
        tvCurrencyPositionValue.setText(currencyPositionValue);
    }

    protected void showSeparator() {
        if (AppPreferences.getSeparator() == Constants.Separator.DOT_FIRST) {
            tvSeparatorValue.setText("Type 1: 1.000,00");
        } else {
            tvSeparatorValue.setText("Type 2: 1,000.00");
        }
    }

    protected void showNumberOfDecimals() {
        tvNumberDecimalsValue.setText("" + AppPreferences.getNumberOfDecimals());
    }

}
