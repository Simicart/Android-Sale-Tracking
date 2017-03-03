package com.simicart.saletracking.product.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.simicart.saletracking.product.delegate.ListProductsDelegate;
import com.simicart.saletracking.product.request.ListProductsRequest;
import com.simicart.saletracking.search.callback.SearchCallBack;
import com.simicart.saletracking.search.entity.SearchEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsController extends AppController {

    protected ListProductsDelegate mDelegate;

    protected RecyclerView.OnScrollListener mOnListScroll;
    protected View.OnClickListener mOnPreviousPageClick;
    protected View.OnClickListener mOnNextPageClick;
    protected View.OnClickListener mOnSearchClick;
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshPull;
    protected SearchEntity mSearchEntity;
    protected HashMap<String, Object> hmData;

    protected int mCurrentPage = 1;
    protected int mTotalPage;
    protected int mOffset = 0;
    protected int mLimit = AppPreferences.getPaging();
    protected boolean isFirstRequest = true;

    @Override
    public void onStart() {
        parseData();
        requestListProducts(Constants.TypeShowLoading.LOADING);
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.showPage(mCurrentPage, mTotalPage);
        mDelegate.updateView(mCollection);
    }

    protected void requestListProducts(final int showLoading) {
        if (showLoading == Constants.TypeShowLoading.LOADING) {
            mDelegate.showLoading();
        } else if(showLoading == Constants.TypeShowLoading.DIALOG) {
            mDelegate.showDialogLoading();
        }
        ListProductsRequest listProductsRequest = new ListProductsRequest();
        listProductsRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if (showLoading == Constants.TypeShowLoading.LOADING) {
                    mDelegate.dismissLoading();
                } else if(showLoading == Constants.TypeShowLoading.DIALOG) {
                    mDelegate.dismissDialogLoading();
                } else {
                    mDelegate.dismissRefresh();
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
        listProductsRequest.setRequestFailCallback(new RequestFailCallback() {
            @Override
            public void onFail(String message) {
                if (showLoading == Constants.TypeShowLoading.LOADING) {
                    mDelegate.dismissLoading();
                } else if(showLoading == Constants.TypeShowLoading.DIALOG) {
                    mDelegate.dismissDialogLoading();
                } else {
                    mDelegate.dismissRefresh();
                }
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        listProductsRequest.setExtendUrl("simitracking/rest/v2/products");
        listProductsRequest.addParam("limit", String.valueOf(mLimit));
        listProductsRequest.addParam("offset", String.valueOf(mOffset));
        listProductsRequest.addParam("store_id", AppManager.getInstance().getStoreID());
        listProductsRequest.addParam("dir", "desc");
        JSONObject object = new JSONObject();
        try {
            if (mSearchEntity != null) {
                listProductsRequest.addSearchParam(mSearchEntity.getKey(), mSearchEntity.getQuery());
                object.put("search_action", mSearchEntity.getKey());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppManager.getInstance().trackWithMixPanel("list_products_action", object);
        listProductsRequest.request();
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

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if(lastPosition == mDelegate.getPageSize() - 1) {
                    mDelegate.showBottom(false);
                }
            }
        };

        mOnNextPageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage < mTotalPage) {
                    mCurrentPage++;
                    mOffset += mLimit;
                    requestListProducts(Constants.TypeShowLoading.DIALOG);

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "next_page");
                        AppManager.getInstance().trackWithMixPanel("list_products_action", object);
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
                    requestListProducts(Constants.TypeShowLoading.DIALOG);

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "previous_page");
                        AppManager.getInstance().trackWithMixPanel("list_products_action", object);
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
                hmData.put("from", Constants.Search.PRODUCT);
                hmData.put("callback", new SearchCallBack() {
                    @Override
                    public void onSearch(SearchEntity searchEntity) {
                        hmData.remove("from");
                        mSearchEntity = searchEntity;
                        requestListProducts(Constants.TypeShowLoading.DIALOG);
                    }
                });
                AppManager.getInstance().openSearch(hmData);
            }
        };

        mOnRefreshPull = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestListProducts(Constants.TypeShowLoading.REFRESH);
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
        }
    }

    public void setDelegate(ListProductsDelegate delegate) {
        mDelegate = delegate;
    }

    public RecyclerView.OnScrollListener getOnListScroll() {
        return mOnListScroll;
    }

    public void setData(HashMap<String, Object> hmData) {
        this.hmData = hmData;
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

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshPull() {
        return mOnRefreshPull;
    }

}
