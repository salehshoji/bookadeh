package com.kms.booklet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kms.booklet.db.entity.BookData;

public class BookResponse {
    @Expose
    @SerializedName("OLID:OL24764937M")
    public BookData data;

    public void setData(BookData data) {
        this.data = data;
    }

    public BookData getData() {
        return data;
    }
}
