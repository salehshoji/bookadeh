package com.kms.booklet.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kms.booklet.db.entity.BookData;

@Dao
public interface BookDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(BookData bookData);

    @Query("SELECT * FROM books_data WHERE olid = :olid LIMIT 1")
    BookData getItemByOLID(String olid);
}
