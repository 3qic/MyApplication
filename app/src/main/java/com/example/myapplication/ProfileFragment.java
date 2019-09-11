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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {


    private Button logOutButton;
    private Button changePwButton;

    private TextView useremail;

    private FirebaseUser firebaseUser;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        useremail = v.findViewById(R.id.currentUser_nameValue);
        logOutButton = v.findViewById(R.id.logout_Button);
        changePwButton = v.findViewById(R.id.changePw_Button);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            String email =firebaseUser.getEmail();
            useremail.setText(email);
        }


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Ausgeloggt", Toast.LENGTH_SHORT).show();

            }
        });

        changePwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, changePasswordFragment).addToBackStack(null).commit();


            }
        });


        return v;
    }

}
