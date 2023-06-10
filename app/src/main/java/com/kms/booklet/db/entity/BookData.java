package com.kms.booklet.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "books_data")
public class BookData {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "OLId")
    private String OLID;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "authors")
    private List<Author> authors;

    @ColumnInfo(name = "cover")
    private String cover_url;

    @NonNull
    public String getTitle() {
        return title;
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
