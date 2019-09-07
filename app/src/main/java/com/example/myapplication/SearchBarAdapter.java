package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBarAdapter extends RecyclerView.Adapter<SearchBarAdapter.MyViewHolder> {

    ArrayList<Recipe>list;
    public SearchBarAdapter(ArrayList<Recipe>list)
    {
        this.list=list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_searchresult_layout, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(list.get(i).getName());
        myViewHolder.desc.setText(list.get(i).getKurzbeschreibung());
        myViewHolder.id.setText(list.get(i).getRezeptID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc, id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            desc = itemView.findViewById(R.id.Kurzbeschreibung);
            id = itemView.findViewById(R.id.RezeptID);

        }
    }
}


