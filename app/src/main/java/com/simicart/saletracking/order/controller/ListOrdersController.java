package com.simicart.saletracking.order.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.order.delegate.ListOrdersDelegate;
import com.simicart.saletracking.order.request.ListOrdersRequest;
import com.simicart.saletracking.search.entity.SearchEntity;
import com.simicart.saletracking.store.entity.StoreViewEntity;
import com.simicart.saletracking.store.request.GetStoreRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 11/28/2016.
 */

public class ListOrdersController extends AppController {

    protected ListOrdersDelegate mDelegate;
    protected ListOrdersRequest mListOrdersRequest;
    protected RecyclerView.OnScrollListener mOnListScroll;
    protected View.OnClickListener mOnPreviousPageClick;
    protected View.OnClickListener mOnNextPageClick;
    protected View.OnClickListener mOnSearchClick;
    protected SearchEntity mSearchEntity;
    protected HashMap<String, Object> hmData;

    protected int mCurrentPage = 1;
    protected int mTotalPage;
    protected int mOffset = 0;
    protected int mLimit = 30;
    protected boolean isFirstRequest = true;

    @Override
    public void onStart() {
        if(AppManager.getInstance().getMenuTopController().getListStoreViews() == null) {
            requestListStoreViews();
        }
        parseData();
        requestListOrders();
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.showPage(mCurrentPage, mTotalPage);
        mDelegate.updateView(mCollection);
    }

    protected void requestListStoreViews() {
        GetStoreRequest getStoreRequest = new GetStoreRequest();
        getStoreRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if(collection != null) {
                    if(collection.containKey("stores")) {
                        ArrayList<StoreViewEntity> listStores = (ArrayList<StoreViewEntity>) collection.getDataWithKey("stores");
                        AppManager.getInstance().getMenuTopController().setListStoreViews(listStores);
                    }
                }
            }
        });
        getStoreRequest.setExtendUrl("stores");
        getStoreRequest.request();
    }

    protected void requestListOrders() {
        if(mListOrdersRequest == null) {
            mListOrdersRequest = new ListOrdersRequest();
            mDelegate.showLoading();
        } else {
            mDelegate.showDialogLoading();
        }
        mListOrdersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
            @Override
            public void onSuccess(AppCollection collection) {
                if(isFirstRequest) {
                    mDelegate.dismissLoading();
                    isFirstRequest = false;
                } else {
                    mDelegate.dismissDialogLoading();
                }
                mCollection = collection;
                if(collection != null) {
                    if (collection.containKey("total")) {
                        int totalResult = (int) collection.getDataWithKey("total");
                        mTotalPage = totalResult / 30;
                        if (totalResult % 30 != 0 || mTotalPage == 0) {
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
                if(isFirstRequest) {
                    mDelegate.dismissLoading();
                } else {
                    mDelegate.dismissDialogLoading();
                }
                mDelegate.updateView(mCollection);
                AppNotify.getInstance().showError(message);
            }
        });
        mListOrdersRequest.setExtendUrl("orders");
        mListOrdersRequest.addParam("dir", "desc");
        mListOrdersRequest.addParam("limit", String.valueOf(mLimit));
        mListOrdersRequest.addParam("offset", String.valueOf(mOffset));
        mListOrdersRequest.addParam("store_id", AppManager.getInstance().getStoreID());
        if(mSearchEntity != null) {
            mListOrdersRequest.addSearchParam(mSearchEntity.getKey(), mSearchEntity.getQuery());
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
                if(mCurrentPage < mTotalPage) {
                    mCurrentPage++;
                    mOffset+=mLimit;
                    requestListOrders();
                }
            }
        };

        mOnPreviousPageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentPage > 1) {
                    mCurrentPage--;
                    mOffset-=mLimit;
                    requestListOrders();
                }
            }
        };

        mOnSearchClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hmData == null) {
                    hmData = new HashMap<>();
                }
                hmData.put("search_entity", mSearchEntity);
                hmData.put("from", Constants.Search.ORDER);
                AppManager.getInstance().openSearch(hmData);
            }
        };

    }

    protected void parseData() {

        if(hmData != null) {
            mSearchEntity = (SearchEntity) hmData.get("search_entity");
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

}
