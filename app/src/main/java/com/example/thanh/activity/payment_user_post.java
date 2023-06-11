package com.example.thanh.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thanh.R;
import com.example.thanh.model.CourseDetail;
import com.example.thanh.model.CourseRegister;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class payment_user_post extends NavActivity {

    private ApiService apiService;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.payment_user_post;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_courses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int courseId = getIntent().getIntExtra("_id", -1);
        Button btnPay = findViewById(R.id.btnPay);

        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                post(courseId);
            }
        });

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<CourseDetail> call = apiService.getCourseById(courseId);
        call.enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(Call<CourseDetail> call, Response<CourseDetail> response) {
                if (response.isSuccessful()) {
                    CourseDetail course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES", jsonString);
                    Log.d("API", "Success");
                    displayPayment(course);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void displayPayment(CourseDetail course) {
        TextView textViewTitlePay = findViewById(R.id.textViewTitlePay);
        TextView textViewPricePay = findViewById(R.id.textViewPricePay);
        TextView textViewPackageValue = findViewById(R.id.textViewPackageValue);
        TextView textViewTotalValue = findViewById(R.id.textViewTotalValue);

        textViewTitlePay.setText(course.getCourses().getTitle());
        textViewPricePay.setText(String.valueOf(course.getCourses().getFee()));
        textViewPackageValue.setText(String.valueOf(course.getCourses().getFee()));
        textViewTotalValue.setText(String.valueOf(course.getCourses().getFee()));
    }

    public void post(int courseId) {
        // Create a new instance of CourseUser or use the appropriate model class
        CourseRegister courseReg = new CourseRegister(courseId, 1, new Date().getTime(), true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("courseId", courseReg.getCourseId());
            jsonObj.put("userId", courseReg.getUserId());
            jsonObj.put("registerDateTime", courseReg.getRegisterDateTime());
            jsonObj.put("status", courseReg.getStatus());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String jsonString = new Gson().toJson(jsonObj);
        Log.d("Create", "Success");
        Log.d("Create", jsonString);
        // Call the appropriate method from the ApiService interface to make the POST request
        Call<CourseRegister> call = apiService.createCourseUser(courseReg);
        Log.d("Call", "Success");
        // Handle the response in the onResponse and onFailure methods of the Callback
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Log.d("POST", "SUCCESS");
                } else {
                    Log.d("POST", "FAIL");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Failure POST", "FAIL");
                Log.d("POST Fail", t.getMessage());
            }
        });
    }
}