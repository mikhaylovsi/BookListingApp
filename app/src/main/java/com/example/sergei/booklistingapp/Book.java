package com.example.sergei.booklistingapp;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;

/**
 * Created by Sergei on 27.07.2017.
 */

class Book implements Parcelable {

    private String uri = "";

    private String name = "";

    Book(String uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {

            return new Gson().fromJson(in.readString(), Book.class);

        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    String getName() {
        return name;
    }

    String getUri() {
        return uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(new Gson().toJson(this));
    }
}
