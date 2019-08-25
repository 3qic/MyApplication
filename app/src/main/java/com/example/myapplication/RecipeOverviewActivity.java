package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class RecipeOverviewActivity extends AppCompatActivity {

    private TextView nameOfRecipe, time, ingredients;
    private ImageView img;
    private Button cookNowButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
