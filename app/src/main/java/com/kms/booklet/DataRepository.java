package com.kms.booklet;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.paging.PagingData;

import com.kms.booklet.api.APIClient;
import com.kms.booklet.db.MainDB;
import com.kms.booklet.db.dao.UserDao;
import com.kms.booklet.db.entity.User;
import com.kms.booklet.model.Book;
import com.kms.booklet.model.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {
    private UserDao mUserDao;
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

    public List<Book> searchBooksByName(String bookName){
        return new ArrayList<>();
    }
}
