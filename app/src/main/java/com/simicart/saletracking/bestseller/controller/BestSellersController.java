package com.simicart.saletracking.bestseller.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.manager.AppNotify;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.base.request.RequestFailCallback;
import com.simicart.saletracking.base.request.RequestSuccessCallback;
import com.simicart.saletracking.bestseller.delegate.BestSellersDelegate;
import com.simicart.saletracking.bestseller.request.BestSellersRequest;
import com.simicart.saletracking.common.AppPreferences;
import com.simicart.saletracking.common.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellersController extends AppController {

    protected BestSellersDelegate mDelegate;
    protected RecyclerView.OnScrollListener mOnListScroll;
    protected View.OnClickListener mOnPreviousPageClick;
    protected View.OnClickListener mOnNextPageClick;
    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshPull;

    protected int mCurrentPage = 1;
    protected int mTotalPage;
    protected int mOffset = 0;
    protected int mLimit = AppPreferences.getPaging();
    protected boolean isFirstRequest = true;

    @Override
    public void onStart() {
        requestBestSellers(Constants.TypeShowLoading.LOADING);
        initListener();
    }

    @Override
    public void onResume() {
        mDelegate.showPage(mCurrentPage, mTotalPage);
        mDelegate.updateView(mCollection);
    }

    protected void requestBestSellers(final int showLoading) {
        if (showLoading == Constants.TypeShowLoading.LOADING) {
            mDelegate.showLoading();
        } else if(showLoading == Constants.TypeShowLoading.DIALOG) {
            mDelegate.showDialogLoading();
        }
        BestSellersRequest bestSellersRequest = new BestSellersRequest();
        bestSellersRequest.setRequestSuccessCallback(new RequestSuccessCallback() {
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
        bestSellersRequest.setRequestFailCallback(new RequestFailCallback() {
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
        bestSellersRequest.setExtendUrl("simitracking/rest/v2/bestsellers");
        bestSellersRequest.addParam("dir", "desc");
        bestSellersRequest.addParam("limit", String.valueOf(mLimit));
        bestSellersRequest.addParam("offset", String.valueOf(mOffset));
        bestSellersRequest.request();
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
                    requestBestSellers(Constants.TypeShowLoading.DIALOG);

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "next_page");
                        AppManager.getInstance().trackWithMixPanel("best_bellers_action", object);
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
                    requestBestSellers(Constants.TypeShowLoading.DIALOG);

                    // Tracking with MixPanel
                    try {
                        JSONObject object = new JSONObject();
                        object.put("action", "previous_page");
                        AppManager.getInstance().trackWithMixPanel("best_bellers_action", object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        mOnRefreshPull = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestBestSellers(Constants.TypeShowLoading.REFRESH);
            }
        };
    }

    public void setDelegate(BestSellersDelegate delegate) {
        mDelegate = delegate;
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

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshPull() {
        return mOnRefreshPull;
    }
}
