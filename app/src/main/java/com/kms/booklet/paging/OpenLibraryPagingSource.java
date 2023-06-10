package com.kms.booklet.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.kms.booklet.api.APIClient;
import com.kms.booklet.exception.NoSearchQueryException;
import com.kms.booklet.model.SearchResultItem;
import com.kms.booklet.model.SearchResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OpenLibraryPagingSource extends RxPagingSource<Integer, SearchResultItem> {
    private String currentSearchQuery;

    public static PagingSource<java.lang.Integer, SearchResultItem> newInstance(String currentSearchQuery) {
        OpenLibraryPagingSource obj = new OpenLibraryPagingSource();
        obj.setCurrentSearchQuery(currentSearchQuery);
        return obj;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, SearchResultItem> pagingState) {
        return null;
        /*int anchorPosition = pagingState.getAnchorPosition();
        Integer key = pagingState.closestPageToPosition(anchorPosition).getPrevKey();
        if (key == null) {
            key = pagingState.closestPageToPosition(anchorPosition).getNextKey();
            return key - 1;
        } else {
            return key + 1;
        }*/
    }

/*    @Nullable
    @Override
    public Single<LoadResult<Integer, Book>> loadSingle(@NonNull LoadParams<Integer> loadParams, @NonNull Continuation<? super LoadResult<Integer, Book>> continuation) {
        try {
            int pageNumber = loadParams.getKey() == null ? 0 : loadParams.getKey();

            Single<SearchResponse> request = APIClient.getAPIInterface().searchForBook("Harry Potter", pageNumber).subscribeOn(Schedulers.io());
            //Response<SearchResponse> response = request.execute();


            Integer prevKey = pageNumber > 0 ? pageNumber - 1 : null;
            Integer nextKey = (response.body().getDocs().size() > 0) ? pageNumber + 1 : null;

            return new LoadResult.Page<>(
                    request.map(SearchResponse::getDocs),
                    prevKey,
                    nextKey
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (HttpException e) {
           throw new RuntimeException(e);
        }
    }*/

    @NonNull
    @Override
    public Single<LoadResult<Integer, SearchResultItem>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try {
            // If page number is already there then init page variable with it otherwise we are loading fist page
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;

            if(currentSearchQuery == null) {
                return Single.just(new LoadResult.Error<>(new NoSearchQueryException()));
            }
            // Send request to server with page number
            return APIClient.getAPIInterface()
                    .searchForBook(currentSearchQuery, page)
                    // Subscribe the result
                    .subscribeOn(Schedulers.io())
                    // Map result top List of movies
                    .map(SearchResponse::getDocs)
                    // Map result to LoadResult Object
                    .map(books -> toLoadResult(books, page))
                    // when error is there return error
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            // Request ran into error return error
            return Single.just(new LoadResult.Error<>(e));
        }
    }

    // Method to map Movies to LoadResult object
    private LoadResult<Integer, SearchResultItem> toLoadResult(List<SearchResultItem> searchResultItems, int page) {
        return new LoadResult.Page<>(searchResultItems, page == 1 ? null : page - 1, searchResultItems.size() > 0 ? page + 1 : null);
    }

    public void setCurrentSearchQuery(String currentSearchQuery) {
        this.currentSearchQuery = currentSearchQuery;
    }
}
