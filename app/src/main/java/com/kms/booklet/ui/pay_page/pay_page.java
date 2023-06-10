package com.kms.booklet.ui.pay_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kms.booklet.R;

public class pay_page extends AppCompatActivity {

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
        return 0;

    }

    public void create_new_history() {
        // read user books, create new history object in database, clear user books (clear cart)
    }

    public void fpay_button() {
        int x = calender_error();
        if (x!=0)
        {
            create_new_history();
            //go to pay_page
        }
    }


}