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

    String search;

    public BookLoader(Context context, String search) {
        super(context);
        this.search = search;
    }

    @Override
    public List<Book> loadInBackground() {

        List<Book> books = new ArrayList<Book>();

        try {
            Response<List<Book>> response = App.getApi().getData(search, 15).execute();
            books.addAll(response.body());
            return books;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
