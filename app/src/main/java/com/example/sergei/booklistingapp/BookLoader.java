package com.example.sergei.booklistingapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Sergei on 27.07.2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    public BookLoader(Context context) {
        super(context);
    }

    @Override
    public List<Book> loadInBackground() {

        List<Book> books = new ArrayList<Book>();

        try {
            Response<List<Book>> response = App.getApi().getData("android", 2).execute();
            books.addAll(response.body());
            return books;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
