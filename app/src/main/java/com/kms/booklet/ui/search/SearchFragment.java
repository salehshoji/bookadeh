package com.kms.booklet.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kms.booklet.databinding.FragmentSearchBinding;
import com.kms.booklet.ui.BookListAdapter;
import com.kms.booklet.ui.SearchScreenLoadStateAdaptor;
import com.kms.booklet.util.BookComparator;

import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    SearchViewModel searchViewModel;
    BookListAdapter bookListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel dashboardViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RadioGroup searchType = binding.searchTypeRadioGroup;
        SetupSearchBar();

        bookListAdapter = new BookListAdapter(new BookComparator());
        searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);

        initRecyclerviewAndAdapter();

        Disposable s = searchViewModel.searchResultPagingDataFlowable.subscribe(pagingData -> {
            bookListAdapter.submitData(getLifecycle(), pagingData);
            Log.d("TEST", "onCreateView: " + pagingData);
        });

        searchViewModel.searchQuery.observe(getViewLifecycleOwner(), query -> {
            bookListAdapter.refresh();
        });

        return root;
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //APIClient.getAPIInterface().searchForBook("Harry Potter", 1).subscribeOn(Schedulers.io());
            return null;
        }
    }

    private void initRecyclerviewAndAdapter() {
        binding.searchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResults.setAdapter(bookListAdapter.withLoadStateFooter(
                new SearchScreenLoadStateAdaptor(v -> {
                    bookListAdapter.retry();
                })));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void SetupSearchBar(){
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchbar = binding.searchbar;
        searchbar.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchbar.setOnQueryTextListener((SearchView.OnQueryTextListener) getActivity());
    }
}