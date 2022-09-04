package com.example.healthifyapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.example.healthifyapp.SharedPreferences.SharedPreference;

import com.example.healthifyapp.api.HelathCalculateApi;
import com.example.healthifyapp.api.UserAccountAPI;
import com.example.healthifyapp.apiclient.UserAccountclient;
import com.example.healthifyapp.model.CalculationDataModel;
import com.example.healthifyapp.model.HealthCalculationModel;
import com.example.healthifyapp.model.LifeStyleItemDataModel;
import com.example.healthifyapp.model.UserAccountDataModel;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;


public class HomeFragments extends Fragment {

    String mobilno = "";
    int UserId = 0, userAccountId=0;
    ViewFlipper viewFlipper;
    int imgarray[] = {R.drawable.dietplanslider1, R.drawable.dietplanslider2, R.drawable.dietplanslider3};
    TextView txt_username, txtView_PrimaryAnalysis;
    TextView textView_bmi, textView_bfp, textView_ibw, textView_lbw, textView_bsa;
    String bmi;
    HealthCalculationModel healthCalculationModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_fragments, container, false);
        // imageSlider=view.findViewById(R.id.image_slider);
        //List<SlideModel> slideModels= new ArrayList<>();
        //slideModels.contains(R.drawable.dietplanslider1);
        //slideModels.contains(R.drawable.dietplanslider2);
        //slideModels.contains(R.drawable.dietplanslider3);
        //List<String> vowels = new ArrayList<>();
        // slideModels.add(new SlideModel(R.drawable.dietplanslider1));
        //slideModels.add(new SlideModel(R.drawable.dietplanslider2));
        //slideModels.add(new SlideModel(R.drawable.dietplanslider3));
        //imageSlider.setImageList(slideModels);


        viewFlipper = view.findViewById(R.id.filpeer);
        for (int i = 0; i < imgarray.length; i++) {
            setFlipperImage(imgarray[i]);

        }

        mobilno = SharedPreference.readSharedSetting(getActivity(), "mobile_no", "");
         userAccountId= SharedPreference.readSharedSetting(getActivity(), "userAccountId", 0);
        // creating object for healthcalculationmodel
        //healthCalculationModel = new HealthCalculationModel();

        try {
            UserId = Integer.parseInt(mobilno);
        } catch (NumberFormatException nfe) {
            // Handle the condition when str is not a number.
        }


        txt_username = view.findViewById(R.id.txt_username);
