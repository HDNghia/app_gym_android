package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.CourseUserAdapter;
import com.example.thanh.model.CourseUser;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_signed_gets extends NavActivity {
    private ApiService apiService;
    private DatabaseHelper databaseHelper;
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
        databaseHelper = new DatabaseHelper(this);
        List<User> ul = databaseHelper.getAllUser();
        super.onCreate(savedInstanceState);
        int userId = ul.get(0).get_id();
//        Toast.makeText(course_user_signed_gets.this, "Fail: " +userId, Toast.LENGTH_SHORT).show();
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        ImageButton btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_user_signed_gets.this, course_user_calendar.class);
                intent.putExtra("_id", userId );
                startActivity(intent);
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        getAllCourses(userId);
    }

    private void getAllCourses(int userId) {
        Call<List<CourseUser>> call = apiService.getAllCoursesUser(userId);
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