package com.example.projectxue;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    public static AppBarConfiguration mAppBarConfiguration;
    private  View headerLayout;
    TextView headerName;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        rfaBtn=findViewById(R.id.activity_main_rfab);
        rfaLayout=findViewById(R.id.activity_main_rfal);

        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.nav_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_order, R.id.nav_past_order,R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        headerLayout=navigationView.getHeaderView(0);

        headerName= headerLayout.findViewById(R.id.nvh_name);

        headerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UtilitiesX.UID==null) {
                    Intent i = new Intent(NavigationDrawer.this, MainActivity.class);
                    startActivity(i);
                    return;
                }
                else{

                    ////////
                }
            }
        });

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getApplicationContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Settings")
                .setResId(R.drawable.ic_baseline_settings_applications_24_white)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("LogOut")
                .setResId(R.drawable.ic_baseline_exit_to_app_24)
                .setIconNormalColor(R.color.colorAccent4)
                .setIconPressedColor(R.color.colorAccent)
                .setLabelColor(R.color.colorAccent)
                .setWrapper(2)
        );
        rfaContent
                .setItems(items)
                .setIconShadowColor(0xff888888)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getApplicationContext(),
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

        System.out.println(" LAAL XX "+position);
        clickHandleFAB(position,item);

        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

        System.out.println(" LAAL XX "+position);

        clickHandleFAB(position,item);
        rfabHelper.toggleContent();
    }

    private void clickHandleFAB(int position, RFACLabelItem item) {

        if(position==1){

            new MaterialAlertDialogBuilder(NavigationDrawer.this, R.style.Theme_MaterialComponents_Dialog_Alert)
                    .setTitle("Confirm ")
                    .setMessage("Log Out ?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent j=new Intent(  NavigationDrawer.this, AnimationActivity.class);
                            UtilitiesX.UID=null;
                            FirebaseAuth.getInstance().signOut();
                            startActivity(j);
                            return;
                        }
                    })
                    .setNeutralButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        else {

            Intent j=new Intent(  getApplicationContext(), Demo.class);
            startActivity(j);

        }
    }
}