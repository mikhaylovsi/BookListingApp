package com.example.sergei.booklistingapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

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

        for(int i = 0; i < 10; i++){
            Book book = new Book("https://yandex.ru", "Гарри Поттер часть " + i);
            books.add(book);
        }

        return books;

    }
}
