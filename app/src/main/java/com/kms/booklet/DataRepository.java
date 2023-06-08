package com.kms.booklet;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kms.booklet.db.MainDB;
import com.kms.booklet.db.dao.UserDao;
import com.kms.booklet.db.entity.User;

import java.util.List;

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

    public void insertUser(User user) {
        mUserDao.insert(user);
    }
}
