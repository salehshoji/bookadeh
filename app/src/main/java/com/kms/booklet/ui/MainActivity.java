package com.kms.booklet.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.kms.booklet.R;
import com.kms.booklet.databinding.ActivityMainBinding;
import com.kms.booklet.model.SearchType;
import com.kms.booklet.ui.search.SearchViewModel;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String SEARCH_TYPE_EXTRA = "com.kms.booklet.SEARCH_TYPE_EXTRA";

    private ActivityMainBinding binding;
    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_account, R.id.navigation_home, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent); // This is needed if launching from search
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchType searchType = SearchType.TITLE;

            Bundle appData = intent.getBundleExtra(SearchManager.APP_DATA);
            if (appData != null && appData.containsKey(SEARCH_TYPE_EXTRA)) {
                searchType = (SearchType) appData.getSerializable(SEARCH_TYPE_EXTRA);
            }

            searchViewModel.setSearchQuery(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent searchIntent = new Intent(this, MainActivity.class);
        searchIntent.setAction(Intent.ACTION_SEARCH);

        searchIntent.putExtra(SearchManager.QUERY, query);

        Bundle appData = new Bundle();
        appData.putSerializable(SEARCH_TYPE_EXTRA, SearchType.AUTHOR);
        searchIntent.putExtra(SearchManager.APP_DATA, appData);

        startActivity(searchIntent);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}