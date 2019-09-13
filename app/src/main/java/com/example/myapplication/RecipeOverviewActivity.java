package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class RecipeOverviewActivity extends AppCompatActivity {

    private TextView nameOfRecipe, time, ingredients;
    private ImageView img;
    private Button cookNowButton;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

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
        fillItems("Essen duh","40 Minuten", "Stuff");
        setupListener();

    }



    private void setupLayout(){
        nameOfRecipe = findViewById(R.id.nameOfRecipe);
        time = findViewById(R.id.time);
        ingredients = findViewById(R.id.ingredients);
        img = findViewById(R.id.imageOfDish);
        cookNowButton = findViewById(R.id.cookNow);

    }

    private void switchToRecipe (){
        Intent i = new Intent(this, RecipeActivity.class);
        startActivity(i);



    }

    private void setupListener(){

        cookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              switchToRecipe();
            }
        });
    }

    private void fillItems(String name, String cookTime, String ing) {
        nameOfRecipe.setText(name);
        time.setText(cookTime);
        ingredients.setText(ing);

    }

}
