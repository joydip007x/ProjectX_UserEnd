package com.example.projectxue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectxue.BasicUserProfile;
import com.example.projectxue.Demo;
import com.example.projectxue.ForgotPass;
import com.example.projectxue.OnGetDataListener;
import com.example.projectxue.R;
import com.example.projectxue.RegisterAccount;
import com.example.projectxue.UtilitiesX;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {


    Button log,reg,forgot;
    TextInputEditText username,pass;
    FirebaseAuth auth;
    String email,password;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        log=findViewById(R.id.ma_sign);
        reg=findViewById(R.id.ma_reg);
        username=findViewById(R.id.ma_username);
        pass=findViewById(R.id.ma_pass);
        progressBar=findViewById(R.id.progressBar);
        forgot=findViewById(R.id.ma_forgot);

        FirebaseApp.initializeApp(MainActivity.this);
        auth=FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);


        final OnGetDataListener OG=new OnGetDataListener() {
            @Override
            public void onSuccess(String reg) {
                signInUser();
            }
            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        };
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=username.getText().toString().trim();
                password=pass.getText().toString().trim();

                if(!UtilitiesX.isEmailValid(email) ){

                    username.setText(" ");
                    username.setError("Invalid mail");
                    return;
                }
                if(password.length()<8){
                    UtilitiesX.showError("Password length must be 8 character long ",MainActivity.this);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signInUser();

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(  MainActivity.this, RegisterAccount.class);
                // i=Utilities.CloseAllPreviousCallStack(i);
                startActivity(i);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ForgotPass.class));
            }
        });

    }
    private void signInUser() {


        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    UtilitiesX.UID=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    checkData(FirebaseDatabase.getInstance().getReference("Users").child(UtilitiesX.UID), new OnGetDataListener() {
                        @Override
                        public void onSuccess(String reg) {

                            Toasty.success(MainActivity.this, "Logged in !", Toast.LENGTH_SHORT, true).show();
                            Intent i=new Intent(  MainActivity.this, NavigationDrawer.class);
                            i= UtilitiesX.CloseAllPreviousCallStack(i);
                            startActivity(i);
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure() {

                            Toasty.warning(MainActivity.this, "Not Registered as an User !", Toast.LENGTH_SHORT, true).show();
                            FirebaseAuth.getInstance().signOut();
                            UtilitiesX.UID=null;
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });


                }
                else {
                    Toasty.error(MainActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    public void checkData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("LAAL  fetched "+ snapshot.toString());

                if(!snapshot.exists()){
                    listener.onFailure();
                    return;
                }
                BasicUserProfile basicUserProfile=new BasicUserProfile((HashMap) snapshot.getValue());

                if(!basicUserProfile.getUser().equals("1")){
                    listener.onFailure();
                    return;
                }
                listener.onSuccess(null);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}