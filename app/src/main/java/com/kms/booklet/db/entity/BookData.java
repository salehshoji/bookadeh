package com.kms.booklet.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@TypeConverters({TestConverter.class})
@Entity(tableName = "books_data")
public class BookData {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "OLId")
    @SerializedName("key")
    @Expose
    private String OLID;

    @NonNull
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

/*    @NonNull
    @ColumnInfo(name = "authors")
    private ArrayList<Author> authors;*/

    @ColumnInfo(name = "cover")
    private String cover_url;

    public BookData(@NonNull String OLID, @NonNull String title, String cover_url) {
        this.OLID = OLID;
        this.title = title;
        this.cover_url = cover_url;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setOLID(@NonNull String OLID) {
        this.OLID = OLID;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    @NonNull
    public String getOLID() {
        return OLID;
    }

    public String getCover_url() {
        return cover_url;
    }

    /* {
    "url": "https://openlibrary.org/books/OL24764937M/The_lord_of_the_rings_J.R.R._Tolkien",
    "key": "/books/OL24764937M",
    "title": "The lord of the rings, J.R.R. Tolkien",
    "authors": [
      {
        "url": "https://openlibrary.org/authors/OL6916799A/Patrick_Gardner",
        "name": "Patrick Gardner"
      }
    ],
    "number_of_pages": 260,
    "pagination": "260 p. ;",
    "by_statement": "[writers, Patrick Gardner, et al.].",
    "identifiers": {
      "isbn_10": [
        "1586637908"
      ],
      "isbn_13": [
        "9781586637903"
      ],
      "oclc": [
        "52939026"
      ],
      "openlibrary": [
        "OL24764937M"
      ]
    },
    "classifications": {
      "lc_classifications": [
        "PR6039.O32 L633695 2002 "
      ],
      "dewey_decimal_class": [
        "823.912"
      ]
    },
    "publishers": [
      {
        "name": "Spark Pub."
      }
    ],
    "publish_places": [
      {
        "name": "New York, NY"
      }
    ],
    "publish_date": "2002",
    "subjects": [
      {
        "name": "Study guides",
        "url": "https://openlibrary.org/subjects/study_guides"
      },
      {
        "name": "Criticism and interpretation",
        "url": "https://openlibrary.org/subjects/criticism_and_interpretation"
      },
      {
        "name": "Middle Earth (Imaginary place)",
        "url": "https://openlibrary.org/subjects/middle_earth_(imaginary_place)"
      }
    ],
    "subject_people": [
      {
        "name": "J. R. R. Tolkien (1892-1973)",
        "url": "https://openlibrary.org/subjects/person:j._r._r._tolkien_(1892-1973)"
      }
    ],
    "notes": "Includes bibliographical references (p. 260).",
    "ebooks": [
      {
        "preview_url": "https://archive.org/details/lordofringsjrrto00gard",
        "availability": "borrow",
        "formats": {},
        "borrow_url": "https://openlibrary.org/books/OL24764937M/The_lord_of_the_rings_J.R.R._Tolkien/borrow",
        "checkedout": false
      }
    ],
    "cover": {
      "small": "https://covers.openlibrary.org/b/id/6818779-S.jpg",
      "medium": "https://covers.openlibrary.org/b/id/6818779-M.jpg",
      "large": "https://covers.openlibrary.org/b/id/6818779-L.jpg"
    }
  }*/
}

class TestConverter{
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
