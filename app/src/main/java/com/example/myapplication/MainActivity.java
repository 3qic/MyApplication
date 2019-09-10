package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


// Quelle für runden Button https://stackoverflow.com/questions/9884202/custom-circle-button
//Quelle für hintergrund bzw rahmen des info feldes: https://stackoverflow.com/questions/3496269/how-do-i-put-a-border-around-an-android-textview

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button foodbutton;
    private Button addbutton;
    private Button searchForName;
    private Button searchForIngrediant;


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

        setContentView(R.layout.activity_main);

        findViews();
        setSupportActionBar(toolbar);
        setupDrawer();
        setupNavigationView();
        setupListener();

    }

    public void findViews() {
        toolbar = findViewById(R.id.main_toolbar);
        foodbutton = findViewById(R.id.foodsharing_button);
        addbutton = findViewById(R.id.add_button);
        drawer = findViewById(R.id.drawer_layout);
        searchForName = findViewById(R.id.searchrecipe_button);
        searchForIngrediant = findViewById(R.id.searchforingredients_button);


    }


    public void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_opern, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.nav_note:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoteFragment()).commit();
                hideButtons();
                break;

            case R.id.nav_profile:
                Intent profileIntent = new Intent(this, LogInActivity.class);
                startActivity(profileIntent);
                hideButtons();
                break;

            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.nav_shopping_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingListFragment()).commit();
                hideButtons();
                break;

            case R.id.nav_favourite_dish:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteDishFragment()).commit();
                hideButtons();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
        }
    }

    public void setupNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void switchToRecipe (){
        Intent i = new Intent(this, RecipeOverviewActivity.class);
        startActivity(i);

    }

    private void setupListener(){

       foodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRecipe();

            }
        });

       searchForName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switchToSearchbar();
           }
       });

       addbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddRecipeFragment()).commit();
                hideButtons();
           }
       });

    }

    private void hideButtons(){
        foodbutton.setVisibility(View.INVISIBLE);
        addbutton.setVisibility(View.INVISIBLE);
        searchForName.setVisibility(View.INVISIBLE);
        searchForIngrediant.setVisibility(View.INVISIBLE);
    }

    private void switchToLogin(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    private void  switchToSearchbar(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}
