package com.kms.booklet.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kms.booklet.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * from users ORDER BY username ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * from users where username == :name")
    User getUserById(String name);
}
