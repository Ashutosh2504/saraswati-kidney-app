package com.example.healthifyapp.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthifyapp.R;

public class PrimaryReport extends AppCompatActivity {
CardView breakfastReport_Button,dinner_Button, lunchReport_Button;
  //  Button lunchReport_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_report);
        lunchReport_Button = findViewById(R.id.lunchReportCardView);
        breakfastReport_Button=findViewById(R.id.breakfastReportCardview);
        dinner_Button=findViewById(R.id.dinnerReportCardView);

        breakfastReport_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryReport.this, BreakFastprimaryanalysisReport.class);
                startActivity(intent);
            }
        });

        dinner_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryReport.this,DinnerPrimaryAnalysisReport.class);
                startActivity(intent);
            }
        });

        lunchReport_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrimaryReport.this, LunchPrimaryAnalysisReport.class);
                startActivity(intent);
            }
        });
    }
}