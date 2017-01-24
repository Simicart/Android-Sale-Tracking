package com.simicart.saletracking.order.controller;

import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.order.entity.OrderEntity;
import com.simicart.saletracking.order.request.OrderDetailRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected View.OnClickListener mOnCustomerClick;
    protected String mOrderID;

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }

    @Override
    public void onStart() {
        requestOrderDetail();

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
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestOrderDetail() {
        mDelegate.showLoading();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        orderDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        orderDetailRequest.setExtendUrl("simitracking/rest/v2/orders/" + mOrderID);
        orderDetailRequest.request();
    }

    public View.OnClickListener getOnCustomerClick() {
        return mOnCustomerClick;
    }
}
