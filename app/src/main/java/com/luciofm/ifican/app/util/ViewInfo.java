package com.luciofm.ifican.app.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class ViewInfo implements Parcelable {
    public static final String VIEW_INFO = "VIEW_INFO";
    public int left;
    public int top;
    public int width;
    public int height;
    public int orientation;
    public int position;

    public ViewInfo(View v, int position) {
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);
        left = screenLocation[0];
        top = screenLocation[1];
        width = v.getWidth();
        height = v.getHeight();
        orientation = v.getResources().getConfiguration().orientation;
        this.position = position;
    }

    @Override
    public String toString() {
        return "ViewInfo{" +
                "left=" + left +
                ", top=" + top +
                ", width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                ", position=" + position +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.left);
        dest.writeInt(this.top);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.orientation);
        dest.writeInt(this.position);
    }

    public ViewInfo(Parcel in) {
        this.left = in.readInt();
        this.top = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.orientation = in.readInt();
        this.position = in.readInt();
    }

    public static Creator<ViewInfo> CREATOR = new Creator<ViewInfo>() {
        public ViewInfo createFromParcel(Parcel source) {
            return new ViewInfo(source);
        }

        public ViewInfo[] newArray(int size) {
            return new ViewInfo[size];
        }
    };
}
