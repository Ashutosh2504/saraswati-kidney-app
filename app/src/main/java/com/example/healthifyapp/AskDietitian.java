package com.example.healthifyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AskDietitian extends AppCompatActivity {
LinearLayout linearLayout;
TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_dietitian);
        linearLayout=findViewById(R.id.btn_Back);
        textView=findViewById(R.id.call);
        textView1=findViewById(R.id.callWhatapp);





        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whurl="http://wa.me/++917887343625?text=";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(whurl));

                startActivity(intent);

/////  boolean installed = isAppInstalled("com.whatsapp");
//
//                if(installed){
//
//                    Intent intent=new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone ="+num+"&text="+ text));
//                    startActivity(intent);
//                }else {
//
//                    Toast.makeText(AskDietitian.this,"Whatsapp is not installed!",Toast.LENGTH_SHORT).show();
//                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+917887343625"));
                startActivity(intent);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AskDietitian.this,DrawerActivity.class);
                startActivity(intent);
            }
        });

    }


}