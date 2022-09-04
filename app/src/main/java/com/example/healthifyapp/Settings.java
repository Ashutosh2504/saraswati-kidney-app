package com.example.healthifyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Settings extends AppCompatActivity {


LinearLayout linearLayout, linearLayoutaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        linearLayout=findViewById(R.id.btn_Back);
        linearLayoutaccount=findViewById(R.id.logout);

        linearLayoutaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder= new AlertDialog.Builder(Settings.this);
                builder.setTitle("Logout");
                 builder.setMessage("Would you like to logout?");
                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         Intent intent=new Intent(Settings.this,MainActivity.class);
                         startActivity(intent);

                     }
                 })
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                                 dialogInterface.dismiss();

                             }
                         }).show();

            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.this,DrawerActivity.class);
                startActivity(intent);
            }
        });


    }
}