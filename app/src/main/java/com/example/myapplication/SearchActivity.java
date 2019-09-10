package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Recipe> options;
    FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder> adapter;


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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("recipe-database").child("Rezepte");
        list = new ArrayList<>();
        searchbar = findViewById(R.id.searchView);
        results = findViewById(R.id.search_results);
        results.setLayoutManager(new LinearLayoutManager(this));
        display();

    }


    private void display() {
        options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(databaseReference, Recipe.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Recipe model) {
                holder.name.setText(model.getName());
                holder.desc.setText(model.getKurzbeschreibung());
                holder.ingredts.setText(model.getZutaten());
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemview = LayoutInflater.from(getBaseContext()).inflate(R.layout.recipe_searchresult_layout, viewGroup, false);
                return new MyRecyclerViewHolder(itemview);
            }
        };


        adapter.startListening();
        results.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(databaseReference != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(Recipe.class));
                        }
                        display();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        if (searchbar != null){
            searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);

                    return true;
                }
            });
        }

        }
    }

    public void search(String string){
        ArrayList<Recipe> searchList = new ArrayList<>();
        for (Recipe object : list) {
            if (object.getName().toLowerCase().contains(string.toLowerCase())) {
                searchList.add(object);
            }
        }
    }
}