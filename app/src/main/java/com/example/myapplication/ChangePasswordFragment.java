package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {


    private EditText newPw;
    private EditText newPwConfirm;
    private Button saveChanges;

    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.fragment_change_password,container,false);

                newPw = v.findViewById(R.id.user_newPw);
                newPwConfirm = v.findViewById(R.id.user_newPwConfirm);
                saveChanges = v.findViewById(R.id.changePw_Button);
                firebaseAuth = FirebaseAuth.getInstance();

                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePW();
                    }
                });

                return v;
    }

    private void changePW() {

        String newPassword = newPw.getText().toString().trim();
        String newPasswordConfirm = newPwConfirm.getText().toString().trim();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (newPassword.equals(newPasswordConfirm)) {

                firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            Toast.makeText(getContext(), "Password geändert", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getContext(), "Password konnte nicht geändert werden", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            } else {Toast.makeText(getContext(), "Passwörter sind nicht gleich", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
