package com.example.sergei.booklistingapp;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new BookDeserializer())
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/") //Базовая часть адреса
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        booksApi = retrofit.create(BooksApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static BooksApi getApi() {
        return booksApi;
    }
}


