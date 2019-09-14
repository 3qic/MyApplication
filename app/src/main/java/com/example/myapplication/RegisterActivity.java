package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private User user;
    private EditText emailAddress;
    private EditText userPassword;
    private EditText confirmUserPassword;
    private Button registerProfileButton;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    DatabaseReference reference;
    FirebaseUser currentuser;


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
            toolbar = findViewById(R.id.register_toolbar);
            toolbar.setTitle("Registrieren");
            findViews();



            reference = FirebaseDatabase.getInstance().getReference().child("User");

            firebaseAuth = FirebaseAuth.getInstance();
            currentuser  = FirebaseAuth.getInstance().getCurrentUser();


            registerProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   final String email = emailAddress.getText().toString().trim();
                    final String password = userPassword.getText().toString().trim();
                   final  String confirmPassword = confirmUserPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(RegisterActivity.this, "Bitte E-Mail Adresse angeben", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(RegisterActivity.this, "Bitte gewünschtes Passwort eingeben", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(confirmPassword)) {
                        Toast.makeText(RegisterActivity.this, "Bitte Passwort wiederholen", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(RegisterActivity.this, "Passwort ist zu kurz", Toast.LENGTH_SHORT).show();
                    }


                    if (password.equals(confirmPassword)) {

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        if (task.isSuccessful()) {

                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            Toast.makeText(RegisterActivity.this, "Registrierung abgeschlossen", Toast.LENGTH_SHORT).show();

                                            String uid =firebaseAuth.getCurrentUser().getUid();


                                            user = new User();
                                            user.setNutzerID(uid);
                                            user.setName(email);
                                            reference.push().setValue(user);

                                        } else {

                                            Toast.makeText(RegisterActivity.this, "Profil bereits vorhanden", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                    }

                }
            });


        }
    }

    private void findViews() {
        emailAddress = findViewById(R.id.register_user_email);
        userPassword = findViewById(R.id.register_user_password);
        confirmUserPassword = findViewById(R.id.register_user_password_confirm);
        registerProfileButton = findViewById(R.id.register_profile_button);
    }
}