package com.example.thanh.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.CourseUserAdapter;
import com.example.thanh.model.CourseUser;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_signed_gets extends NavActivity {
    private ApiService apiService;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.courses_user_signed_gets;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_yourcourses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        getAllCourses();
    }

    private void getAllCourses() {
        Call<List<CourseUser>> call = apiService.getAllCoursesUser(1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    String jsonString = new Gson().toJson(response.body());
                    List<CourseUser> courses = (List<CourseUser>) response.body();
                    Log.d("RES", jsonString);
                    Log.d("API", "Success");
                    displayCourses(courses);
                } else {
                    Log.w("API", "Failed");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Failure:", "Failed");
            }
        });
    }

    private void displayCourses(List<CourseUser> courses) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CourseUserAdapter adapter = new CourseUserAdapter(courses);
        recyclerView.setAdapter(adapter);
    }
}