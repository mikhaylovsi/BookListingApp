package com.example.sergei.booklistingapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergei on 31.07.2017.
 */

public class BookDeserializer implements JsonDeserializer<List<Book>> {
    @Override
    public List<Book> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        List<Book> books = new ArrayList<>();

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();
        for(JsonElement jsonElement : jsonArray){

            JsonObject jsonBook = jsonElement.getAsJsonObject();
            JsonObject volume = jsonBook.get("volumeInfo").getAsJsonObject();
            String title = volume.get("title").getAsString();
            JsonObject imageLinks = volume.get("imageLinks").getAsJsonObject();
            String url = imageLinks.get("smallThumbnail").getAsString();

            Book book = new Book(url, title);
            books.add(book);

        }

        return books;

    }
}
