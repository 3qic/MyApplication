package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RecipeOverviewActivity extends AppCompatActivity {

    private TextView nameOfRecipe, descriprion, time, ingredients;
    private Button cookNowButton;
    SharedPreferences app_preferences;

    int appTheme;
    int themeColor;
    int appColor;
    private ImageButton image;
    private String nameString;
    private String descriptionString;
    private String cookingTimeString;
    private String instructionString;
    private String ingrediantsString;
    private String idString;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    private int favourite = 1;
    private int loginStatus = 0;





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
        }
        setContentView(R.layout.recipe_overview_activity);
        setupLayout();
        setupListener();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Lieblingsrezepte").child(mAuth.getCurrentUser().getUid());


        nameString = getIntent().getExtras().get("Name").toString();
        descriptionString = getIntent().getExtras().get("Beschreibung").toString();
        ingrediantsString = getIntent().getExtras().get("Zutaten").toString();
        instructionString = getIntent().getExtras().get("Kochanleitung").toString();
        cookingTimeString = getIntent().getExtras().get("Zubereitungszeit").toString();
        idString = getIntent().getExtras().get("RezeptID").toString();
        putInfo();
    }



    private void setupLayout(){
        descriprion = findViewById(R.id.recipe_short_description);
        nameOfRecipe = findViewById(R.id.nameOfRecipe);
        time = findViewById(R.id.recipe_cooking_time);
        ingredients = findViewById(R.id.ingredients);
        cookNowButton = findViewById(R.id.cookNow);
        image = findViewById(R.id.favourite_button_empty);


    }



    private void setupListener(){
        cookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecipeActivity.class);

                i.putExtra("Name", nameString);
                i.putExtra("Beschreibung", descriptionString);
                i.putExtra("Zutaten", ingrediantsString);
                i.putExtra("Zubereitungszeit", cookingTimeString);
                i.putExtra("Kochanleitung", instructionString);
                i.putExtra("RezeptID", idString);


                startActivity(i);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    if (favourite == 1) {

                        Lieblingsrezept lieblingsrezept = new Lieblingsrezept();

                        String id = idString;
                        String name = nameString;
                        String desrc = descriptionString;
                        lieblingsrezept.setName(name);
                        lieblingsrezept.setRezeptid(id);
                        lieblingsrezept.setKurzbeschreibung(desrc);

                        reference.push().setValue(lieblingsrezept);

                        image.setBackgroundResource(R.drawable.ic_favorite_full);
                       Toast.makeText(getApplicationContext(), "Favorit hinzugefügt", Toast.LENGTH_SHORT).show();
                       favourite = 0;

                    } else {

                        image.setBackgroundResource(R.drawable.ic_favorite_empty);
                        Toast.makeText(getApplicationContext(), "Von Favoriten entfernt", Toast.LENGTH_SHORT).show();

                        favourite = 1;
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Bitte loggen Sie sich ein um Favoriten hinzuzufügen", Toast.LENGTH_SHORT).show();
                }

                //hier noch datenbank updaten!!!


            }
        });

    }
    private void putInfo(){
        nameOfRecipe.setText(nameString);
        descriprion. setText(descriptionString);
        ingredients.setText(ingrediantsString);
        time.setText(cookingTimeString);
    }
    @Override
    public void onStart() {

        // checken ob Rezept in der Favoritenliste ist


        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginStatus = 1;
        } else {
            loginStatus = 0;

        }

    }


}
