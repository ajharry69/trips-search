package com.hava.trips.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IdRes;

import com.hava.trips.R;

public class FilterParams implements Parcelable {
    public static final FilterParams DEFAULT = new FilterParams.Builder().build();
    private String keyword;
    @IdRes
    private int distance;
    @IdRes
    private int time;
    private boolean includeCancelled;

    private FilterParams(String keyword, int distance, int time, boolean includeCancelled) {
        this.keyword = keyword;
        this.distance = distance;
        this.time = time;
        this.includeCancelled = includeCancelled;
    }

    protected FilterParams(Parcel in) {
        keyword = in.readString();
        distance = in.readInt();
        time = in.readInt();
        includeCancelled = in.readInt() == 1;
    }

    public static final Creator<FilterParams> CREATOR = new Creator<FilterParams>() {
        @Override
        public FilterParams createFromParcel(Parcel in) {
            return new FilterParams(in);
        }

        @Override
        public FilterParams[] newArray(int size) {
            return new FilterParams[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterParams)) return false;

        FilterParams that = (FilterParams) o;

        if (getDistance() != that.getDistance()) return false;
        if (getTime() != that.getTime()) return false;
        if (isIncludeCancelled() != that.isIncludeCancelled()) return false;
        return getKeyword().equals(that.getKeyword());
    }

    @Override
    public int hashCode() {
        int result = getKeyword().hashCode();
        result = 31 * result + getDistance();
        result = 31 * result + getTime();
        result = 31 * result + (isIncludeCancelled() ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(keyword);
        parcel.writeInt(distance);
        parcel.writeInt(time);
        parcel.writeInt(includeCancelled ? 1 : 0);
    }

    public static class Builder {

        private String keyword = "";
        @IdRes
        private int distance = R.id.distance_any;
        @IdRes
        private int time = R.id.time_any;
        private boolean includeCancelled = false;

        public Builder setKeyword(String keyword) {
            this.keyword = keyword == null ? "" : keyword;
            return this;
        }

        public Builder setDistance(@IdRes int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setTime(@IdRes int time) {
            this.time = time;
            return this;
        }

        public Builder setIncludeCancelled(boolean includeCancelled) {
            this.includeCancelled = includeCancelled;
            return this;
        }

        public FilterParams build() {
            return new FilterParams(keyword, distance, time, includeCancelled);
        }

    }

    public String getKeyword() {
        return keyword;
    }

    @IdRes
    public int getDistance() {
        return distance;
    }

    @IdRes
    public int getTime() {
        return time;
    }

    public boolean isIncludeCancelled() {
        return includeCancelled;
    }
}
