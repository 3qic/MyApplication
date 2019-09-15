package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


// Quelle für runden Button https://stackoverflow.com/questions/9884202/custom-circle-button
//Quelle für hintergrund bzw rahmen des info feldes: https://stackoverflow.com/questions/3496269/how-do-i-put-a-border-around-an-android-textview
// Code für Bürgermenü https://www.youtube.com/watch?v=fGcMLu1GJEc und https://www.youtube.com/watch?v=bjYstsO1PgI

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button randomButton;
    private Button addbutton;
    private Button searchForName;
    private Button searchForIngrediant;



    private FirebaseAuth firebaseAuth;
    private int user = 0;

    SharedPreferences app_preferences;

    int appTheme;
    int themeColor;
    int appColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
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
        drawer = findViewById(R.id.drawer_layout);
        randomButton = findViewById(R.id.random_button);
        addbutton = findViewById(R.id.add_button);
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
                break;

           case R.id.nav_profile:

               if(user == 0){
                   Intent profileIntent = new Intent(this, LogInActivity.class);
                   startActivity(profileIntent);
               } else {
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
               }

                break;

            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.nav_shopping_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingListFragment()).commit();
                break;

            case R.id.nav_favourite_dish:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteDishFragment()).commit();
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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setupNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }




    private void setupListener(){

       randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomRecipe();

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
               randomButton.setVisibility(View.INVISIBLE);
               addbutton.setVisibility(View.INVISIBLE);
             searchForName.setVisibility(View.INVISIBLE);
              searchForIngrediant.setVisibility(View.INVISIBLE);

           }
       });

        searchForIngrediant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IngredientSearchActivity.class);
                startActivity(intent);
            }
        });

    }




    private void  switchToSearchbar(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    private void getRandomRecipe(){



        /* int max = Recipe.getId();
         int min = Constant.idMin;
         int randomId = new Random().nextInt((max - min) + 1) + min;
            //es fehlt nur noch ein adapter der über die id ein rezept zurück gib
       */

        }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            user = 0;
        } else {
            user = 1;

        }

    }


}
