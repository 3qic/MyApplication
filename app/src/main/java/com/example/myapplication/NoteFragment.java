package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

//Code für Shared Preferences Basierend auf Code von https://www.youtube.com/watch?v=GlR7wqWEomU

public class NoteFragment extends Fragment {
    EditText Notepad;
    FloatingActionButton saveButton;
    String textnote;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        Notepad = v.findViewById(R.id.EditTextNote);
        saveButton = v.findViewById(R.id.floating_save_button);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        textnote = preferences.getString("Text", "");
        Notepad.setText(textnote);
        editor = preferences.edit();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textnote = Notepad.getText().toString();
                editor.putString("Text", textnote);
                editor.commit();


                Toast.makeText(getContext(), "Notiz gespeichert", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


}
