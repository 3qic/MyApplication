package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;





public class SearchActivity extends AppCompatActivity {

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private SearchView searchbar;
    private ArrayList<Recipe> list;
    private RecyclerView results;
    private DatabaseReference reference;


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

        setContentView(R.layout.search_activity_layout);
        reference = FirebaseDatabase.getInstance().getReference().child("recipe-database").child("Rezepte");
        findViews();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(Recipe.class));
                        }
                        SearchBarAdapter adapter = new SearchBarAdapter(list);
                        results.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            if (searchbar != null) {
                searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s){
                        search(s);

                        return true;
                    }
                });
            }

        }
    }

    private void search(String string) {

        ArrayList<Recipe> searchList = new ArrayList<>();
        for (Recipe object : list) {
            if (object.getKurzbeschreibung().toLowerCase().contains(string.toLowerCase())) {
                searchList.add(object);
            }
        }
        SearchBarAdapter adapter = new SearchBarAdapter(searchList);
        results.setAdapter(adapter);
    }


    public void findViews() {
        searchbar = findViewById(R.id.searchView);
        results = findViewById(R.id.search_results);

    }


}