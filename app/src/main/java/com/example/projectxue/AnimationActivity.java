package com.example.projectxue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Thread front_page = new Thread()
        {
            @Override
            public void run()
            {
                try{
                    sleep(2000);
                    Intent i= new Intent(getApplicationContext(),NavigationDrawer.class);
                    UtilitiesX.CloseAllPreviousCallStack(i);
                    startActivity(i);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        front_page.start();

    }
}