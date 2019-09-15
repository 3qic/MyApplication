package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Code: https://www.youtube.com/watch?v=KOUyvCkwRss

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView name, desc, ingredts, instruction, cookingTime, id;

    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.Name);
        desc = itemView.findViewById(R.id.Kurzbeschreibung);
        ingredts = itemView.findViewById(R.id.Zutaten);
        instruction = itemView.findViewById(R.id.Anleitung);
        cookingTime = itemView.findViewById(R.id.Kochzeit);
        id = itemView.findViewById(R.id.Rezeptid);
    }
}
