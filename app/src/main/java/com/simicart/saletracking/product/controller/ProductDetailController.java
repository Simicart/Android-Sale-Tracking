package com.simicart.saletracking.product.controller;

import android.view.View;

import com.android.volley.Request;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.component.EditProductInfoPopup;
import com.simicart.saletracking.product.delegate.EditProductCallBack;
import com.simicart.saletracking.product.entity.ProductEntity;
import com.simicart.saletracking.product.request.ProductDetailRequest;

import java.util.HashMap;

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
            }
        };

        mOnShortDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(true, false);
            }
        };

        mOnEditShortDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(true, true);
            }
        };

        mOnEditDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDescription(false, true);
            }
        };

        mOnEditProductInfoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCollection.containKey("product")) {
                    ProductEntity productEntity = (ProductEntity) mCollection.getDataWithKey("product");
                    if (productEntity != null) {
                        EditProductInfoPopup popup = new EditProductInfoPopup(productEntity);
                        popup.setEditProductCallBack(new EditProductCallBack() {
                            @Override
                            public void editProductInfo(String name, String quantity, String stock) {
                                requestEditProductInfo(name, quantity, stock);
                            }
                        });
                        popup.show();
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
                if(isShortDescription) {
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
                if(!isEdit) {
                    hmData.put("edit", Constants.EditProductDescription.NONE);
                } else {
                    if(isShortDescription) {
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

    protected void requestEditProductInfo(String name, String quantity, String stock) {
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
        editProductInfoRequest.addParamBody("name", name);
        editProductInfoRequest.addParamBody("qty", quantity);
        if(stock.equals("In Stock")) {
            editProductInfoRequest.addParamBody("is_in_stock", "1");
        } else {
            editProductInfoRequest.addParamBody("is_in_stock", "0");
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
