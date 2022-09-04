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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthifyapp.SharedPreferences.SharedPreference;
import com.example.healthifyapp.adapter.AddItemAdapters;
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

public class DinnerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static int kcalorie;
    TextView datetext1, textViewdiettype, textViewquantity, timetext;
    AutoCompleteTextView dinner;
    TextView kcalstext;
    Button btnsubmit;
    int minteger = 1;
    ArrayAdapter<String> arrayAdapter;
    //ArrayList<DietDataModel> dietDataModelArrayList;
    AddItemAdapters addItemAdapters;
    String mobilno = "";
    int UserId = 0;
    ArrayList<FoodDietModel> foodList;
    ArrayList<String> food;
    String dinnerr;
    HashMap<String, String> foodWithKcal;
    TextView display;
    ImageView increaseBtn, decreaseBtn;
    String Time, date;
    String dietType;
    static int originalKcal = 1;
    int tempQuantity = 1, userAccountId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        // display = (TextView) findViewById(R.id.quantity);
        textViewdiettype = findViewById(R.id.dinnerr);
        timetext = findViewById(R.id.selecttime);
        kcalstext = findViewById(R.id.kcaltextview);
        increaseBtn = findViewById(R.id.increase);
        decreaseBtn = findViewById(R.id.decrease);


        textViewquantity = findViewById(R.id.quantity);
        dinner = findViewById(R.id.dinnerauto);
        btnsubmit = findViewById(R.id.add_dinner);
        mobilno = SharedPreference.readSharedSetting(this, "mobile_no", "");
        //breakfast = (AutoCompleteTextView) findViewById(R.id.addbreakfast);

        datetext1 = findViewById(R.id.selectdate);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        foodList = new ArrayList<FoodDietModel>();
        food = new ArrayList<>();
        foodWithKcal = new HashMap<>();
        dietType = textViewdiettype.getText().toString();
        userAccountId = SharedPreference.readSharedSetting(this, "userAccountId", 0);

        getAllFoodItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, food);
        // breakfast = (AutoCompleteTextView) findViewById(R.id.addbreakfast);
        //  breakfast.setThreshold(1);
        dinner.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        // breakFastfood = breakfast.getText().toString();

        //getting the breakfast which is selected
        dinner.setOnItemClickListener(this);
       // originalKcal = Integer.parseInt(kcalstext.getText().toString());
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


                kcalstext.setText(kcalorie);
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


                    kcalstext.setText("" + kcalorie);
                    display(tempQuantity);

                } else {
                    decreaseBtn.setVisibility(View.GONE);
                    display(tempQuantity);
                }
            }
        });




        LinearLayout linearLayout1 = findViewById(R.id.layout_breakfast);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostBreakfast(
                        userAccountId, textViewdiettype.getText().toString()
                        , textViewquantity.getText().toString()
                        , dinnerr
                        , datetext1.getText().toString()
                        , timetext.getText().toString()
                        , kcalstext.getText().toString());
                Toast.makeText(DinnerActivity.this, "Add diet Successfully" + kcalstext.getText().toString(), Toast.LENGTH_SHORT).show(); // Due to no response from get from api but data is stored.. 200 code is returned and check log for response

            }
        });


        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TimePickerDialog mTimePicker = new TimePickerDialog(DinnerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Time = (hour + ":" + minute);
                        timetext.setText(Time);
                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        datetext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(DinnerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = (dayOfMonth + "-"
                                + (month + 1) + "-" + year);

                        datetext1.setText(date);

                    }
                }, year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                dialog.show();


            }
        });


    }


    private void PostBreakfast(int userAccountId, String dietType, String quantity, String item, String date, String dietTime, String kCal) {
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://easywaygst.theumangsociety.org")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        DietApi dietApi=retrofit.create(DietApi.class);

        DietApi dietApi = UserAccountclient.getClient().create(DietApi.class);

        DietDataModel dietAnalysisDetailList = new DietDataModel(quantity, item, kCal);
        ArrayList<DietDataModel> dietAnalysisDetailList2 = new ArrayList<DietDataModel>();
        dietAnalysisDetailList2.add(dietAnalysisDetailList);
        DietDataModel.Root dietDataModel = new DietDataModel.Root(userAccountId, dietType, dietTime, dietType, date, dietAnalysisDetailList2);
        Gson g1 = new Gson();
        Log.v("Sending data:", g1.toJson(dietDataModel));
        Call<DietDataModel.Root> call = dietApi.createPost(dietDataModel);

        call.enqueue(new Callback<DietDataModel.Root>() {
            @Override
            public void onResponse(Call<DietDataModel.Root> call, Response<DietDataModel.Root> response) {

                Toast.makeText(DinnerActivity.this, "Add diet", Toast.LENGTH_SHORT).show();

                if (response.code() == 200) {
                    String responseString = "Response code :" + response.code();
                    Toast.makeText(DinnerActivity.this, "Add diet Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Response =" + responseString);
                    Gson gson = new Gson();
                    String s1 = gson.toJson(response.body());
                    Log.e("Response", s1);

                } else if (!response.isSuccessful()) {

                    Toast.makeText(DinnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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



  /*  private void getItemDiet(String s) {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://easywaygst.theumangsociety.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DietApi dietApi=retrofit.create(DietApi.class);

        Call<ArrayList<DietItemDataModel>> diet=dietApi.getdietitemDataModel();
        diet.enqueue(new Callback<ArrayList<DietItemDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DietItemDataModel>> call, Response<ArrayList<DietItemDataModel>> response) {


                List<DietItemDataModel> itemDataModels=response.body();


                String itemName=itemDataModels.get(0).getItemName();
                String kCal=itemDataModels.get(1).getkCal();
                addbreakfastedittext.clearListSelection();

                addbreakfastedittext.setText(itemName);
                kcalstext.setText(kCal);

               // arrayAdapter= new ArrayAdapter<String>(Breakfast.this, android.R.layout.simple_list_item_activated_1, Collections.singletonList(itemName));
                addbreakfastedittext.setAdapter(arrayAdapter);
                addbreakfastedittext.setThreshold(1);


                Log.e("TAG","Response ="+response);
            }

            @Override
            public void onFailure(Call<ArrayList<DietItemDataModel>> call, Throwable t) {

            }


        });

    }*/


    private void display(int number) {

        textViewquantity.setText("" + number);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        dinnerr = adapterView.getItemAtPosition(position).toString();
        Log.d("breakfast", ":" + dinnerr);
        for (Map.Entry<String, String> breakfast : foodWithKcal.entrySet()) {
            if (dinnerr.equalsIgnoreCase(breakfast.getKey())) {
                kcalstext.setText(breakfast.getValue());
                Log.d("found", ":" + kcalstext.getText().toString());
                Log.d("foundBBBB", ":" + dinnerr);
                originalKcal =Integer.parseInt( kcalstext.getText().toString());
            }
        }
        // create Toast with user selected value
        Toast.makeText(DinnerActivity.this, "Selected Item is: \t" + dinnerr, Toast.LENGTH_LONG).show();
        SharedPreference.saveSharedSetting(DinnerActivity.this, "dinner", dinnerr);
    }
}