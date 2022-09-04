package com.example.healthifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthifyapp.SharedPreferences.SharedPreference;
import com.example.healthifyapp.api.DietApi;
import com.example.healthifyapp.apiclient.UserAccountclient;
import com.example.healthifyapp.model.DietDataModel;
import com.example.healthifyapp.model.FoodDietModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LunchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView datetext, textViewdiettype, textViewquantity, timetext;
    AutoCompleteTextView lunch;
    TextView    kcalstext;
    Button btnsubmit;
    int minteger = 1, userAccountId=0;
    ArrayList<FoodDietModel> foodList;
    ArrayList<String> food;
    String lunchh;
    HashMap<String, String> foodWithKcal;
    static int kcalorie;

    String mobilno = "";
    ImageView increaseBtn, decreaseBtn;
    String Time, date;
    String dietType;
    int minusinteger=0;
    static int originalKcal = 1;
    int tempQuantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        textViewdiettype = findViewById(R.id.lunchh);
        timetext = findViewById(R.id.selecttime);
        kcalstext = findViewById(R.id.kcal);
        increaseBtn = findViewById(R.id.increase);
        decreaseBtn = findViewById(R.id.decrease);

        datetext = findViewById(R.id.selectdate);

        textViewquantity = findViewById(R.id.quantity);
        lunch = findViewById(R.id.addlunch);
        btnsubmit = findViewById(R.id.breakbtnadd);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        mobilno = SharedPreference.readSharedSetting(this, "mobile_no", "");
        foodList = new ArrayList<FoodDietModel>();
        food = new ArrayList<>();
        foodWithKcal = new HashMap<>();
        dietType = textViewdiettype.getText().toString();
        userAccountId = SharedPreference.readSharedSetting(this, "userAccountId", 0);

        getAllFoodItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, food);

        //  breakfast.setThreshold(1);
        lunch.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        // breakFastfood = breakfast.getText().toString();

        //getting the breakfast which is selected
        lunch.setOnItemClickListener(this);
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempQuantity = Integer.parseInt(textViewquantity.getText().toString());
                tempQuantity += 1;
                if (tempQuantity <= 1) {
                    decreaseBtn.setVisibility(View.VISIBLE);

                }
                kcalorie = Integer.parseInt(kcalstext.getText().toString());
                kcalorie =  originalKcal * tempQuantity;


                kcalstext.setText(String.valueOf(kcalorie));
                display(tempQuantity);
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempQuantity = Integer.parseInt(textViewquantity.getText().toString());
                tempQuantity = tempQuantity - 1;
                if (tempQuantity >0) {
                    kcalorie = Integer.parseInt(kcalstext.getText().toString());


                    kcalorie = kcalorie - originalKcal;


                    kcalstext.setText(String.valueOf( kcalorie));
                    display(tempQuantity);

                } else {
                    decreaseBtn.setVisibility(View.GONE);
                    display(tempQuantity);
                }
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostBreakfast(
                        userAccountId, textViewdiettype.getText().toString()
                        , textViewquantity.getText().toString()
                        , lunchh
                        , datetext.getText().toString()
                        , timetext.getText().toString()
                        , kcalstext.getText().toString());
                Toast.makeText(LunchActivity.this, "Add diet Successfully", Toast.LENGTH_SHORT).show(); // Due to no response from get from api but data is stored.. 200 code is returned and check log for response

            }
        });

        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TimePickerDialog mTimePicker=new TimePickerDialog(LunchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                         Time=( hour+ ":" + minute);
                        timetext.setText(Time);
                    }
                },hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(LunchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                         date = (dayOfMonth + "-" + (month + 1) + "-" + year);
                        datetext.setText(date);

                    }
                }, year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
              //  dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
                //dialog.show();

            }
        });
    }

    private void PostBreakfast(int userAccountId, String dietType, String quantity, String item, String date, String dietTime, String kCal) {
//

        DietApi dietApi = UserAccountclient.getClient().create(DietApi.class);

        DietDataModel dietAnalysisDetailList = new DietDataModel(quantity, item, kCal);
        ArrayList<DietDataModel> dietAnalysisDetailList2 = new ArrayList<DietDataModel>();
        dietAnalysisDetailList2.add(dietAnalysisDetailList);
        DietDataModel.Root dietDataModel = new DietDataModel.Root(userAccountId, "lunch", dietTime, dietType, date, dietAnalysisDetailList2);
        Gson g1 = new Gson();
        Log.v("Sending data:", g1.toJson(dietDataModel));
        Call<DietDataModel.Root> call = dietApi.createPost(dietDataModel);

        call.enqueue(new Callback<DietDataModel.Root>() {
            @Override
            public void onResponse(Call<DietDataModel.Root> call, Response<DietDataModel.Root> response) {

                Toast.makeText(LunchActivity.this, "Add diet", Toast.LENGTH_SHORT).show();

                if (response.code() == 200) {
                    String responseString = "Response code :" + response.code();
                    Toast.makeText(LunchActivity.this, "Add diet Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Response =" + responseString);
                    Gson gson = new Gson();
                    String s1 = gson.toJson(response.body());
                    Log.e("Response", s1);

                } else if (!response.isSuccessful()) {

                    Toast.makeText(LunchActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    String responseString = "Response code :" + response.code();
                    Log.e("TAG", "Response =" + responseString);

                }
            }

            @Override
            public void onFailure(Call<DietDataModel.Root> call, Throwable t) {

            }


        });

    }

    private void getAllFoodItems() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://easywaygst.theumangsociety.org")
                // on below line we are calling add
                // Converter factory as Gson converter factory.
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();

        // below line is to create an instance for our retrofit api class.
        DietApi retrofitAPI = retrofit.create(DietApi.class);

        // on below line we are calling a method to get all the cities from API.
        Call<ArrayList<FoodDietModel>> call = retrofitAPI.getFoodItemDataModel();

        call.enqueue(new Callback<ArrayList<FoodDietModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodDietModel>> call, Response<ArrayList<FoodDietModel>> response) {
                // inside on response method we are checking
                // if the response is success or not.
                if (response.isSuccessful()) {
                    // below line is to add our data from api to our array list.
                    foodList = response.body();
                    // below line we are running a loop to add data to our adapter class.
                    for (int i = 0; i < foodList.size(); i++) {
                        Log.d("foodList", ":" + foodList.get(i).getItemName());
                        food.add(foodList.get(i).getItemName());
                        Log.d("foodName", ":" + food.get(i));

                        foodWithKcal.put(foodList.get(i).getItemName(), foodList.get(i).getKCal());
                        Log.d("KCAL", ":" + foodList.get(i).getKCal());
                        Log.d("KCAL", ":" + foodWithKcal.get(foodList.get(i).getItemName()));

                    }
                    for (Map.Entry<String, String> breakfast : foodWithKcal.entrySet()) {
                        Log.d("KCAL", ":" + breakfast.getValue());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FoodDietModel>> call, Throwable t) {

            }
        });
    }

    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);

    }public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
    }

    private void display(int number) {

        textViewquantity.setText("" + number);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        lunchh = adapterView.getItemAtPosition(position).toString();
        Log.d("breakfast", ":" + lunchh);
        for (Map.Entry<String, String> breakfast : foodWithKcal.entrySet()) {
            if (lunchh.equalsIgnoreCase(breakfast.getKey())) {
                kcalstext.setText(breakfast.getValue());
                originalKcal =Integer.parseInt( breakfast.getValue());
                Log.d("found", ":" + kcalstext.getText().toString());
                Log.d("foundBBBB", ":" + lunchh);
            }
        }
        // create Toast with user selected value
        Toast.makeText(LunchActivity.this, "Selected Item is: \t" + lunchh, Toast.LENGTH_LONG).show();
        SharedPreference.saveSharedSetting(LunchActivity.this, "lunch", lunchh);
    }
}