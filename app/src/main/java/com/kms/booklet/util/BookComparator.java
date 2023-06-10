package com.kms.booklet.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.kms.booklet.model.Book;

public class BookComparator extends DiffUtil.ItemCallback<Book> {
    @Override
    public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
        return oldItem.getKey().equals(newItem.getKey());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
        return oldItem.getKey().equals(newItem.getKey());
    }
}
