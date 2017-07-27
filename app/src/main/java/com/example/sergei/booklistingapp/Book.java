package com.example.sergei.booklistingapp;

/**
 * Created by Sergei on 27.07.2017.
 */

public class Book {

    private String uri;
    private String name;

    Book(String uri, String name){
        this.uri = uri;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
