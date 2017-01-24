package com.simicart.saletracking.order.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.layer.entity.LayerEntity;
import com.simicart.saletracking.layer.entity.TimeLayerEntity;
import com.simicart.saletracking.order.delegate.ListOrdersDelegate;
import com.simicart.saletracking.order.request.ListOrdersRequest;
import com.simicart.saletracking.search.entity.SearchEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/28/2016.
 */

public class ListOrdersController extends AppController {

    protected ListOrdersDelegate mDelegate;
    protected RecyclerView.OnScrollListener mOnListScroll;
    protected View.OnClickListener mOnPreviousPageClick;
    protected View.OnClickListener mOnNextPageClick;
    protected View.OnClickListener mOnSearchClick;
    protected View.OnClickListener mOnStatusFilterClick;
    protected View.OnClickListener mOnSortClick;
    protected View.OnClickListener mOnTimeFilterClick;
    protected SearchEntity mSearchEntity;
    protected LayerEntity mStatusFilter;
    protected LayerEntity mSortEntity;
    protected LayerEntity mTimeEntity;
    protected HashMap<String, Object> hmData;
    protected boolean isDetail;

    protected int mCurrentPage = 1;
    protected int mTotalPage;
    protected int mOffset = 0;
    protected int mLimit = AppPreferences.getPaging();
    protected boolean isFirstRequest = true;

    @Override
    public void onStart() {
        parseData();
        requestListOrders();
        initListener();
    }

    @Override
    public void onResume() {
        requestListOrders();
    }

