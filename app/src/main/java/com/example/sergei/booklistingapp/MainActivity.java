package com.example.sergei.booklistingapp;

import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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

        binding.buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = binding.editText.getText().toString();
                if (!TextUtils.isEmpty(search)) {

                    Bundle args = new Bundle();
                    args.putString("search", search);


                    Loader loader = getSupportLoaderManager().getLoader(1);
                    if (loader == null) {
                        getSupportLoaderManager().initLoader(1, args, MainActivity.this).forceLoad();
                    } else {
                        getSupportLoaderManager().restartLoader(1, args, MainActivity.this);
                    }

                }
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, args.getString("search"));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        booksListAdapter.clear();
        booksListAdapter.addAll(books);
        booksListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }
}
