package com.kms.booklet.ui.pay_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kms.booklet.R;
import com.kms.booklet.db.entity.BookData;

public class pay_page extends AppCompatActivity {
    private int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);
    }

    public  void total_price() {
        // read the user id, get his books from database, set the total price text, id = price


    }

    public int calender_error() {
        // check the calender and show error if needed ( error -> return zero )
        return 1;

    }

    public void create_new_history(int price) {
        // read user books, create new history object in database, clear user books (clear cart)

        //books = books of user in cart
        BookData[] books = new BookData[0];
        String s = "";
        for (int i =0 ; i < books.length ; i++)
        {
            s = s + books[i].getTitle();
            s = s + " ";
            s = s + "price : ";
            s = s + Integer.toString(price);
            s = s + "\n";
        }
        // save s to database


        //clear cart of user

    }

    public void fpay_button() {
        int x = calender_error();
        if (x!=0)
        {
            create_new_history(this.price);
            //go to homepage
        }
    }


}