    protected void requestListOrders() {
        if (isFirstRequest) {
            mDelegate.showLoading();
        } else {
            mDelegate.showDialogLoading();
        }
        ListOrdersRequest mListOrdersRequest = new ListOrdersRequest();
        mListOrdersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if (isFirstRequest) {
                    mDelegate.dismissLoading();
                    isFirstRequest = false;
                } else {
                    mDelegate.dismissDialogLoading();
                }
                mCollection = collection;
                if (collection != null) {
                    if (collection.containKey("total")) {
                        int totalResult = (int) collection.getDataWithKey("total");
                        mTotalPage = totalResult / mLimit;
                        if (totalResult % mLimit != 0 || mTotalPage == 0) {
                            mTotalPage += 1;
                        }
                    }
                }
                mDelegate.showPage(mCurrentPage, mTotalPage);
                mDelegate.updateView(mCollection);
            }
        });
        mListOrdersRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                if (isFirstRequest) {
                    mDelegate.dismissLoading();
                } else {
                    mDelegate.dismissDialogLoading();
                }
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        mListOrdersRequest.setExtendUrl("simitracking/rest/v2/orders");
        mListOrdersRequest.addParam("limit", String.valueOf(mLimit));
        mListOrdersRequest.addParam("offset", String.valueOf(mOffset));
        try {
            JSONObject object = new JSONObject();
            if (mSearchEntity != null) {
                mListOrdersRequest.addSearchParam(mSearchEntity.getKey(), mSearchEntity.getQuery());
                object.put("search_action", mSearchEntity.getKey());
            }
            if (mStatusFilter != null) {
                mListOrdersRequest.addFilterParam(mStatusFilter.getKey(), mStatusFilter.getValue());
                object.put("filter_action", mStatusFilter.getKey());
            }
            if (mSortEntity != null) {
                mListOrdersRequest.addSortParam(mSortEntity.getKey());
                object.put("sort_action", mSortEntity.getKey());
                mListOrdersRequest.addParam("dir", mSortEntity.getValue());
                object.put("dir", mSortEntity.getValue());
            } else {
                mListOrdersRequest.addSortDirDESCParam();
                object.put("dir", "desc");
            }
            if (mTimeEntity != null) {
                TimeLayerEntity timeLayerEntity = (TimeLayerEntity) mTimeEntity;
                mListOrdersRequest.addParam(timeLayerEntity.getFromDateKey(), timeLayerEntity.getFromDate());
                mListOrdersRequest.addParam(timeLayerEntity.getToDateKey(), timeLayerEntity.getToDate());
                object.put("time_filter_action", timeLayerEntity.getKey());
            }
            object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
            object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
            AppManager.getInstance().trackWithMixPanel("list_orders_action", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mListOrdersRequest.request();
    }

    protected void initListener() {

        mOnListScroll = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) {
                    mDelegate.showBottom(false);
                } else {
                    mDelegate.showBottom(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        };

        mOnNextPageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage < mTotalPage) {
                    mCurrentPage++;
                    mOffset += mLimit;
                    requestListOrders();

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "next_page");
                        object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                        object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                        AppManager.getInstance().trackWithMixPanel("list_orders_action", object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        mOnPreviousPageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage > 1) {
                    mCurrentPage--;
                    mOffset -= mLimit;
                    requestListOrders();

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "previous_page");
                        object.put("customer_identity", AppManager.getInstance().getCurrentUser().getEmail());
                        object.put("customer_ip", AppManager.getInstance().getCurrentUser().getIP());
                        AppManager.getInstance().trackWithMixPanel("list_orders_action", object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        mOnSearchClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hmData == null) {
                    hmData = new HashMap<>();
                }
                hmData.put("search_entity", mSearchEntity);
                hmData.put("from", Constants.Search.ORDER);
                hmData.put("is_detail", isDetail);
                AppManager.getInstance().openSearch(hmData);
            }
        };

        mOnStatusFilterClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hmData == null) {
                    hmData = new HashMap<>();
                }
                hmData.put("status_layer", mStatusFilter);
                if (mCollection.containKey("layer_status")) {
                    hmData.put("list_status_layer", (ArrayList<LayerEntity>) mCollection.getDataWithKey("layer_status"));
                }
                hmData.put("from", Constants.Layer.FILTER);
                hmData.put("title", "Select Status To Filter");
                AppManager.getInstance().openLayer(hmData);
            }
        };

        mOnSortClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hmData == null) {
                    hmData = new HashMap<>();
                }
                hmData.put("sort_layer", mSortEntity);
                hmData.put("from", Constants.Layer.SORT);
                hmData.put("title", "Sort By");
                AppManager.getInstance().openLayer(hmData);
            }
        };

        mOnTimeFilterClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hmData == null) {
                    hmData = new HashMap<>();
                }
                hmData.put("time_layer", mTimeEntity);
                hmData.put("from", Constants.Layer.TIME);
                hmData.put("title", "Select Time Range To Filter");
                AppManager.getInstance().openLayer(hmData);
            }
        };

    }

    protected void parseData() {
        if (hmData != null) {
            if (hmData.containsKey("search_entity")) {
                mSearchEntity = (SearchEntity) hmData.get("search_entity");
            } else {
                mSearchEntity = null;
            }
            if (hmData.containsKey("status_layer")) {
                mStatusFilter = (LayerEntity) hmData.get("status_layer");
            } else {
                mStatusFilter = null;
            }
            if (hmData.containsKey("sort_layer")) {
                mSortEntity = (LayerEntity) hmData.get("sort_layer");
            } else {
                mSortEntity = null;
            }
            if (hmData.containsKey("time_layer")) {
                mTimeEntity = (LayerEntity) hmData.get("time_layer");
            } else {
                mTimeEntity = null;
            }
        }
    }

    public void setDelegate(ListOrdersDelegate delegate) {
        mDelegate = delegate;
    }

    public void setData(HashMap<String, Object> hmData) {
        this.hmData = hmData;
    }

    public RecyclerView.OnScrollListener getOnListScroll() {
        return mOnListScroll;
    }

    public View.OnClickListener getOnNextPageClick() {
        return mOnNextPageClick;
    }

    public View.OnClickListener getOnPreviousPageClick() {
        return mOnPreviousPageClick;
    }

    public View.OnClickListener getOnSearchClick() {
        return mOnSearchClick;
    }

    public View.OnClickListener getOnSortClick() {
        return mOnSortClick;
    }

    public View.OnClickListener getOnStatusFilterClick() {
        return mOnStatusFilterClick;
    }

    public View.OnClickListener getOnTimeFilterClick() {
        return mOnTimeFilterClick;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }
}
