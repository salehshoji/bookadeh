package com.kms.booklet.db;

import com.google.gson.Gson;
import com.kms.booklet.db.entity.BookData;
import com.kms.booklet.model.BookResponse;
import com.kms.booklet.model.SearchResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryService {
    @GET("search.json")
    Single<SearchResponse> searchForBook(@Query("title") String title, @Query("page") int page);

    @GET("api/books")
    Call<BookResponse> getBookByOLID(@Query("bibkeys") String bibkeys, @Query("jscmd") String jscmd, @Query("format") String format);
}
