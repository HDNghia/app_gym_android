package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.R;
import com.example.thanh.model.*;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_get extends NavActivity {
    private ApiService apiService;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_user_get;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int courseId = getIntent().getIntExtra("_id", -1);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        Button btn1 = findViewById(R.id.btnReg1);
        Button btn2 = findViewById(R.id.btnReg2);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_user_get.this, payment_user_post.class);
                intent.putExtra("_id", courseId);
                startActivity(intent);
            }
        };

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);

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
                    displayCourse(course);
                } else {
                    Log.d("API", "Fail");
                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                Log.d("FAILURE", "FAIL");
            }
        });
    }

    private void displayCourse(CourseDetail course) {
        ImageView imageViewAva = findViewById(R.id.imageViewAva);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewRole = findViewById(R.id.textViewRole);
        TextView textViewAuthor = findViewById(R.id.textViewAuthor);
        TextView textViewUpdated = findViewById(R.id.textViewUpdated);
        //TextView textViewFeeInfo = findViewById(R.id.textViewFeeInfo);
        Button btnReg1 = findViewById(R.id.btnReg1);
        TextView textViewDescInfo = findViewById(R.id.textViewDescInfo);

        TextView textViewInsName = findViewById(R.id.textViewInsName);
        TextView textViewInsRole = findViewById(R.id.textViewInsRole);
        ImageView imageViewInsAva = findViewById(R.id.imageViewInsAva);
        TextView textViewInsInfo = findViewById(R.id.textViewInsInfo);

        TextView textViewPrice = findViewById(R.id.textViewPrice);

        imageViewAva.setImageResource(R.drawable.course);
        textViewName.setText(course.getCourses().getTitle());
        textViewRole.setText("Fitness Coach");
        textViewAuthor.setText(course.getTrainerinfo().getFirstName() + course.getTrainerinfo().getLastName());
        textViewUpdated.setText("Last Updated: " + String.valueOf(course.getCourses().getLastModifyDate()));

        btnReg1.setText(String.valueOf(course.getCourses().getFee()) + ".00 $");
        textViewPrice.setText(String.valueOf(course.getCourses().getFee()) + ".00 $");

        textViewDescInfo.setText(course.getCourses().getDescription());

        textViewInsName.setText(course.getTrainerinfo().getFirstName() + course.getTrainerinfo().getLastName());
        textViewInsRole.setText("Fitness Coach");
        imageViewInsAva.setImageResource(R.drawable.ins);
        textViewInsInfo.setText("It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
    }
}