package com.kms.booklet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kms.booklet.R;
import com.kms.booklet.model.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private final LayoutInflater mInflater;
    private List<Book> mBooks;

    BookListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (mBooks != null) {
            Book current = mBooks.get(position);
            holder.setData(current);
        }
    }

    void setBooks(List<Book> books){
        mBooks = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBooks != null)
            return mBooks.size();
        else return 0;
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        private BookViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }

        public void setData(Book bookData) {
            nameTextView.setText(bookData.getName());
        }
    }
}
