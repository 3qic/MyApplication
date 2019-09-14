package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private RecyclerView recyclerView;
    private EditText searchbar;
    FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder> adapter;
    FirebaseRecyclerOptions<Recipe> options;
    private DatabaseReference databaseReference;
    private ArrayList<Recipe> list;

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
        setupView();

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    search(s.toString());
                }else{
                    search("");

                }
            }
        });




        adapter=new FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Recipe model) {

                holder.name.setText(model.getName());
                holder.desc.setText(model.getKurzbeschreibung());
                holder.ingredts.setText(model.getZutaten());
                holder.cookingTime.setText(model.getArbeitszeit());
                holder.instruction.setText(model.getAnleitung());
                holder.id.setText(model.getRezeptid());

                final String name = holder.name.getText().toString();
                final String description = holder.desc.getText().toString();
                final String ingredients = holder.ingredts.getText().toString();
                final String cookingTime = holder.cookingTime.getText().toString();
                final String instruction = holder.instruction.getText().toString();
                final String id = holder.id.getText().toString();


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),RecipeOverviewActivity.class);
                        intent.putExtra("Name", name);
                        intent.putExtra("Beschreibung", description);
                        intent.putExtra("Zutaten", ingredients);
                        intent.putExtra("Zubereitungszeit", cookingTime);
                        intent.putExtra("Kochanleitung", instruction);
                        intent.putExtra("RezeptID", id);
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_searchresult_layout, viewGroup,false);

                return new MyRecyclerViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    private void search(String s) {
        Query query = databaseReference.orderByChild("name")
                .startAt(s)
                .endAt(s + "\uf8ff");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final Recipe recipe = ds.getValue(Recipe.class);
                            list.add(recipe);
                        }
                        SearchBarAdapter myAdapter = new SearchBarAdapter(getApplicationContext(), list);
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    private void setupView(){
        searchbar = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.search_results);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rezepte");
        options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(databaseReference, Recipe.class)
                .build();
    }



}
