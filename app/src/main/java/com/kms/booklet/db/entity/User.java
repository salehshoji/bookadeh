package com.kms.booklet.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    @ColumnInfo(name = "nickName")
    private String nickName;

    public User(@NonNull String username, @NonNull String password, @NonNull String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getNickName() {
        return nickName;
    }
}
