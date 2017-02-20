package com.simicart.saletracking.customer.controller;

import android.view.View;

import com.android.volley.Request;
import com.simicart.saletracking.base.component.EditCallback;
import com.simicart.saletracking.base.component.EditPopup;
import com.simicart.saletracking.base.component.RowEntity;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.customer.entity.CustomerEntity;
import com.simicart.saletracking.customer.request.CustomerDetailRequest;
import com.simicart.saletracking.order.fragment.ListOrdersFragment;
import com.simicart.saletracking.search.entity.SearchEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected String mCustomerID;
    protected View.OnClickListener mOnCustomerOrderClick;
    protected View.OnClickListener mOnCustomerAddressesClick;
    protected View.OnClickListener mOnEditSummaryClick;
    protected View.OnClickListener mOnEditInfoClick;

    @Override
    public void onStart() {
        requestCustomerDetail();
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestCustomerDetail() {
        mDelegate.showLoading();
        CustomerDetailRequest customerDetailRequest = new CustomerDetailRequest();
        customerDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        customerDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        customerDetailRequest.setExtendUrl("simitracking/rest/v2/customers/" + mCustomerID);
        customerDetailRequest.request();
    }

    protected void initListener() {
        mOnCustomerOrderClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_customer_orders");
                    AppManager.getInstance().trackWithMixPanel("customer_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, Object> hmData = new HashMap<>();
                if (mCollection != null) {
                    CustomerEntity customerEntity = (CustomerEntity) mCollection.getDataWithKey("customer");
                    SearchEntity searchEntity = new SearchEntity();
                    searchEntity.setKey("customer_email");
                    searchEntity.setQuery(customerEntity.getEmail());
                    hmData.put("search_entity", searchEntity);
                }
                hmData.put("orders_customer", "1");
                ListOrdersFragment orderFragment = ListOrdersFragment.newInstance(new AppData(hmData));
                orderFragment.setFragmentName("Orders");
                orderFragment.setDetail(true);
                AppManager.getInstance().replaceFragment(orderFragment);
                AppManager.getInstance().getMenuTopController().setOnDetail(true);
                AppManager.getInstance().getMenuTopController().showStorePicker(true);
            }
        };

        mOnCustomerAddressesClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_customer_addresses");
                    AppManager.getInstance().trackWithMixPanel("customer_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, Object> hmData = new HashMap<>();
                hmData.put("customer_id", mCustomerID);
                AppManager.getInstance().openListAddresses(hmData);
            }
        };

        mOnEditSummaryClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hmData = new HashMap<>();
                if (mCollection != null) {
                    CustomerEntity customerEntity = (CustomerEntity) mCollection.getDataWithKey("customer");
                    if (customerEntity != null) {
                        onEditSummary(customerEntity);
                    }
                }
            }
        };

        mOnEditInfoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hmData = new HashMap<>();
                if (mCollection != null) {
                    CustomerEntity customerEntity = (CustomerEntity) mCollection.getDataWithKey("customer");
                    if (customerEntity != null) {
                        onEditInfo(customerEntity);
                    }
                }
            }
        };
    }

    protected void onEditSummary(CustomerEntity customerEntity) {
        ArrayList<RowEntity> mListRows = new ArrayList<>();
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Prefix", "prefix", customerEntity.getPrefix(), false));
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "First Name", "firstname", customerEntity.getFirstName(), true));
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Middle Name", "middlename", customerEntity.getMiddleName(), false));
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Last Name", "lastname", customerEntity.getLastName(), true));
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Suffix", "suffix", customerEntity.getSuffix(), false));
        EditPopup editPopup = new EditPopup(mListRows, "Edit Customer Summary");
        editPopup.setEditCallback(new EditCallback() {
            @Override
            public void onEditComplete(HashMap<String, String> hmData) {
                requestEditCustomerInfo(hmData);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("edit_action", "customer_summary");
                    AppManager.getInstance().trackWithMixPanel("customer_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        editPopup.show();
    }

    protected void onEditInfo(CustomerEntity customerEntity) {
        ArrayList<RowEntity> mListRows = new ArrayList<>();
        mListRows.add(new RowEntity(Constants.RowType.TIME, "Date Of Birth", "dob", customerEntity.getDob(), false));
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Tax VAT", "taxvat", customerEntity.getTaxVAT(), false));
        EditPopup editPopup = new EditPopup(mListRows, "Edit Customer Information");
        editPopup.setEditCallback(new EditCallback() {
            @Override
            public void onEditComplete(HashMap<String, String> hmData) {
                requestEditCustomerInfo(hmData);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("edit_action", "customer_information");
                    AppManager.getInstance().trackWithMixPanel("customer_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        editPopup.show();
    }

    protected void requestEditCustomerInfo(HashMap<String, String> hmData) {
        mDelegate.showDialogLoading();
        CustomerDetailRequest editCustomerRequest = new CustomerDetailRequest();
        editCustomerRequest.setRequestMethod(Request.Method.PUT);
        editCustomerRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        editCustomerRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        editCustomerRequest.setExtendUrl("simitracking/rest/v2/customers/" + mCustomerID);
        for (Map.Entry<String, String> entry : hmData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            editCustomerRequest.addParamBody(key, value);
        }
        editCustomerRequest.request();
    }

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setCustomerID(String customerID) {
        mCustomerID = customerID;
    }

    public View.OnClickListener getOnCustomerOrderClick() {
        return mOnCustomerOrderClick;
    }

    public View.OnClickListener getOnCustomerAddressesClick() {
        return mOnCustomerAddressesClick;
    }

    public View.OnClickListener getOnEditInfoClick() {
        return mOnEditInfoClick;
    }

    public void setOnEditInfoClick(View.OnClickListener onEditInfoClick) {
        mOnEditInfoClick = onEditInfoClick;
    }

    public View.OnClickListener getOnEditSummaryClick() {
        return mOnEditSummaryClick;
    }

    public void setOnEditSummaryClick(View.OnClickListener onEditSummaryClick) {
        mOnEditSummaryClick = onEditSummaryClick;
    }
}
