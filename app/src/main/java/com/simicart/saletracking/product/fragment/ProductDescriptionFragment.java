package com.simicart.saletracking.product.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.RatingCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.request.ProductDetailRequest;

import java.util.HashMap;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDescriptionFragment extends AppFragment {

    protected String mDescription;
    protected int mEdit;
    protected String mProductID;

    protected EditText etDescription;

    public static ProductDescriptionFragment newInstance(AppData data) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_description, container, false);

        if (mData != null) {
            mDescription = (String) getValueWithKey("description");
            mEdit = (int) getValueWithKey("edit");
            mProductID = (String)  getValueWithKey("product_id");
        }

        etDescription = (EditText) rootView.findViewById(R.id.et_description);
        etDescription.setTextColor(Color.BLACK);
        if (Utils.validateString(mDescription)) {
            Utils.setTextHtml(etDescription, mDescription);
        }

        AppCompatButton btSave = (AppCompatButton) rootView.findViewById(R.id.bt_save);
        AppColor.getInstance().initButton(btSave, "Save");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard();
                editProductDescription();
            }
        });

        if(mEdit == Constants.EditProductDescription.NONE) {
            etDescription.setEnabled(false);
            btSave.setVisibility(View.GONE);
        } else {
            etDescription.setEnabled(true);
            etDescription.requestFocus();
            btSave.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    protected void editProductDescription() {
        AppManager.getInstance().showDialogLoading();
        ProductDetailRequest editProductRequest = new ProductDetailRequest();
        editProductRequest.setRequestMethod(Request.Method.PUT);
        editProductRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                AppManager.getInstance().dismissDialogLoading();
                HashMap<String, Object> hmData = new HashMap<String, Object>();
                hmData.put("product_id", mProductID);
                AppManager.getInstance().openProductDetail(hmData);
            }
        });
        editProductRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                AppManager.getInstance().dismissDialogLoading();
                AppNotify.getInstance().showError(message);
            }
        });
        editProductRequest.setExtendUrl("simitracking/rest/v2/products/" + mProductID);
        if(mEdit == Constants.EditProductDescription.DESCRIPTION) {
            editProductRequest.addParamBody("description", etDescription.getText().toString());
        } else {
            editProductRequest.addParamBody("short_description", etDescription.getText().toString());
        }
        editProductRequest.request();
    }

}
