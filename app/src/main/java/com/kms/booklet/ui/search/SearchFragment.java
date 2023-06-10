package com.kms.booklet.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kms.booklet.R;
import com.kms.booklet.databinding.FragmentSearchBinding;
import com.kms.booklet.exception.NoSearchQueryException;
import com.kms.booklet.ui.BookListAdapter;
import com.kms.booklet.ui.SearchScreenLoadStateAdaptor;
import com.kms.booklet.util.BookComparator;

import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    BookListAdapter bookListAdapter;

    SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RadioGroup searchType = binding.searchTypeRadioGroup;
        SetupSearchBar();

        bookListAdapter = new BookListAdapter(new BookComparator());

        initRecyclerviewAndAdapter();

        searchViewModel.searchQuery.observe(getViewLifecycleOwner(), query -> bookListAdapter.refresh());

        binding.searchLoadingRetryButton.setOnClickListener(v -> bookListAdapter.retry());

        Disposable s = searchViewModel.searchResultPagingDataFlowable.subscribe(pagingData -> {
            bookListAdapter.submitData(getLifecycle(), pagingData);
        });

        searchViewModel.loadingState.observe(getViewLifecycleOwner(), loadState -> {
            if (loadState instanceof LoadState.Error) {
                Throwable error = ((LoadState.Error) loadState).getError();
                if (error instanceof NoSearchQueryException) {
                    binding.searchResults.setVisibility(View.GONE);
                    binding.searchLoading.setVisibility(View.GONE);
                    return;
                }
                binding.searchLoadingErrorMsg.setText(R.string.error_default_msg);
            }
            binding.searchResults.setVisibility(loadState instanceof LoadState.NotLoading
                    ? View.VISIBLE : View.GONE);
            binding.searchLoading.setVisibility(loadState instanceof LoadState.NotLoading
                    ? View.GONE : View.VISIBLE);
            binding.searchLoadingProgressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            binding.searchLoadingRetryButton.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            binding.searchLoadingErrorMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        });

        return root;
    }

    private void initRecyclerviewAndAdapter() {
        binding.searchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.searchResults.setAdapter(bookListAdapter.withLoadStateFooter(
                new SearchScreenLoadStateAdaptor(v -> bookListAdapter.retry())));

        bookListAdapter.addLoadStateListener(loadStates -> {
            searchViewModel.setLoadingState(loadStates.getRefresh());
            return null;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void SetupSearchBar() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchbar = binding.searchbar;
        searchbar.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchbar.setOnQueryTextListener((SearchView.OnQueryTextListener) getActivity());
    }
}