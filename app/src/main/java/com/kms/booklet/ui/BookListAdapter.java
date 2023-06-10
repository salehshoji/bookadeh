package com.kms.booklet.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kms.booklet.databinding.BookItemBinding;
import com.kms.booklet.model.SearchResultItem;

public class BookListAdapter extends PagingDataAdapter<SearchResultItem, BookListAdapter.BookViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int BOOK_ITEM = 1;

    public BookListAdapter(@NonNull DiffUtil.ItemCallback<SearchResultItem> diffCallback) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new BookViewHolder(BookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Get current movie
        SearchResultItem currentSearchResultItem = getItem(position);
        // Check for null
        if (currentSearchResultItem != null) {
            // Set rating of movie
            holder.bookItemBinding.bookTitle.setText(String.valueOf(currentSearchResultItem.getTitle()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? BOOK_ITEM : LOADING_ITEM;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        BookItemBinding bookItemBinding;

        public BookViewHolder(@NonNull BookItemBinding bookItemBinding) {
            super(bookItemBinding.getRoot());
            this.bookItemBinding = bookItemBinding;
        }
    }
}
