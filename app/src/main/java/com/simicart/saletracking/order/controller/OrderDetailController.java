package com.simicart.saletracking.order.controller;

import android.view.View;

import com.android.volley.Request;
import com.simicart.saletracking.base.component.ChooserCallback;
import com.simicart.saletracking.base.component.ChooserPopup;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.order.entity.ActionEntity;
import com.simicart.saletracking.order.entity.OrderEntity;
import com.simicart.saletracking.order.request.OrderDetailRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected View.OnClickListener mOnCustomerClick;
    protected View.OnClickListener mOnEditOrderClick;
    protected String mOrderID;

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }

    @Override
    public void onStart() {
        requestOrderDetail(Request.Method.GET, null);

        mOnCustomerClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCollection != null) {
                    if (mCollection.containKey("order")) {
                        OrderEntity orderEntity = (OrderEntity) mCollection.getDataWithKey("order");
                        String customerID = orderEntity.getCustomerID();
                        HashMap<String,Object> hmData = new HashMap<>();
                        hmData.put("customer_id", customerID);

                        // Tracking with MixPanel
                        try {
                            JSONObject object = new JSONObject();
                            object.put("action", "view_customer_detail");
                            object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                            object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                            AppManager.getInstance().trackWithMixPanel("order_detail_action", object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AppManager.getInstance().openCustomerDetail(hmData);
                    }
                }
            }
        };

        mOnEditOrderClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditOrderChooser();
            }
        };
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestOrderDetail(int requestMethod, final HashMap<String,String> hmParams) {
        if(hmParams != null) {
            mDelegate.showDialogLoading();
        } else {
            mDelegate.showLoading();
        }
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if(hmParams != null) {
                    mDelegate.dismissDialogLoading();
                } else {
                    mDelegate.dismissLoading();
                }
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        orderDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                if(hmParams != null) {
                    mDelegate.dismissDialogLoading();
                } else {
                    mDelegate.dismissLoading();
                }
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        orderDetailRequest.setExtendUrl("simitracking/rest/v2/orders/" + mOrderID);
        orderDetailRequest.setRequestMethod(requestMethod);
        if(hmParams != null) {
            for (Map.Entry<String, String> entry : hmParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                orderDetailRequest.addParamBody(key, value);
            }
        }
        orderDetailRequest.request();
    }

    protected void showEditOrderChooser() {
        if(mCollection != null && mCollection.containKey("action")) {
            final ArrayList<ActionEntity> listActions = (ArrayList<ActionEntity>) mCollection.getDataWithKey("action");
            if(listActions.size() > 0) {
                ArrayList<String> listSActions = new ArrayList<>();
                for(ActionEntity actionEntity : listActions) {
                    listSActions.add(actionEntity.getValue());
                }
                ChooserPopup chooserPopup = new ChooserPopup(listSActions, -1);
                chooserPopup.setChooserCallback(new ChooserCallback() {
                    @Override
                    public void onClick(int position) {
                        String action = listActions.get(position).getKey();
                        HashMap<String,String> hmParams = new HashMap<String, String>();
                        hmParams.put("status", action);
                        requestOrderDetail(Request.Method.PUT, hmParams);
                    }
                });
                chooserPopup.setTitle("Update Order");
                chooserPopup.show();
            }
        }
    }

    public View.OnClickListener getOnCustomerClick() {
        return mOnCustomerClick;
    }

    public View.OnClickListener getOnEditOrderClick() {
        return mOnEditOrderClick;
    }
}
