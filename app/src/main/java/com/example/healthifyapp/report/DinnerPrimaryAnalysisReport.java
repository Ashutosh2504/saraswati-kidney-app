package com.example.healthifyapp.report;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthifyapp.R;
import com.example.healthifyapp.SharedPreferences.SharedPreference;
import com.example.healthifyapp.adapter.ReportAdapter;
import com.example.healthifyapp.api.LunchPrimaryReportAPI;
import com.example.healthifyapp.model.LunchPrimaryReportDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DinnerPrimaryAnalysisReport extends AppCompatActivity {
    int userAccountId =0;
    LinearLayout linearLayoutButton;
    Root modelList;
    static List<Root.Result.DietAnalysisDetails> dietAnalysisDetails;
    List<Root.Result> resultList;
    Root.Result.DietAnalysisDetails dietAnalysisDetailsObj;
    Root.Result result ;
    List<LunchPrimaryReportDataModel> lunchPrimaryReportDataModels;
    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    ReportAdapter lunchPrimaryReport;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_primary_analysis_report);
        linearLayoutButton=findViewById(R.id.btn_BackDinner);

        lunchPrimaryReportDataModels = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.lunchrecylesview);
        recyclerView.setHasFixedSize(true);
        dietAnalysisDetails = new ArrayList<Root.Result.DietAnalysisDetails>();
        getDinnerPrimaryAnalysisReport(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("Adapterrr","Adapter before  createddd");



        Log.d("Adapterrr","Adapter createddd");

        linearLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DinnerPrimaryAnalysisReport.this, PrimaryAnalysisDietReport.class);
                startActivity(intent);
            }
        });
    }

    private void getDinnerPrimaryAnalysisReport(DinnerPrimaryAnalysisReport dinnerPrimaryAnalysisReport) {

        try {
            userAccountId= SharedPreference.readSharedSetting(getApplicationContext(), "userAccountId", 0);
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://easywaygst.theumangsociety.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LunchPrimaryReportAPI lunchPrimaryReportAPI = retrofit.create(LunchPrimaryReportAPI.class);
            Call<Root> call = lunchPrimaryReportAPI.getlunchprimaryReportDataModel(userAccountId);

            call.enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {

                    if (response.code() ==200)
                    {
                        modelList = response.body();
                        resultList = modelList.getResult();



                        Log.d("TAG", "Response = " + response.message());
                        Toast.makeText(DinnerPrimaryAnalysisReport.this, "Add diet Successfully", Toast.LENGTH_SHORT).show();
                        Log.d("Response", "" + modelList.toString());
                        // Log.d("DietResult", "" + modelList.getDietAnalysisDetailsList().toString());
                        for(int i=0; i<resultList.size(); i++) {
                            Log.d("DietResult", "" + resultList.get(i).getDietAnalysisDetailsList());
                            Log.d("DietResultObj", "" + resultList.get(i));
                            dietAnalysisDetailsObj= resultList.get(i).getDietAnalysisDetailsList().get(0);
                            dietAnalysisDetails.add(dietAnalysisDetailsObj);
                            Log.d("DietResult", "" + dietAnalysisDetails.toString());
                            Log.d("DietResult", "" + dietAnalysisDetails.get(i).getkCal().toString());
                        }
                        reportAdapter = new ReportAdapter(getApplicationContext(), dietAnalysisDetails);
                        recyclerView.setAdapter(reportAdapter);
                        reportAdapter.setDietAnalysisObjectList(dietAnalysisDetails);

                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "Failure in getting report", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Report:","::::"+e.getMessage());
        }
    }
}