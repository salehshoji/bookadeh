package com.kms.booklet.db;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.kms.booklet.model.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class BookApi implements LoaderManager.LoaderCallbacks<List<Book>> {

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        return new SearchForBook(null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

    }

    // make http request to api async
    public static class SearchForBook extends AsyncTaskLoader<List<Book>> {
        // Base URL for Books API.
        private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
        // Parameter for the search string.
        private static final String QUERY_PARAM = "q";
        // Parameter that limits search results.
        private static final String MAX_RESULTS = "maxResults";
        // Parameter to filter by print type.
        private static final String PRINT_TYPE = "printType";

        private final String mQueryString;

        public SearchForBook(@NonNull Context context, String queryString) {
            super(context);
            mQueryString = queryString;
        }

        @Nullable
        @Override
        public List<Book> loadInBackground() {
            List<Book> data = new ArrayList<Book>();

            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;
            String bookJSONString = null;

            try {
                Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, mQueryString)
                        .appendQueryParameter(MAX_RESULTS, "10")
                        .appendQueryParameter(PRINT_TYPE, "books")
                        .build();
                URL requestURL = new URL(builtURI.toString());

                urlConnection = (HttpsURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Get the InputStream.
                InputStream inputStream = urlConnection.getInputStream();

                // Create a buffered reader from that input stream.
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // Use a StringBuilder to hold the incoming response.
                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    // Since it's JSON, adding a newline isn't necessary (it won't
                    // affect parsing) but it does make debugging a *lot* easier
                    // if you print out the completed buffer for debugging.
                    builder.append("\n");
                }
                if (builder.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                bookJSONString = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Close the streams.
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    // Close our reader stream.
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
    }

}
