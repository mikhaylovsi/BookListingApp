package com.example.sergei.booklistingapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.sergei.booklistingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Book> books = new ArrayList<Book>();
    BooksListAdapter booksListAdapter;

    Observable<ArrayList<Book>> booksObservable;
    Subscription booksSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList("books") != null) {
                books = savedInstanceState.getParcelableArrayList("books");
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        booksObservable = getSavedBooksObservable();

        binding.buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = binding.editText.getText().toString();
                if (!TextUtils.isEmpty(search)) {

                    binding.pb.setVisibility(View.VISIBLE);
                    booksObservable = App.getApi().getData(search, 15)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .cache();

                    booksSubscription = booksObservable
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

        if (booksObservable != null) {
            booksSubscription = booksObservable
                    .subscribe(new Action1<ArrayList<Book>>() {
                        @Override
                        public void call(ArrayList<Book> books) {
                            MainActivity.this.books = books;
                            updateList();
                        }
                    });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(booksSubscription != null) {
            booksSubscription.unsubscribe();
        }
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

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        if (booksObservable != null) {
            return booksObservable;
        }

        return null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Observable<ArrayList<Book>> getSavedBooksObservable() {

        if (getLastCustomNonConfigurationInstance() != null) {
            return (Observable<ArrayList<Book>>) getLastCustomNonConfigurationInstance();
        }

        return null;
    }
}
