package com.example.projectxue.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectxue.BasicUserProfile;
import com.example.projectxue.MainActivity;
import com.example.projectxue.NavigationDrawer;
import com.example.projectxue.OnGetDataListener;
import com.example.projectxue.ProfileEdit;
import com.example.projectxue.R;
import com.example.projectxue.UtilitiesX;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile extends Fragment {


    private static TextView user,email,phone;
    private Button edit;
    public static  BasicUserProfile basicUserProfile;

    public static void updateValue() {

        user.setText("User Name : "+basicUserProfile.getUsername());
        email.setText("Email : "+basicUserProfile.getEmail());
        phone.setText("Phone : "+basicUserProfile.getPhone());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        if(UtilitiesX.UID==null){

            new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogTheme)
                    .setTitle("Info")
                    .setMessage("You need to login/sign up first ! ")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent j=new Intent(  getContext(), MainActivity.class);
                            startActivity(j);
                        }
                    })
                    .setNeutralButton("Skip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            NavigationDrawer.mAppBarConfiguration.getDrawerLayout().openDrawer(Gravity.LEFT);
                        }
                    })
                    .show();
            return null;

        }

        user=v.findViewById(R.id.fp_username);
        email=v.findViewById(R.id.fp_useremail);
        phone=v.findViewById(R.id.fp_userphone);
        edit=v.findViewById(R.id.fp_edit);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j=new Intent(  getContext(), ProfileEdit.class);
                startActivity(j);
            }
        });
        OnGetDataListener OG=new OnGetDataListener() {
            @Override
            public void onSuccess(String reg) {

             ///   System.out.println(" LAAL X2 "+basicUserProfile.H.toString());

                updateValue();

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        };

       if(UtilitiesX.UID!=null)
           RetrieveData(FirebaseDatabase.getInstance().getReference("Users").child(UtilitiesX.UID),OG);
        return  v;

    }

    public void RetrieveData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists() ){
                    listener.onFailure();
                    return;
                }
                basicUserProfile=new BasicUserProfile((HashMap) snapshot.getValue());
                listener.onSuccess(null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}