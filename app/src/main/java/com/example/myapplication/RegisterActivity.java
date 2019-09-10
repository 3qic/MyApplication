package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private EditText emailAddress;
    private EditText password;
    private EditText confirmPassword;
    private Button registerProfileButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;


        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);

            setContentView(R.layout.activity_register);
            findViews();


        }
    }

    private void findViews() {
        emailAddress = findViewById(R.id.register_user_email);
        password = findViewById(R.id.register_user_password);
        confirmPassword = findViewById(R.id.register_user_password_confirm);
        registerProfileButton = findViewById(R.id.register_profile_button);
    }
}