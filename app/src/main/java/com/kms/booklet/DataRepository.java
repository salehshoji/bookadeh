package com.kms.booklet;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;

import com.kms.booklet.api.APIClient;
import com.kms.booklet.db.MainDB;
import com.kms.booklet.db.dao.BookDataDao;
import com.kms.booklet.db.dao.UserDao;
import com.kms.booklet.db.entity.BookData;
import com.kms.booklet.db.entity.User;
import com.kms.booklet.model.SearchResponse;
import com.kms.booklet.model.SearchResultItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

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
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insertUser(User user) {
        mUserDao.insert(user);
    }



    public List<SearchResultItem> searchBooksByName(String bookName){
        return new ArrayList<>();
    }

    public BookData getBookDataByOLID(String OLID){
        BookData bookData = mBookDataDao.getItemByOLID(OLID);
        if(bookData == null){
            APIClient.getAPIInterface()
                    .getBookByOLID(OLID, "data", "json")
                    .subscribeOn(Schedulers.io());
        }
        return mBookDataDao.getItemByOLID(OLID);
    }
}
