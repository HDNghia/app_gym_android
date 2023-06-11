package com.example.thanh.activity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thanh.R;
import com.example.thanh.model.*;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.sql.Date;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_calendar extends NavActivity {

    private ApiService apiService;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_user_calendar;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_yourcourses;
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

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<CourseScheduleDetail> call = apiService.getCourseSchedule(courseId);
        call.enqueue(new Callback<CourseScheduleDetail>() {
            @Override
            public void onResponse(retrofit2.Call<CourseScheduleDetail> call, Response<CourseScheduleDetail> response) {
                if (response.isSuccessful()) {
                    CourseScheduleDetail course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES Schedule", jsonString);
                    Log.d("API schedule", "Success");
                    displaySchedule(course);
                } else {
                    Log.d("API schedule", "Fail");
                }
            }

            @Override
            public void onFailure(Call<CourseScheduleDetail> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.d("Error: ", errorMessage);
            }
        });
    }

    private void displaySchedule(CourseScheduleDetail courseSchedule) {
        CalendarView calendarView = findViewById(R.id.calendarView);
        TextView dayNote = findViewById(R.id.dayNote);
        TextView dayNot = findViewById(R.id.dayNot);
        TextView dayName = findViewById(R.id.dayName);
        Calendar calendar = Calendar.getInstance();
        long nowInMillis = calendar.getTimeInMillis();
        if(nowInMillis >= courseSchedule.getCourseSchedule().getFromDateTime() * 1000 && nowInMillis <= courseSchedule.getCourseSchedule().getToDateTime() * 1000){
            dayNot.setVisibility(View.GONE);
            dayNote.setVisibility(View.VISIBLE);
        } else {
            dayNot.setVisibility(View.VISIBLE);
            dayNote.setVisibility(View.GONE);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                dayNote.setVisibility(View.GONE);
                dayName.setVisibility(View.GONE);
                dayNot.setVisibility(View.VISIBLE);
                // Lấy giá trị ngày được chọn
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

//                long from = courseSchedule.getFromDateTime();
//                long to = courseSchedule.getToDateTime();
//                Date dateFrom = new Date(from * 1000);
//                Date dateTo = new Date(to * 1000);

                // Tạo định dạng cho đối tượng Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                // Chuyển đổi đối tượng Date thành chuỗi ngày tháng
//                String fromDate = dateFormat.format(dateFrom);
//                String toDate = dateFormat.format(dateTo);
//                Log.d("from", fromDate);
//                Log.d("to", toDate);

                // Đặt giá trị giờ, phút và giây thành tối đa
                selectedDate.set(Calendar.HOUR_OF_DAY, 23);
                selectedDate.set(Calendar.MINUTE, 59);
                selectedDate.set(Calendar.SECOND, 59);
                selectedDate.set(Calendar.MILLISECOND, 999);

                // Chuyển đổi ngày sang định dạng long
                long selectedDateTime = selectedDate.getTimeInMillis();
                Date dateNow = new Date(selectedDateTime);
                String nowDate = dateFormat.format(dateNow);
//                Log.d("now", nowDate);

                // So sánh với từng CourseSchedule và hiển thị ghi chú tương ứng
                if (selectedDateTime >= courseSchedule.getCourseSchedule().getFromDateTime() * 1000 && selectedDateTime <= courseSchedule.getCourseSchedule().getToDateTime() * 1000) {
                    String note = courseSchedule.getCourseSchedule().getNote();
                    String courseName = courseSchedule.getCourseInfo().getTitle();
                    // Hiển thị ghi chú
                    Log.d("Show", "Success");
                    dayNot.setVisibility(View.GONE);
                    dayNote.setVisibility(View.VISIBLE);
                    dayNote.setText(note);
                    dayName.setVisibility(View.VISIBLE);
                    dayName.setText(courseName);
                }
            }
        });
    }
}