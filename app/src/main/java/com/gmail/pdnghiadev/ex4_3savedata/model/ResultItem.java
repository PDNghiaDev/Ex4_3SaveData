package com.gmail.pdnghiadev.ex4_3savedata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by PDNghiaDev on 12/1/2015.
 */
public class ResultItem implements Parcelable, Serializable {
    private Date date;
    private int countTap;

    public ResultItem(Date date, int countTap) {
        this.date = date;
        this.countTap = countTap;
    }


    protected ResultItem(Parcel in) {
        countTap = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<ResultItem> CREATOR = new Creator<ResultItem>() {
        @Override
        public ResultItem createFromParcel(Parcel in) {
            return new ResultItem(in);
        }

        @Override
        public ResultItem[] newArray(int size) {
            return new ResultItem[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCountTap() {
        return countTap;
    }

    public void setCountTap(int countTap) {
        this.countTap = countTap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(countTap);
        dest.writeLong(this.date.getTime());
    }
}
