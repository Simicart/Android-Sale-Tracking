package com.simicart.saletracking.customer.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.customer.delegate.ListCustomersDelegate;
import com.simicart.saletracking.customer.request.ListCustomersRequest;
import com.simicart.saletracking.search.entity.SearchEntity;

import java.util.HashMap;

/**
 * Created by Glenn on 11/30/2016.
 */

public class ListCustomersController extends AppController {

    protected ListCustomersDelegate mDelegate;
    protected ListCustomersRequest mListCustomersRequest;
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
        parseData();
        requestListCustomers();
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.showPage(mCurrentPage, mTotalPage);
        mDelegate.updateView(mCollection);
    }

    protected void requestListCustomers() {
        if(mListCustomersRequest == null) {
            mListCustomersRequest = new ListCustomersRequest();
            mDelegate.showLoading();
        } else {
            mDelegate.showDialogLoading();
        }
        mListCustomersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
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
        mListCustomersRequest.setRequestFailCallback(new RequestFailCallback() {
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
        mListCustomersRequest.setExtendUrl("customers");
        mListCustomersRequest.addParam("dir", "desc");
        mListCustomersRequest.addParam("limit", String.valueOf(mLimit));
        mListCustomersRequest.addParam("offset", String.valueOf(mOffset));
        if(mSearchEntity != null) {
            mListCustomersRequest.addSearchParam(mSearchEntity.getKey(), mSearchEntity.getQuery());
        }
        mListCustomersRequest.request();
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
                    requestListCustomers();
                }
            }
        };

        mOnPreviousPageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage > 1) {
                    mCurrentPage--;
                    mOffset -= mLimit;
                    requestListCustomers();
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
                hmData.put("from", Constants.Search.CUSTOMER);
                AppManager.getInstance().openSearch(hmData);
            }
        };

    }

    protected void parseData() {

        if(hmData != null) {
            mSearchEntity = (SearchEntity) hmData.get("search_entity");
        }

    }

    public void setDelegate(ListCustomersDelegate delegate) {
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
