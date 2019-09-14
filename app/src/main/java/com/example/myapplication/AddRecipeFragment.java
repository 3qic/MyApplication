package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddRecipeFragment extends Fragment {
    private EditText recipeName;
    private EditText recipeIngredients;
    private EditText recipeDescription;
    private EditText recipeInstruction;
    private EditText recipeCookingTime;

    private DatabaseReference reference;
    private Button uploadRecipe;
    private Recipe recipe;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_add_recipe,container,false);
        recipeName = v.findViewById(R.id.addrecipe_name);
        recipeIngredients = v.findViewById(R.id.addrecipe_ingerdients);
        recipeDescription = v.findViewById(R.id.addrecipe_description);
        recipeInstruction = v.findViewById(R.id.addrecipe_instuctions);
        recipeCookingTime = v.findViewById(R.id.addrecipe_cookingTime);

        uploadRecipe = v.findViewById(R.id.saverecipe_button);

        reference = FirebaseDatabase.getInstance().getReference().child("Rezepte");

        uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( recipeName.getText().length() != 0 && recipeDescription.getText().length() !=0 && recipeInstruction.getText().length() != 0 && recipeIngredients.getText().length() != 0){
                        String name = recipeName.getText().toString();
                        String desc = recipeDescription.getText().toString();
                        String Instruct = recipeInstruction.getText().toString();
                        String Ingredts = recipeIngredients.getText().toString();
                        String CookingTime = recipeCookingTime.getText().toString();

                        recipe = new Recipe();
                        recipe.setName(name);
                        recipe.setKurzbeschreibung(desc);
                        recipe.setAnleitung(Instruct);
                        recipe.setZutaten(Ingredts);
                        recipe.setArbeitszeit(CookingTime);
                        recipe.setRezeptid(UUID.randomUUID().toString());

                        reference.push().setValue(recipe);
                    Toast.makeText(getActivity(),"Rezept hochgeladen", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                } else{

                    Toast.makeText(getActivity(),"Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


}
