package com.example.sergei.booklistingapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sergei on 27.07.2017.
 */

public class App extends Application {

    private static BooksApi booksApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        booksApi = retrofit.create(BooksApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static BooksApi getApi() {
        return booksApi;
    }
}


