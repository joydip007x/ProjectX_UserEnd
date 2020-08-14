package com.example.projectxue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilitiesX {

    public static String UID=null;
    static String DP="https://firebasestorage.googleapis.com/v0/b/projectx-a026c.appspot.com/o/Restaurant%20Profile%20pictures%2FXyHd5RK0pJejWs00a5kiz51BVaf1.jpg?alt=media&token=3ae26079-80f1-4a1f-b61c-283c25a46dda";

    public static void showError(String Err, Context context){

        new MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle("Info")
                .setMessage(Err)
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
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
     static boolean isPhoneValid(String s)
    {

        Pattern p = Pattern.compile("(01)?[0-9]{9}");
        Matcher m = p.matcher(s);
        return ((m.find() && m.group().equals(s)) && s.length()<=11 );
    }

    public static Intent CloseAllPreviousCallStack(Intent intent){
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }




}