//        textView_bmi.setText(""+ SharedPreference.readSharedSetting(getActivity(),"Id",""));
        txt_username.setText("Dear " + SharedPreference.readSharedSetting(getActivity(), "login_name", ""));
        textView_bsa = view.findViewById(R.id.bsa);

        textView_bfp = view.findViewById(R.id.bfp);
        textView_bmi = view.findViewById(R.id.bmi);
        textView_lbw = view.findViewById(R.id.lbw);
        textView_ibw = view.findViewById(R.id.ibw);
        // bmi = SharedPreference.readSharedSetting(getContext(), "bmi", "");
        //textView_bmi.setText(Integer.parseInt(bmi));
        // getBmiData();
        txtView_PrimaryAnalysis = view.findViewById(R.id.txtView_PrimaryAnalysis);

        txtView_PrimaryAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlert();
            }
        });
        getBmiData(userAccountId);
        getBFPData(userAccountId);
        getBSAData(userAccountId);
        getIBWData(userAccountId);
        getLBWData(userAccountId);
        ImageButton imageButton = view.findViewById(R.id.arrowImage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customAlert();


            }
        });

        return view;

    }

    private void getBmiData(int userAccountId) {


        HelathCalculateApi bmiapi = UserAccountclient.getClient().create(HelathCalculateApi.class);



        Call<HealthCalculationModel> call = bmiapi.getbmi(userAccountId);

        call.enqueue(new Callback<HealthCalculationModel>() {
            @Override
            public void onResponse(Call<HealthCalculationModel> call, Response<HealthCalculationModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();

                    Log.d("TAG", "Response =" + responseString);
//
                    healthCalculationModel = response.body();
                    Toast.makeText(getContext(), "" + healthCalculationModel.getResult(), Toast.LENGTH_SHORT).show();

                    SharedPreference.saveSharedSetting(getActivity(),"bmi",healthCalculationModel.getResult() );
                    Log.d("BMIIIII", ":" + healthCalculationModel.getResult());
                    textView_bmi.setText(healthCalculationModel.getResult());

                } else if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Failed in BMI", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("Failed in BMI", "Response =" + responseString);
                }
            }

            @Override
            public void onFailure(Call<HealthCalculationModel> call, Throwable t) {

            }
        });

    }

    private void getLBWData(int userAccountId) {


        HelathCalculateApi lbwapi = UserAccountclient.getClient().create(HelathCalculateApi.class);

        Call<HealthCalculationModel> call = lbwapi.getlbw(userAccountId);

        call.enqueue(new Callback<HealthCalculationModel>() {
            @Override
            public void onResponse(Call<HealthCalculationModel> call, Response<HealthCalculationModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();

                    Log.d("TAG", "Response =" + responseString);
//
                    healthCalculationModel = response.body();
                    Toast.makeText(getContext(), "" + healthCalculationModel.getResult(), Toast.LENGTH_SHORT).show();

                    Log.d("BMIIIII", ":" + healthCalculationModel.getResult());
                    textView_lbw.setText(healthCalculationModel.getResult());

                } else if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Failed in LBW ", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("Failed in LBW", "Response =" + responseString);
                }
            }

            @Override
            public void onFailure(Call<HealthCalculationModel> call, Throwable t) {

            }
        });

    }

    private void getIBWData(int userAccountId) {


        HelathCalculateApi ibwapi = UserAccountclient.getClient().create(HelathCalculateApi.class);



        Call<HealthCalculationModel> call = ibwapi.getibw( userAccountId);

        call.enqueue(new Callback<HealthCalculationModel>() {
            @Override
            public void onResponse(Call<HealthCalculationModel> call, Response<HealthCalculationModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();

                    Log.d("TAG", "Response =" + responseString);
//
                    healthCalculationModel = response.body();
                    Toast.makeText(getContext(), "" + healthCalculationModel.getResult(), Toast.LENGTH_SHORT).show();

                    Log.d("IBWWWWWW", ":" + healthCalculationModel.getResult());
                    SharedPreference.saveSharedSetting(getActivity(),"ibw",healthCalculationModel.getResult() );
                    textView_ibw.setText(healthCalculationModel.getResult());

                } else if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Failed in IBW", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("Failed in IBW", "Response =" + responseString);
                }
            }

            @Override
            public void onFailure(Call<HealthCalculationModel> call, Throwable t) {

            }
        });

    }

    private void getBSAData(int userAccountId) {


        HelathCalculateApi bsaapi = UserAccountclient.getClient().create(HelathCalculateApi.class);



        Call<HealthCalculationModel> call = bsaapi.getbsa(userAccountId);

        call.enqueue(new Callback<HealthCalculationModel>() {
            @Override
            public void onResponse(Call<HealthCalculationModel> call, Response<HealthCalculationModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();

                    Log.d("TAG", "Response =" + responseString);
//
                    healthCalculationModel = response.body();
                    Toast.makeText(getContext(), "" + healthCalculationModel.getResult(), Toast.LENGTH_SHORT).show();

                    Log.d("BSAAAAA", ":" + healthCalculationModel.getResult());
                    textView_bsa.setText(healthCalculationModel.getResult());

                } else if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Failed in BSW", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("Failed in BSW", "Response =" + responseString);
                }
            }

            @Override
            public void onFailure(Call<HealthCalculationModel> call, Throwable t) {

            }
        });

    }

    private void getBFPData(int userAccountId) {


        HelathCalculateApi bfpapi = UserAccountclient.getClient().create(HelathCalculateApi.class);



        Call<HealthCalculationModel> call = bfpapi.getbfp(userAccountId);

        call.enqueue(new Callback<HealthCalculationModel>() {
            @Override
            public void onResponse(Call<HealthCalculationModel> call, Response<HealthCalculationModel> response) {
                if (response != null && response.code() == 200) {
                    String responseString = "Response code :" + response.code();

                    Log.d("TAG", "Response =" + responseString);
//
                    healthCalculationModel = response.body();
                    Toast.makeText(getContext(), "" + healthCalculationModel.getResult(), Toast.LENGTH_SHORT).show();

                    Log.d("BFPPPPP", ":" + healthCalculationModel.getResult());
                    SharedPreference.saveSharedSetting(getActivity(),"bfp",healthCalculationModel.getResult() );
                    textView_bfp.setText(healthCalculationModel.getResult());

                } else if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Failed in BFP", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("Failed in BFP", "Response =" + responseString);
                }
            }

            @Override
            public void onFailure(Call<HealthCalculationModel> call, Throwable t) {

            }
        });

    }



    private void customAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialogs, null);
        Button btn_okay = (Button) mView.findViewById(R.id.buttonOk);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrimaryAnalysisDietACtivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void setFlipperImage(int res) {

        ImageView image = new ImageView(getContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

    }
}
