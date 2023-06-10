package com.kms.booklet.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kms.booklet.db.dao.BookDataDao;
import com.kms.booklet.db.dao.UserDao;
import com.kms.booklet.db.entity.BookData;
import com.kms.booklet.db.entity.User;

@Database(entities = {User.class, BookData.class}, version = 1, exportSchema = false)
public abstract class MainDB extends RoomDatabase {

    private static volatile MainDB INSTANCE;

    public static MainDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MainDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MainDB.class, "main_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract BookDataDao bookDataDao();
}