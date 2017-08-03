package com.example.sergei.booklistingapp;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface BooksApi {

    @GET("books/v1/volumes")
    Observable<List<Book>> getData(@Query("q") String resourceName, @Query("maxResults") int count);


}
