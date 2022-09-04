
package com.example.healthifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthifyapp.SharedPreferences.SharedPreference;
import com.example.healthifyapp.api.MobileotpApi;
import com.example.healthifyapp.ashutoshseven.Name3;
import com.example.healthifyapp.model.MobileOtpDataModel;
import com.example.healthifyapp.model.UserAccountDataModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtpActivity extends AppCompatActivity {
    Button btnSubmit;

    EditText editText;

    String mobileno="";
    String otpStr="";
    String otp;
    int userAccountId=0;
   // ArrayList<UserAccountDataModel> userData = new ArrayList<UserAccountDataModel>();
    MobileOtpDataModel mobileOtpDataModel ;
    UserAccountDataModel userAccountDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnSubmit = findViewById(R.id.submit);
        editText = findViewById(R.id.edittextotp);

        mobileno = getIntent().getStringExtra("mobile_no");
        otpStr = getIntent().getStringExtra("otp");
        mobileOtpDataModel = new MobileOtpDataModel();
        userAccountDataModel = new UserAccountDataModel();
        SharedPreference.saveSharedSetting(this,"mobile_no",mobileno);

//        Log.v("Mobile: ",mobileno);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Intent intent = new Intent(OtpActivity.this,FirstActivity.class);
                //startActivity(intent);
                otp = SharedPreference.readSharedSetting(OtpActivity.this, "otp", "0");
                Log.d("otp in otpActivity",":"+otp);


//
                OtpData(otpStr,mobileno);
            }
        });

    }

    private void OtpData(String otp,String mobileno) {
        //  String mobileno = editTextmobilenumber.getText().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://easywaygst.theumangsociety.org").addConverterFactory(GsonConverterFactory.create()).build();

        MobileotpApi retrofitAPI = retrofit.create(MobileotpApi.class);
        //  JSONObject mobileDataModel=new JSONObject();
        Call <MobileOtpDataModel> call = retrofitAPI.VerifyUser(mobileno,otp);
        call.enqueue(new Callback <MobileOtpDataModel>() {
            @Override
            public void onResponse(Call <MobileOtpDataModel> call,Response <MobileOtpDataModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();
                    Toast.makeText(OtpActivity.this,"Verify Successfully",Toast.LENGTH_SHORT).show();
                    Log.e("TAG","Response =" + responseString);
                    Gson gson = new Gson();

                    // code updated by ashutosh
                    // getting the json obect of result from api to get id
                    mobileOtpDataModel = response.body();
                    userAccountDataModel = mobileOtpDataModel.getResult();
                    userAccountId = userAccountDataModel.getUserAccountId();
                    SharedPreference.saveSharedSetting(OtpActivity.this,"userAccountId",userAccountId);
                    String s1 = gson.toJson(response.body());
                    Log.e("Response",s1);
                    Log.d("otp",":"+otp);
                    Log.d("userAccountId",":"+userAccountId);
                    Intent intent = new Intent(OtpActivity.this,Name3.class);
                    startActivity(intent);

                } else if (!response.isSuccessful()) {

                    Toast.makeText(OtpActivity.this,"varification Failed",Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("TAG","Response =" + responseString);
                }
                Log.e("TAG","Response =" + response);

            }

            @Override
            public void onFailure(Call <MobileOtpDataModel> call,Throwable t) {

            }
        });
    }



}

