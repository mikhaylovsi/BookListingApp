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

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

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

                    binding.pb.setVisibility(View.VISIBLE);
                    App.getApi().getData(search, 15)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<List<Book>>() {
                                @Override
                                public void call(List<Book> books) {
                                    booksListAdapter.clear();
                                    booksListAdapter.addAll(books);
                                    booksListAdapter.notifyDataSetChanged();
                                    binding.pb.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

    }

}
