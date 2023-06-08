package com.kms.booklet.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kms.booklet.DataRepository;
import com.kms.booklet.db.entity.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private DataRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = DataRepository.getInstance(application);
        mAllUsers = mRepository.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public void insert(User user) { mRepository.insertUser(user); }
}