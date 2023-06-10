package com.kms.booklet.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kms.booklet.databinding.BookItemBinding;
import com.kms.booklet.model.Book;

public class BookListAdapter extends PagingDataAdapter<Book, BookListAdapter.BookViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int BOOK_ITEM = 1;

    public BookListAdapter(@NonNull DiffUtil.ItemCallback<Book> diffCallback) {
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
        Book currentBook = getItem(position);
        // Check for null
        if (currentBook != null) {
            // Set rating of movie
            holder.bookItemBinding.bookTitle.setText(String.valueOf(currentBook.getTitle()));
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
