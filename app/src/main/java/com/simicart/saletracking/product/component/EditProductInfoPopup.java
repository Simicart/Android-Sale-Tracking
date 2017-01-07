package com.simicart.saletracking.product.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.delegate.EditProductCallBack;
import com.simicart.saletracking.product.entity.ProductEntity;

/**
 * Created by Martial on 1/7/2017.
 */

public class EditProductInfoPopup {

    protected ProductEntity mProduct;
    protected Context mContext;
    protected ProgressDialog pdEdit;
    protected LayoutInflater mInflater;
    protected EditProductCallBack mEditProductCallBack;

    public EditProductInfoPopup(ProductEntity entity) {
        mProduct = entity;
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
        createPopup();
    }

    protected void createPopup() {
        pdEdit = ProgressDialog.show(mContext, null, null, true, false);
        View contentView = createView();
        pdEdit.setContentView(contentView);
        pdEdit.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        pdEdit.setCanceledOnTouchOutside(true);
    }

    protected View createView() {
        View rootView = mInflater.inflate(R.layout.popup_edit_product_info, null);

        TextInputLayout tilName = (TextInputLayout) rootView.findViewById(R.id.til_name);
        tilName.setHint("Name");
        final EditText etName = (EditText) rootView.findViewById(R.id.et_name);
        String name = mProduct.getName();
        if(Utils.validateString(name)) {
            etName.setText(name);
        }

        TextInputLayout tilQty = (TextInputLayout) rootView.findViewById(R.id.til_qty);
        tilQty.setHint("Quantity");
        final EditText etQty = (EditText) rootView.findViewById(R.id.et_qty);
        String qty = mProduct.getQuantity();
        if(Utils.validateString(qty)) {
            etQty.setText(qty);
        }

        TextView tvStockLabel = (TextView) rootView.findViewById(R.id.tv_stock_label);
        tvStockLabel.setTextColor(Color.BLACK);
        tvStockLabel.setText("Stock Availability");
        final Spinner spStock = (Spinner) rootView.findViewById(R.id.sp_stock);
        String stocks[] = {"In Stock","Out of Stock"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, stocks);
        spStock.setAdapter(adapter);

        AppCompatButton btSave = (AppCompatButton) rootView.findViewById(R.id.bt_save);
        AppColor.getInstance().initButton(btSave, "Save");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String quantity = etQty.getText().toString();
                String stock = (String) spStock.getSelectedItem();
                if(Utils.validateString(name) && Utils.validateString(quantity)) {
                    Utils.hideKeyboard();
                    dismiss();
                    if(mEditProductCallBack != null) {
                        mEditProductCallBack.editProductInfo(name, quantity, stock);
                    }
                } else {
                    AppNotify.getInstance().showToast("Cannot leave blank field!");
                }
            }
        });

        return  rootView;
    }

    public void show() {
        pdEdit.show();
    }

    public void dismiss() {
        if (pdEdit.isShowing()) {
            pdEdit.dismiss();
        }
    }

    public void setEditProductCallBack(EditProductCallBack editProductCallBack) {
        mEditProductCallBack = editProductCallBack;
    }
}
