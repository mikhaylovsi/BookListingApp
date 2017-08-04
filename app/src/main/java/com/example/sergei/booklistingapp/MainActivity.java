package com.example.sergei.booklistingapp;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.sergei.booklistingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Book> books = new ArrayList<Book>();
    BooksListAdapter booksListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if(savedInstanceState.getParcelableArrayList("books") != null) {
                books = savedInstanceState.getParcelableArrayList("books");
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = binding.editText.getText().toString();
                if (!TextUtils.isEmpty(search)) {

                    binding.pb.setVisibility(View.VISIBLE);
                    App.getApi().getData(search, 15)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<ArrayList<Book>>() {
                                @Override
                                public void call(ArrayList<Book> books) {
                                    MainActivity.this.books = books;
                                    updateList();
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {

        booksListAdapter = (BooksListAdapter) binding.lv.getAdapter();
        if (booksListAdapter == null) {
            booksListAdapter = new BooksListAdapter(this, new ArrayList<Book>());
            binding.lv.setAdapter(booksListAdapter);
        }

        booksListAdapter.clear();
        booksListAdapter.addAll(books);
        booksListAdapter.notifyDataSetChanged();
        binding.pb.setVisibility(View.GONE);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (books != null) {
            outState.putParcelableArrayList("books", books);
        }
        super.onSaveInstanceState(outState);
    }

}
