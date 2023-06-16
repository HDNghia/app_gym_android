package com.example.thanh.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.*;
import com.example.thanh.model.Course;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_trainer_get extends NavActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_trainer_get;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_trainercourse;
    }
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_trainer_get);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Log.d("Tag","Heloo");
        getCoursesByTrainerID(2);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed(); // Navigate back to the previous screen
            }
        });
//        ImageButton courseCalendarView = findViewById(R.id.courseCalendarView);
//        courseCalendarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(course_trainer_get.this, course_calendar_view.class);
//                startActivity(intent);
//            }
//        });

        FloatingActionButton createCourseButton = findViewById(R.id.createCourseButton);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateCourseActivity();
            }
        });


    }
    private List<Course> filterCoursesByStatus(List<Course> courses, int status) {
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getStatus() == status) {
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    private void getCoursesByTrainerID(int trainerID) {
        Call<List<Course>> call = apiService.getCoursesByTrainerID(trainerID);
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    List<Course> courses = response.body();

                    String jsonString = new Gson().toJson(courses);
                    Log.d("RES", jsonString);

                    Log.d("API", "Success");
                    displayCourses(courses);
                } else {
                    Log.w("API", "Failed");
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d("Failure:", "Failed");
            }
        });
    }

    public void openCreateCourseActivity() {
        Intent intent = new Intent(course_trainer_get.this, create_course_trainer.class);
        startActivity(intent);
    }


    private void displayCourses(List<Course> courses) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CourseTrainerAdapter adapter = new CourseTrainerAdapter(courses);
        List<Course> filteredCourses = filterCoursesByStatus(courses, 0);
        adapter.setCourses(filteredCourses);
        recyclerView.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                if (selectedTabPosition == 0) {
                    // Animation khi danh sách khóa học thay đổi từ trạng thái 1 sang trạng thái 2
                    Animation animation = AnimationUtils.loadAnimation(course_trainer_get.this, R.anim.anim_fade_in);
                    recyclerView.startAnimation(animation);
                } else if (selectedTabPosition == 1) {
                    // Animation khi danh sách khóa học thay đổi từ trạng thái 2 sang trạng thái 1
                    Animation animation = AnimationUtils.loadAnimation(course_trainer_get.this, R.anim.anim_fade_in);
                    recyclerView.startAnimation(animation);
                } else if (selectedTabPosition == 2) {
                    // Animation khi danh sách khóa học thay đổi từ trạng thái 1 hoặc 2 sang trạng thái 3
                    Animation animation = AnimationUtils.loadAnimation(course_trainer_get.this, R.anim.anim_fade_in);
                    recyclerView.startAnimation(animation);
                }
                // Dựa trên tab được chọn, lọc danh sách khóa học theo trạng thái và cập nhật adapter
                List<Course> filteredCourses = filterCoursesByStatus(courses, selectedTabPosition);
                adapter.setCourses(filteredCourses);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }
        });
    }
}