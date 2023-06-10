package com.kms.booklet.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.LoadState;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.kms.booklet.model.Book;
import com.kms.booklet.paging.OpenLibraryPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class SearchViewModel extends ViewModel {
    public Flowable<PagingData<Book>> searchResultPagingDataFlowable;

    MutableLiveData<String> searchQuery = new MutableLiveData<>();

    MutableLiveData<LoadState> loadingState = new MutableLiveData<>();


    public SearchViewModel() {
        init();
    }

    // Init ViewModel Data
    private void init() {
        Pager<Integer, Book> pager = new Pager<>(
                // Create new paging config
                new PagingConfig(20, //  Count of items in one page
                        20, //  Number of items to prefetch
                        true, // Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> OpenLibraryPagingSource.newInstance(searchQuery.getValue())); // set paging source

        searchResultPagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(searchResultPagingDataFlowable, coroutineScope);
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void setLoadingState(LoadState state) {
        Log.d("TEST", "setLoadingState: " + state);
        loadingState.setValue(state);
    }

/*    public class MyAsyncTask extends AsyncTask<String, Void, Response<SearchResponse>> {

        @Override
        protected Response<SearchResponse> doInBackground(String... strings) {
            Call<SearchResponse> request = APIClient.getAPIInterface().searchForBook(strings[0], 5);
            Log.d("Test", "test:" + request.request().url());
            try {
                request.timeout().timeout(10000, java.util.concurrent.TimeUnit.MILLISECONDS);
                Response<SearchResponse> res = request.execute();
                Log.d("Test", "test:" + res.body().getDocs().get(0).getTitle());
                return res;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(Response<SearchResponse> result) {
            searchResultPagingData = PagingData.from(result.body().getDocs());
            bookListAdapter.submitData(getLifecycle(), PagingData.from(result.body().getDocs()));
        }
    }*/
}