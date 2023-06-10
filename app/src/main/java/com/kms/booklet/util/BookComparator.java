package com.kms.booklet.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.kms.booklet.model.SearchResultItem;

public class BookComparator extends DiffUtil.ItemCallback<SearchResultItem> {
    @Override
    public boolean areItemsTheSame(@NonNull SearchResultItem oldItem, @NonNull SearchResultItem newItem) {
        return oldItem.getKey().equals(newItem.getKey());
    }

    @Override
    public boolean areContentsTheSame(@NonNull SearchResultItem oldItem, @NonNull SearchResultItem newItem) {
        return oldItem.getKey().equals(newItem.getKey());
    }
}
