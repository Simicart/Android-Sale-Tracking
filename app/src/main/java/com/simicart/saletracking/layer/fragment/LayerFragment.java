package com.simicart.saletracking.layer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.layer.adapter.LayerAdapter;

/**
 * Created by Glenn on 12/2/2016.
 */

public class LayerFragment extends AppFragment {

    protected TextView tvTitle, tvClear;
    protected AppCompatButton btCancel;
    protected RecyclerView rvLayers;
    protected LayerAdapter mAdapter;
    protected String mTitle;
    protected int mFrom;

    public static LayerFragment newInstance(AppData data) {
        LayerFragment fragment = new LayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layer, container, false);

        if (mData != null) {
            mTitle = (String) getValueWithKey("title");
            mFrom = (int) getValueWithKey("from");
        }

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.BLACK);
        if (Utils.validateString(mTitle)) {
            tvTitle.setText(mTitle);
        }

        tvClear = (TextView) rootView.findViewById(R.id.tv_clear);
        tvClear.setTextColor(AppColor.getInstance().getButtonColor());
        tvClear.setText("Clear");
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHashMapData.remove("from");
                switch (mFrom) {
                    case Constants.Layer.FILTER:
                        mHashMapData.remove("status_layer");
                        break;
                    case Constants.Layer.SORT:
                        mHashMapData.remove("sort_layer");
                        break;
                    case Constants.Layer.TIME:
                        mHashMapData.remove("time_layer");
                        break;
                    default:
                        break;
                }
                AppManager.getInstance().openListOrders(mHashMapData);
            }
        });

        btCancel = (AppCompatButton) rootView.findViewById(R.id.bt_cancel);
        AppColor.getInstance().initButton(btCancel, "Cancel");

        rvLayers = (RecyclerView) rootView.findViewById(R.id.rv_layers);
        rvLayers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (mData != null) {
            mAdapter = new LayerAdapter(mHashMapData);
            rvLayers.setAdapter(mAdapter);
        }

        return rootView;
    }

}
