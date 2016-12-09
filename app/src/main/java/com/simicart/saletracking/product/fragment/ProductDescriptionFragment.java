package com.simicart.saletracking.product.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.common.Utils;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDescriptionFragment extends AppFragment {

    protected String mDescription;

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
        }

        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        tvDescription.setTextColor(Color.BLACK);
        if (Utils.validateString(mDescription)) {
            Utils.setTextHtml(tvDescription, mDescription);
        }

        return rootView;
    }
}
