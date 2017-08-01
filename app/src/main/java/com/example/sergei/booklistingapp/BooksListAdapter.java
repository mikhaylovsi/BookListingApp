package com.example.sergei.booklistingapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Sergei on 27.07.2017.
 */

public class BooksListAdapter extends ArrayAdapter<Book> {

    private List<Book> books;
    private Context context;
    ViewHolder viewHolder;

    public BooksListAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, R.layout.item_listview, books);

        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(context);
            convertView = li.inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivBookImage = (ImageView) convertView.findViewById(R.id.bookImage);
            viewHolder.tvBookName = (TextView) convertView.findViewById(R.id.bookName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Picasso.with(context)
                .load(getItem(position).getUri())
                .placeholder(R.drawable.book_icon)
                .error(R.drawable.book_icon)
                .into(viewHolder.ivBookImage);

        viewHolder.tvBookName.setText(getItem(position).getName());

        return convertView;
    }

    private class ViewHolder {
        ImageView ivBookImage;
        TextView tvBookName;
    }

}
