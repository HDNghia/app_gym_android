package com.example.thanh.activity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.CourseScheduleAdapter;
import com.example.thanh.model.*;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_trainer_calendar extends NavActivity {

    private ApiService apiService;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_trainer_calendar;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_yourcourses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int trainerID = getIntent().getIntExtra("_id", -1);
        Log.d("TrainerID:", String.valueOf(trainerID));
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<CourseScheduleCalendar>> call = apiService.getCourseScheduleCalendarOfTrainer(trainerID);
        call.enqueue(new Callback<List<CourseScheduleCalendar>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CourseScheduleCalendar>> call, Response<List<CourseScheduleCalendar>> response) {
                if (response.isSuccessful()) {
                    List<CourseScheduleCalendar> course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES Schedule", jsonString);
                    Log.d("API schedule", "Success");
                    displaySchedule(course);
                } else {
                    Log.d("API schedule", "Fail");
                }
            }

            @Override
            public void onFailure(Call<List<CourseScheduleCalendar>> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.d("Error: ", errorMessage);
            }
        });
    }

    private void displaySchedule(List<CourseScheduleCalendar> courseSchedule) {
        CalendarView calendarView = findViewById(R.id.calendarView);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourseSchedulesCalendar);
        List<CourseSchedule> allCourseSchedules = new ArrayList<>();
        for (CourseScheduleCalendar scheduleCalendar : courseSchedule) {
            List<CourseSchedule> scheduleInfo = scheduleCalendar.getCourseScheduleInfo();
            allCourseSchedules.addAll(scheduleInfo);
        }
        CourseScheduleAdapter adapter = new CourseScheduleAdapter(allCourseSchedules);

        // Thiết lập LayoutManager cho RecyclerView (LinearLayoutManager, GridLayoutManager, etc.)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Thiết lập Adapter cho RecyclerView
        recyclerView.setAdapter(adapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Lọc danh sách lịch học dựa trên ngày được chọn (year, month, dayOfMonth)
                List<CourseSchedule> filteredSchedule = new ArrayList<>();
                for (CourseScheduleCalendar scheduleCalendar : courseSchedule) {
                    List<CourseSchedule> scheduleInfo = scheduleCalendar.getCourseScheduleInfo();
                    for (CourseSchedule schedule : scheduleInfo) {
                        // Kiểm tra xem ngày bắt đầu của lịch học có khớp với ngày được chọn không
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(schedule.getFromDateTime());
                        if (calendar.get(Calendar.YEAR) == year
                                && calendar.get(Calendar.MONTH) == month
                                && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                            filteredSchedule.add(schedule);
                        }
                    }
                }

                // Cập nhật dữ liệu trong CourseScheduleAdapter
                adapter.setCourseSchedules(filteredSchedule);
            }
        });


    }
}