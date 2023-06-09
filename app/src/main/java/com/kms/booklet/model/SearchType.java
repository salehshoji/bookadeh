package com.kms.booklet.model;

public enum SearchType {
    TITLE("Title"),
    AUTHOR("Author"),
    PUBLISHER("Publisher");

    private final String value;

    SearchType(String value) {
        this.value = value;
    }
}
