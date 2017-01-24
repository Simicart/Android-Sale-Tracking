package com.simicart.saletracking.product.controller;

import android.view.View;

import com.android.volley.Request;
import com.simicart.saletracking.base.component.EditCallback;
import com.simicart.saletracking.base.component.EditPopup;
import com.simicart.saletracking.base.component.RowEntity;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.entity.ProductEntity;
import com.simicart.saletracking.product.request.ProductDetailRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDetailController extends AppController {

    protected AppDelegate mDelegate;
    protected String mProductID;
    protected View.OnClickListener mOnShortDescriptionClick;
    protected View.OnClickListener mOnDescriptionClick;
    protected View.OnClickListener mOnEditDescriptionClick;
    protected View.OnClickListener mOnEditShortDescriptionClick;
    protected View.OnClickListener mOnEditProductInfoClick;

    @Override
    public void onStart() {
        requestProductDetail();
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mCollection);
    }

    protected void requestProductDetail() {
        mDelegate.showLoading();
        ProductDetailRequest productDetailRequest = new ProductDetailRequest();
        productDetailRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        productDetailRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissLoading();
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        productDetailRequest.setExtendUrl("simitracking/rest/v2/products/" + mProductID);
        productDetailRequest.request();
    }

    protected void initListener() {
        mOnDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(false, false);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_description");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("product_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        mOnShortDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(true, false);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("action", "view_short_description");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("product_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        mOnEditShortDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(true, true);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("edit_action", "short_description");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("product_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        mOnEditDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(false, true);

                // Tracking with MixPanel
                try {
                    JSONObject object = new JSONObject();
                    object.put("edit_action", "description");
                    object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                    object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                    AppManager.getInstance().trackWithMixPanel("product_detail_action", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        mOnEditProductInfoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCollection.containKey("product")) {
                    ProductEntity productEntity = (ProductEntity) mCollection.getDataWithKey("product");
                    if (productEntity != null) {
                        openEditPopup(productEntity);
                    }
                }
            }
        };
    }

    protected void openDescription(boolean isShortDescription, boolean isEdit) {
        if (mCollection.containKey("product")) {
            ProductEntity productEntity = (ProductEntity) mCollection.getDataWithKey("product");
            if (productEntity != null) {
                HashMap<String, Object> hmData = new HashMap<>();
                if (isShortDescription) {
                    String shortDescription = productEntity.getShortDescription();
                    if (Utils.validateString(shortDescription)) {
                        hmData.put("description", shortDescription);
                        hmData.put("title", "Short Description");
                    }
                } else {
                    String description = productEntity.getDescription();
                    if (Utils.validateString(description)) {
                        hmData.put("description", description);
                        hmData.put("title", "Description");
                    }
                }
                if (!isEdit) {
                    hmData.put("edit", Constants.EditProductDescription.NONE);
                } else {
                    if (isShortDescription) {
                        hmData.put("edit", Constants.EditProductDescription.SHORT_DESCRIPTION);
                    } else {
                        hmData.put("edit", Constants.EditProductDescription.DESCRIPTION);
                    }
                    hmData.put("product_id", productEntity.getID());
                }
                AppManager.getInstance().openDescription(hmData);
            }
        }
    }

    protected void openEditPopup(ProductEntity productEntity) {
        ArrayList<RowEntity> mListRows = new ArrayList<>();
        mListRows.add(new RowEntity(Constants.RowType.TEXT, "Name", "name", productEntity.getName()));
        mListRows.add(new RowEntity(Constants.RowType.TEXT_NUMBER, "Quantity", "qty", productEntity.getQuantity()));
        String stocks[] = {"In Stock", "Out of Stock"};
        int selectedStock;
        if (productEntity.isInStock()) {
            selectedStock = 0;
        } else {
            selectedStock = 1;
        }
        mListRows.add(new RowEntity(Constants.RowType.SPINNER, "Stock Availability", "is_in_stock", stocks, selectedStock));
        EditPopup editPopup = new EditPopup(mListRows, "Edit Product Information");
        editPopup.setEditCallback(new EditCallback() {
            @Override
            public void onEditComplete(HashMap<String, String> hmData) {
                requestEditProductInfo(hmData);
            }
        });
        editPopup.show();
    }

    protected void requestEditProductInfo(HashMap<String, String> hmData) {
        mDelegate.showDialogLoading();
        ProductDetailRequest editProductInfoRequest = new ProductDetailRequest();
        editProductInfoRequest.setRequestMethod(Request.Method.PUT);
        editProductInfoRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                mDelegate.dismissDialogLoading();
                mCollection = collection;
                mDelegate.updateView(mCollection);
            }
        });
        editProductInfoRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                mDelegate.dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        editProductInfoRequest.setExtendUrl("simitracking/rest/v2/products/" + mProductID);

        for (Map.Entry<String, String> entry : hmData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("is_in_stock")) {
                if (value.equals("In Stock")) {
                    value = "1";
                } else {
                    value = "0";
                }
            }
            editProductInfoRequest.addParamBody(key, value);
        }

        // Tracking with MixPanel
        try {
            JSONObject object = new JSONObject();
            object.put("edit_action", "product_information");
            object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
            object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
            AppManager.getInstance().trackWithMixPanel("product_detail_action", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editProductInfoRequest.request();
    }

    public void setDelegate(AppDelegate delegate) {
        mDelegate = delegate;
    }

    public void setProductID(String productID) {
        mProductID = productID;
    }

    public View.OnClickListener getOnDescriptionClick() {
        return mOnDescriptionClick;
    }

    public View.OnClickListener getOnShortDescriptionClick() {
        return mOnShortDescriptionClick;
    }

    public View.OnClickListener getOnEditDescriptionClick() {
        return mOnEditDescriptionClick;
    }

    public View.OnClickListener getOnEditShortDescriptionClick() {
        return mOnEditShortDescriptionClick;
    }

    public View.OnClickListener getOnEditProductInfoClick() {
        return mOnEditProductInfoClick;
    }
}
