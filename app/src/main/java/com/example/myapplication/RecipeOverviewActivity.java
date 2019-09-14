package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeOverviewActivity extends AppCompatActivity {

    private TextView nameOfRecipe, descriprion, time, ingredients;


    private Button cookNowButton;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private String nameString;
    private String descriptionString;
    private String cookingTimeString;
    private String instructionString;
    private String ingrediantsString;





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

                startActivity(i);
            }
        });
    }
    private void putInfo(){
        nameOfRecipe.setText(nameString);
        descriprion. setText(descriptionString);
        ingredients.setText(ingrediantsString);
        time.setText(cookingTimeString);
    }
}
