package com.kms.booklet.db;

import com.kms.booklet.model.SearchResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryService {
    @GET("search.json")
    Single<SearchResponse> searchForBook(@Query("title") String title, @Query("page") int page);
}
