package com.simicart.saletracking.search.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.search.entity.SearchEntity;
import com.simicart.saletracking.style.PredicateLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Glenn on 12/1/2016.
 */

public class SearchFragment extends AppFragment {

    protected EditText edtQuery;
    protected ImageView imgDelete;
    protected ImageView imgIconSearch;
    protected PredicateLayout plSearch;
    protected SearchEntity mSelectedSearchEntity;
    protected ArrayList<SearchEntity> mListSearches;
    protected ArrayList<TextView> mListSearchTags;
    protected int mFrom;

    public static SearchFragment newInstance(AppData data) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        if(mData != null) {
            mSelectedSearchEntity = (SearchEntity) getValueWithKey("search_entity");
            mFrom = (int) getValueWithKey("from");
        }

        mListSearches = new ArrayList<>();
        if(mFrom == Constants.Search.ORDER) {
            initSearchOrder();
        } else {
            initSearchCustomer();
        }

        edtQuery = (EditText) rootView.findViewById(R.id.et_query);
        edtQuery.setTextColor(AppColor.getInstance().getBlackColor());
        edtQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = edtQuery.getText().toString();
                    hiddenKeyboard(v, true);
                    performSearch(query);
                    return true;
                }
                return false;
            }
        });

        imgDelete = (ImageView) rootView.findViewById(R.id.iv_delete);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableSearchAction(false);
            }
        });

        imgIconSearch = (ImageView) rootView.findViewById(R.id.iv_search);
        imgIconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableSearchAction(true);
            }
        });

        plSearch = (PredicateLayout) rootView.findViewById(R.id.pl_search);

        if(mSelectedSearchEntity != null) {
            enableSearchAction(true);
            edtQuery.setText(mSelectedSearchEntity.getQuery());
        }

        if(mListSearches != null) {
            initSearchTags();
        }

        return rootView;
    }

    protected void initSearchTags() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mListSearchTags = new ArrayList<>();
        for(final SearchEntity searchEntity : mListSearches) {
            View itemView = inflater.inflate(R.layout.view_search_tag, null);
            final TextView tvItem = (TextView) itemView.findViewById(R.id.tv_search_tag);
            tvItem.setText(searchEntity.getLabel());

            LinearLayout llItem = (LinearLayout) itemView.findViewById(R.id.ll_search_tag);

            if(mSelectedSearchEntity != null) {
                if(searchEntity.getKey().equals(mSelectedSearchEntity.getKey())) {
                    setTagChoose(tvItem, true);
                } else {
                    setTagChoose(tvItem, false);
                }
            } else {
                if(mListSearches.indexOf(searchEntity) == 0) {
                    mSelectedSearchEntity = searchEntity;
                    setTagChoose(tvItem, true);
                } else {
                    setTagChoose(tvItem, false);
                }
            }

            mListSearchTags.add(tvItem);
            plSearch.addView(itemView);

            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(TextView textView : mListSearchTags) {
                        setTagChoose(textView, false);
                        setTagChoose(tvItem, true);
                        mSelectedSearchEntity = searchEntity;
                    }
                }
            });

        }

    }

    protected void setTagChoose(TextView textView, boolean choose) {
        if(choose) {
            textView.setBackgroundResource(R.drawable.tag_background_choosed);
            textView.setTextColor(AppColor.getInstance().getWhiteColor());
        } else {
            textView.setBackgroundResource(R.drawable.tag_background);
            textView.setTextColor(AppColor.getInstance().getBlackColor());
        }
    }

    protected void enableSearchAction(boolean isEnable) {
        if (isEnable) {
            RelativeLayout.LayoutParams paramsIcon = new RelativeLayout.LayoutParams(Utils.toPixel(20), Utils.toPixel(20));
            paramsIcon.addRule(RelativeLayout.ALIGN_PARENT_START);
            paramsIcon.addRule(RelativeLayout.CENTER_VERTICAL);
            paramsIcon.rightMargin = Utils.toPixel(5);
            paramsIcon.leftMargin = Utils.toPixel(16);
            imgIconSearch.setLayoutParams(paramsIcon);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.toPixel(30));
            params.addRule(RelativeLayout.END_OF, imgIconSearch.getId());
            params.setMarginEnd(Utils.toPixel(40));
            edtQuery.setLayoutParams(params);
            edtQuery.setVisibility(View.VISIBLE);
            hiddenKeyboard(edtQuery, false);
            imgDelete.setVisibility(View.VISIBLE);

        } else {
            edtQuery.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);

            RelativeLayout.LayoutParams paramsIcon = new RelativeLayout.LayoutParams(Utils.toPixel(20), Utils.toPixel(20));
            paramsIcon.addRule(RelativeLayout.CENTER_IN_PARENT);
            paramsIcon.rightMargin = Utils.toPixel(5);
            paramsIcon.leftMargin = Utils.toPixel(5);
            imgIconSearch.setLayoutParams(paramsIcon);

            edtQuery.clearFocus();
            hiddenKeyboard(edtQuery, true);
        }

    }

    protected void performSearch(String query) {
        HashMap<String, Object> hm = mData.getData();
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mSelectedSearchEntity.setQuery(query);
        hm.put("search_entity", mSelectedSearchEntity);

        if(mFrom == Constants.Search.ORDER) {
            AppManager.getInstance().openListOrders(hm);
        } else {
            AppManager.getInstance().openListCustomers(hm);
        }
    }

    protected void hiddenKeyboard(View v, boolean show) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(!show) {
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
        }
    }

    protected ArrayList<SearchEntity> initSearchOrder() {

        SearchEntity searchCustomerEmail = new SearchEntity();
        searchCustomerEmail.setKey("customer_email");
        searchCustomerEmail.setLabel("Customer Email");
        mListSearches.add(searchCustomerEmail);

        SearchEntity searchOrderID = new SearchEntity();
        searchOrderID.setKey("entity_id");
        searchOrderID.setLabel("Order ID");
        mListSearches.add(searchOrderID);

        SearchEntity searchIncrementID = new SearchEntity();
        searchIncrementID.setKey("increment_id");
        searchIncrementID.setLabel("Increment Id");
        mListSearches.add(searchIncrementID);

        return mListSearches;
    }

    protected ArrayList<SearchEntity> initSearchCustomer() {

        SearchEntity customerID = new SearchEntity();
        customerID.setKey("entity_id");
        customerID.setLabel("Customer ID");
        mListSearches.add(customerID);

        SearchEntity email = new SearchEntity();
        email.setKey("email");
        email.setLabel("Email");
        mListSearches.add(email);

        SearchEntity firstName = new SearchEntity();
        firstName.setKey("firstname");
        firstName.setLabel("First Name");
        mListSearches.add(firstName);

        SearchEntity lastName = new SearchEntity();
        lastName.setKey("lastname");
        lastName.setLabel("Last Name");
        mListSearches.add(lastName);

        SearchEntity websiteID = new SearchEntity();
        websiteID.setKey("website_id");
        websiteID.setLabel("Website (Website Id)");
        mListSearches.add(websiteID);

        SearchEntity groupID = new SearchEntity();
        groupID.setKey("group_id");
        groupID.setLabel("Group (Group Id)");
        mListSearches.add(groupID);

        return mListSearches;
    }

}
