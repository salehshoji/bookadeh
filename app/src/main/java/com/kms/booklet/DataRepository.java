package com.kms.booklet;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;

import com.kms.booklet.api.APIClient;
import com.kms.booklet.db.MainDB;
import com.kms.booklet.db.dao.BookDataDao;
import com.kms.booklet.db.dao.UserDao;
import com.kms.booklet.db.entity.BookData;
import com.kms.booklet.db.entity.User;
import com.kms.booklet.model.BookResponse;
import com.kms.booklet.model.SearchResponse;
import com.kms.booklet.model.SearchResultItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {
    private UserDao mUserDao;
    private BookDataDao mBookDataDao;

    private LiveData<List<User>> mAllUsers;

    private static volatile DataRepository sInstance;

    public static DataRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(application);
                }
            }
        }
        return sInstance;
    }

    DataRepository(Application application) {
        MainDB db = MainDB.getDatabase(application);
        mBookDataDao = db.bookDataDao();
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public User getUserById(String username){return mUserDao.getUserById(username);}

    public void insertUser(User user) {
        mUserDao.insert(user);
    }



    public List<SearchResultItem> searchBooksByName(String bookName){
        return new ArrayList<>();
    }

    public LiveData<BookData> getBookDataByOLID(String OLID){
        LiveData<BookData> bookData = mBookDataDao.getItemByOLID(OLID);
        if(bookData.getValue() == null){
            new GetBookTask().execute(OLID);
        }
        return bookData;
    }

    private class GetBookTask extends AsyncTask<String, Void, Response<BookResponse>>{
        @Override
        protected Response<BookResponse> doInBackground(String... strings) {
            Call<BookResponse> request = APIClient.getAPIInterface()
                    .getBookByOLID(strings[0], "data", "json");

            Log.d("TEST", "doInBackground: " + request.request().url().toString());
            try {
                request.timeout().timeout(10000, java.util.concurrent.TimeUnit.MILLISECONDS);
                Response<BookResponse> res = request.execute();
                return res;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(Response<BookResponse> result) {
            Log.d("TEST", "doInBackground: " + result.body().getData());


            mBookDataDao.insertItems(result.body().getData());
        }
    }

    public void changePassword(String username, String password) {
        mUserDao.changePassword(username, password);
    }

    public void removeAccount(String username) {
        mUserDao.removeAccount(username);
    }
}
