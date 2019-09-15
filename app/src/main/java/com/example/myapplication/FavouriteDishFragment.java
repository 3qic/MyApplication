package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavouriteDishFragment extends Fragment {

   RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Recipe,MyRecyclerViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Recipe> options;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite_dish,container,false);

        recyclerView= v.findViewById(R.id.favorite_recipes);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lieblingsrezepte").child(userID);
        options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(databaseReference, Recipe.class)
                .build();





firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder>(options) {
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
        final String favorite = "yes";


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),RecipeOverviewActivity.class);
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

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        return v;
    }


}
