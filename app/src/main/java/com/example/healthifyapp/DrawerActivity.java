package com.example.healthifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.healthifyapp.SharedPreferences.SharedPreference;

import com.example.healthifyapp.model.CalculationDataModel;
import com.example.healthifyapp.report.PrimaryAnalysisDietReport;
import com.example.healthifyapp.report.PrimaryReport;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    CalculationDataModel bmiModel;
    String bmi;
    SharedPreference sharedPreference;
    String name, mobileno, city, gender, dob, weightUnit, heightUnit, lifeStyleModel, lifeStyleSubModel;
    float height, weight;
//    name = getIntent().getStringExtra("login_name");


    TextView userNameText, mobileNoText;
    private BottomNavigationView.OnNavigationItemSelectedListener navSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;

            switch (item.getItemId()) {
                case R.id.page_1:

                    selectFragment = new HomeFragments();

                    break;

                case R.id.page_2:
                    selectFragment = new PlansFragment();
                    break;

                case R.id.page_3:
                    selectFragment = new MailFragment();
                    break;

                case R.id.page_4:
                    selectFragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = findViewById(R.id.toolsbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //   navigation.setOnNavigationItemReselectedListener((BottomNavigationView.OnNavigationItemReselectedListener) navigationItemSelectedListener);
        navigation.setOnNavigationItemSelectedListener(navSelectedListener);
        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragments()).commit();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.Close);
        actionBarDrawerToggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        userNameText = (TextView) headerView.findViewById(R.id.userNameText);
        mobileNoText = (TextView) headerView.findViewById(R.id.mobileNoText);
        userNameText.setText(SharedPreference.readSharedSetting(this, "login_name", ""));
        mobileNoText.setText(SharedPreference.readSharedSetting(this, "mobile_no", ""));
        mobileno = SharedPreference.readSharedSetting(this, "mobile_no", "");
        Log.d("in Drawer",":"+mobileno);
        //getBmiData();
       try {
           navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   Fragment selectFragment = null;
                   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                   getSupportActionBar().setHomeButtonEnabled(true);
                   switch (item.getItemId()) {
                       case R.id.primaryAnalysis:
                           Intent primary = new Intent(DrawerActivity.this, PrimaryReport.class);
                           startActivity(primary);
                           drawerLayout.closeDrawer(GravityCompat.START);
                           break;
                       case R.id.chatting:
                           Intent intent = new Intent(DrawerActivity.this, Chatus.class);
                           startActivity(intent);
                           drawerLayout.closeDrawer(GravityCompat.START);
                           break;
                       case R.id.askdietitian:
                           Intent askdietintent = new Intent(DrawerActivity.this, AskDietitian.class);
                           startActivity(askdietintent);
                           drawerLayout.closeDrawer(GravityCompat.START);
                           break;

                       case R.id.sharethisapp:
                           Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                           shareIntent.setType("text/plain");
                           shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
                           String app_url = " https://play.google.com/store/apps/saraswatiapp";
                           shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                           startActivity(Intent.createChooser(shareIntent, "Share via"));
                           // selectFragment=new PlansFragment();
                           drawerLayout.closeDrawer(GravityCompat.START);
                           break;

                       case R.id.setting:
                           Intent settingintent = new Intent(DrawerActivity.this, Settings.class);
                           startActivity(settingintent);
                           drawerLayout.closeDrawer(GravityCompat.START);


                           break;
                   }

                   return true;
               }
           });
       }
       catch (Exception e)
       {
           Log.d("Report:",""+e.getMessage());
       }
    }



}