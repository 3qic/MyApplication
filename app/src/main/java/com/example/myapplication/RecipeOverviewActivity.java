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
    private Button cookNowButton;SharedPreferences app_preferences;
    SharedPreferences.Editor editor;
    int appTheme;
    int themeColor;
    int appColor;
    int favButton;

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
    private int favouriteStatus;
    private int loginStatus = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);

        themeColor = appColor;
        favButton = app_preferences.getInt("buttonstate", 1);
        favouriteStatus = favButton;


        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }



        setContentView(R.layout.recipe_overview_activity);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Lieblingsrezepte").child(mAuth.getCurrentUser().getUid());

        setupLayout();



        setupListener();

        nameString = getIntent().getExtras().get("Name").toString();
        descriptionString = getIntent().getExtras().get("Beschreibung").toString();
        ingrediantsString = getIntent().getExtras().get("Zutaten").toString();
        instructionString = getIntent().getExtras().get("Kochanleitung").toString();
        cookingTimeString = getIntent().getExtras().get("Zubereitungszeit").toString();


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
                    if (favouriteStatus == 1) {

                        Recipe recipe = new Recipe();

                        String id = idString;
                        String name = nameString;
                        String descr = descriptionString;
                        String time = cookingTimeString;
                        String instr = instructionString;
                        String ingred = ingrediantsString;

                        recipe.setName(name);
                        recipe.setRezeptid(id);
                        recipe.setKurzbeschreibung(descr);
                        recipe.setAnleitung(instr);
                        recipe.setArbeitszeit(time);
                        recipe.setZutaten(ingred);

                        reference.push().setValue(recipe);

                        image.setBackgroundResource(R.drawable.ic_favorite_full);
                       Toast.makeText(getApplicationContext(), "Favorit hinzugefügt", Toast.LENGTH_SHORT).show();

                       favouriteStatus = 0;

                    } else {

                        image.setBackgroundResource(R.drawable.ic_favorite_empty);
                        Toast.makeText(getApplicationContext(), "Von Favoriten entfernt", Toast.LENGTH_SHORT).show();


                        Recipe recipe = new Recipe();

                        String id = null;
                        String name = null;
                        String descr = null;
                        String time = null;
                        String instr = null;
                        String ingred = null;

                        recipe.setName(name);
                        recipe.setRezeptid(id);
                        recipe.setKurzbeschreibung(descr);
                        recipe.setAnleitung(instr);
                        recipe.setArbeitszeit(time);
                        recipe.setZutaten(ingred);


                        reference.setValue(recipe);


                        favouriteStatus = 1;


                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Bitte loggen Sie sich ein um Favoriten hinzuzufügen", Toast.LENGTH_SHORT).show();
                }

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

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginStatus = 1;
        } else {
            loginStatus = 0;

        }

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }



}
