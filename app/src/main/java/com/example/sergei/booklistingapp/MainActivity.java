package com.example.sergei.booklistingapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergei.booklistingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        List<Book> books = new ArrayList<Book>();

        for(int i = 0; i < 10; i++){
            Book book = new Book("https://yandex.ru", "Гарри Поттер часть " + i);
            books.add(book);
        }

        BooksListAdapter booksListAdapter = new BooksListAdapter(this, books);

        binding.lv.setAdapter(booksListAdapter);

    }
}
