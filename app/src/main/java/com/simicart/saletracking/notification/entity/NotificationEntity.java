package com.simicart.saletracking.notification.entity;

import com.simicart.saletracking.base.entity.AppEntity;

import java.io.Serializable;

/**
 * Created by Glenn on 12/15/2016.
 */

public class NotificationEntity extends AppEntity implements Serializable {

    protected String mTitle;
    protected String mContent;
    protected String mOrderID;

    private final String NOTICE_TITLE = "notice_title";
    private final String NOTICE_CONTENT = "notice_content";
    private final String ORDER_ID = "order_id";

    @Override
    public void parse() {

        mTitle = getString(NOTICE_TITLE);

        mContent = getString(NOTICE_CONTENT);

        mOrderID = getString(ORDER_ID);

    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOrderID() {
        return mOrderID;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }
}
