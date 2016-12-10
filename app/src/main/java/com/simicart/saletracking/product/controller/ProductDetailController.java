package com.simicart.saletracking.product.controller;

import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Utils;
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
                if (mCollection != null) {
                    if (mCollection.containKey("product")) {
                        ProductEntity productEntity = (ProductEntity) mCollection.getDataWithKey("product");
                        if (productEntity != null) {
                            String description = productEntity.getDescription();
                            if (Utils.validateString(description)) {
                                HashMap<String, Object> hmData = new HashMap<>();
                                hmData.put("description", description);
                                hmData.put("title", "Description");
                                AppManager.getInstance().openDescription(hmData);
                            }
                        }
                    }
                }
            }
        };

        mOnShortDescriptionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCollection.containKey("product")) {
                    ProductEntity productEntity = (ProductEntity) mCollection.getDataWithKey("product");
                    if (productEntity != null) {
                        String shortDescription = productEntity.getShortDescription();
                        if (Utils.validateString(shortDescription)) {
                            HashMap<String, Object> hmData = new HashMap<>();
                            hmData.put("description", shortDescription);
                            hmData.put("title", "Short Description");
                            AppManager.getInstance().openDescription(hmData);
                        }
                    }
                }
            }
        };
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
}
