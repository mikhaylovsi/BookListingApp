package com.example.sergei.booklistingapp;

import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergei.booklistingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    ActivityMainBinding binding;
    List<Book> books = new ArrayList<Book>();
    BooksListAdapter booksListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        booksListAdapter = new BooksListAdapter(this, books);
        binding.lv.setAdapter(booksListAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books){
        booksListAdapter.clear();
        booksListAdapter.addAll(books);
        booksListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }
}
