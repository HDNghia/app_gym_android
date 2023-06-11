package com.example.thanh.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.CourseAdapter;
import com.example.thanh.model.Course;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_gets extends NavActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_user_gets;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_home;
    }

    private ApiService apiService;

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
        Call<List<Course>> call = apiService.getAllCourses();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    List<Course> courses = (List<Course>) response.body();

                    String jsonString = new Gson().toJson(courses);
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

    private void displayCourses(List<Course> courses) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CourseAdapter adapter = new CourseAdapter(courses);
        recyclerView.setAdapter(adapter);
    }
}