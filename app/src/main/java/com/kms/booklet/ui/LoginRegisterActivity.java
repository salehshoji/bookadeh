package com.kms.booklet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kms.booklet.Config;
import com.kms.booklet.DataRepository;
import com.kms.booklet.R;
import com.kms.booklet.db.entity.User;

public class LoginRegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        EditText repeat_password_text = findViewById(R.id.repeat_password_text);
        EditText nickname = findViewById(R.id.nickname);
        Button changeLoginRegister = findViewById(R.id.login_register);
        CheckBox stay_checkbox = findViewById(R.id.stay_checkbox);
        changeLoginRegister.setOnClickListener(v -> {
            if (nickname.getVisibility() == View.INVISIBLE){
                nickname.setVisibility(View.VISIBLE);
                repeat_password_text.setVisibility(View.VISIBLE);
                changeLoginRegister.setText(R.string.login_mode);
                stay_checkbox.setVisibility(View.INVISIBLE);

            }else{
                nickname.setVisibility(View.INVISIBLE);
                repeat_password_text.setVisibility(View.INVISIBLE);
                changeLoginRegister.setText(R.string.register_mode);
                stay_checkbox.setVisibility(View.VISIBLE);
            }
        });

        Button letsGo = findViewById(R.id.lets_go);
        letsGo.setOnClickListener(v -> {
            if(nickname.getVisibility() == View.VISIBLE){
                registerFunc();
            }else{
                loginFunc();
            }
        });
    }

    private void loginFunc() {
        String username_text = ((EditText)findViewById(R.id.username_text)).getText().toString();
        String password_text = ((EditText)findViewById(R.id.password_text)).getText().toString();
        CheckBox stay_checkbox = findViewById(R.id.stay_checkbox);
        User user = DataRepository.getInstance(getApplication()).getUserById(username_text);
        // todo doshvari!!!
        if(user == null){
            Toast.makeText(LoginRegisterActivity.this, "wrong username", Toast.LENGTH_LONG).show();
            return;
        }
        if (!user.getPassword().equals(password_text)){
            Toast.makeText(LoginRegisterActivity.this, "wrong password", Toast.LENGTH_LONG).show();
            return;
        }
        Config.username = username_text;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void registerFunc() {
        String username_text = ((EditText)findViewById(R.id.username_text)).getText().toString();
        String password_text = ((EditText)findViewById(R.id.password_text)).getText().toString();
        String repeat_password_text = ((EditText)findViewById(R.id.repeat_password_text)).getText().toString();
        String nickname = ((EditText)findViewById(R.id.nickname)).getText().toString();
        // todo if not null doshvari!!!
        if (DataRepository.getInstance(getApplication()).getUserById(username_text) != null){
            Toast.makeText(LoginRegisterActivity.this, "username exists", Toast.LENGTH_LONG).show();
            return;
        }
        if (username_text.length() > 30 || username_text.length() < 6){

            Toast.makeText(LoginRegisterActivity.this, "username length wrong", Toast.LENGTH_LONG).show();
            return;
        }
        if (password_text.length() > 30 || password_text.length() < 6){
            Toast.makeText(LoginRegisterActivity.this, "password length wrong", Toast.LENGTH_LONG).show();
            return;
        }
        if (nickname.length() > 30 || nickname.length() < 6){
            Toast.makeText(LoginRegisterActivity.this, "nickname length wrong", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password_text.equals(repeat_password_text)){
            Toast.makeText(LoginRegisterActivity.this, "password doesn't equal!", Toast.LENGTH_LONG).show();
        }else{
            User user = new User(username_text, password_text, nickname);
            DataRepository.getInstance(getApplication()).insertUser(user);
        }
    }
}
