package com.simicart.saletracking.base.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Glenn on 11/29/2016.
 */

public class AppData implements Parcelable {

    public static final Creator<AppData> CREATOR = new Creator<AppData>() {
        @Override
        public AppData createFromParcel(Parcel in) {
            return new AppData(in);
        }

        @Override
        public AppData[] newArray(int size) {
            return new AppData[size];
        }
    };
    protected HashMap<String, Object> mData;

    public AppData(HashMap<String, Object> data) {
        mData = data;
    }

    protected AppData(Parcel in) {
        try {
            mData = in.readHashMap(HashMap.class.getClassLoader());
        } catch (Exception e) {

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {

            if (null == mData) {
                return;
            }

            dest.writeValue(mData);
        } catch (Exception e) {
            Log.e("AppData ", " write exception " + e.getMessage());
        }
    }

    public HashMap<String, Object> getData() {
        return mData;
    }

}
