package com.example.healthifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PrimaryAnalysisDietACtivity extends AppCompatActivity {

    CardView cardView1, cardView2,cardView3, cardViewaddextrabreakfast,cardViewaddextralunch,cardViewaddextradinner;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_primary_analysis_diet);

        linearLayout=findViewById(R.id.btn_Back);

        cardView1=findViewById(R.id.cardview1);
        cardView2=findViewById(R.id.cardview2);
        cardView3=findViewById(R.id.cardview3);
        cardViewaddextrabreakfast=findViewById(R.id.addextrabreakfast);
        cardViewaddextradinner=findViewById(R.id.addextradinner);
        cardViewaddextralunch=findViewById(R.id.addextralunch);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                startActivity(intent);
            }
        });
        cardViewaddextrabreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,AddExtraBreakfast.class);
                startActivity(intent);
            }
        });
        cardViewaddextradinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,AddExtraDinner.class);
                startActivity(intent);
            }
        });

        cardViewaddextralunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,AddExtraLunch.class);
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,Breakfast.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,LunchActivity.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryAnalysisDietACtivity.this,DinnerActivity.class);
                startActivity(intent);
            }
        });




    }
}
