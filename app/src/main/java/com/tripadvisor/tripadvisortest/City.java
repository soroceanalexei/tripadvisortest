package com.tripadvisor.tripadvisortest;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    private String name;
    private String country;
    private String url;
    private String description;

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public City(String name, String country, String url, String description) {
        this.name = name;
        this.country = country;
        this.url = url;
        this.description = description;
    }

    City() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.country);
        dest.writeString(this.url);
        dest.writeString(this.description);
    }


    private City(Parcel in) {
        name = in.readString();
        country = in.readString();
        url = in.readString();
        description = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}