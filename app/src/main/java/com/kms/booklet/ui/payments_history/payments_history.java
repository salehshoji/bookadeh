package com.kms.booklet.ui.payments_history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kms.booklet.R;

public class payments_history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_history);
    }

    public void show() {
        //check database for payment history of user, add them to list view, recycle id == histories
    }
}