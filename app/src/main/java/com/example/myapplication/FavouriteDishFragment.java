package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());
        options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(databaseReference, Recipe.class)
                .build();





firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Recipe, MyRecyclerViewHolder>(options) {
    @Override
    protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Recipe model) {
        holder.name.setText(model.getName());
        holder.desc.setText(model.getKurzbeschreibung());

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
