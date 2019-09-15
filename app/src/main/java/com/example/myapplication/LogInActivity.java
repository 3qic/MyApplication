package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//Code für Shared Preferences: https://www.youtube.com/watch?v=GlR7wqWEomU

public class LogInActivity extends AppCompatActivity {


    private Button registerButton;

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private Button loginButton;
    private EditText userEmail;
    private EditText userPw;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            setContentView(R.layout.login_activity);
            findViews();
            firebaseAuth = FirebaseAuth.getInstance();

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = userEmail.getText().toString().trim();
                    String password = userPw.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(LogInActivity.this, "Bitte E-Mail Adresse angeben", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(LogInActivity.this, "Bitte gewünschtes Passwort eingeben", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(LogInActivity.this, "Passwort ist zu kurz", Toast.LENGTH_SHORT).show();
                    }

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        Toast.makeText(LogInActivity.this, "LogIn erfolgreich", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LogInActivity.this, "E-Mail Adresser oder Passwort ist falsch", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            });


            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void findViews() {
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.signIn_button);
        userEmail = findViewById(R.id.signIn_userEmail);
        userPw = findViewById(R.id.signIn_userPw);
        toolbar = findViewById(R.id.login_toolbar);
        toolbar.setTitle("Einloggen");
    }
}
