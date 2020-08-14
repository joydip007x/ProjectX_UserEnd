package com.example.projectxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.projectxue.UtilitiesX;
import com.example.projectxue.ui.profile.Profile;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileEdit extends AppCompatActivity {

    private TextView user,email,phone;
    Button edit;
    BasicUserProfile b= Profile.basicUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        user=findViewById(R.id.fp2_username);
        email=findViewById(R.id.fp2_email);
        phone=findViewById(R.id.fp2_phone);
        edit=findViewById(R.id.fp2_edit);

        user.setText(b.getUsername());
        email.setText(b.getEmail());
        phone.setText(b.getPhone());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Users").child(UtilitiesX.UID);

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        b=new BasicUserProfile((HashMap) snapshot.getValue());

                        if(!UtilitiesX.isEmailValid(email.getText().toString()) ||
                                !UtilitiesX.isPhoneValid(phone.getText().toString())   ){

                            new MaterialAlertDialogBuilder(ProfileEdit.this, R.style.AlertDialogTheme)
                                    .setTitle("Info")
                                    .setMessage("Check Email && Phone Number  ")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .setNeutralButton("Skip", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();
                            return;
                        }

                        b.setUsername(user.getText().toString());
                        b.setEmail(email.getText().toString());
                        b.setPhone(phone.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Users").child(UtilitiesX.UID).setValue(b.H);
                        Profile.basicUserProfile=b;
                        Profile.updateValue();
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}