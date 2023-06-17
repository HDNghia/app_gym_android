package com.example.thanh.activity;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thanh.R;
import com.example.thanh.model.*;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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
        setContentView(R.layout.course_user_calendar);
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<CourseScheduleDetail>> call = apiService.getCourseScheduleByUserId(1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                if (response.isSuccessful()) {
                    List<CourseScheduleDetail> course = (List<CourseScheduleDetail>) response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES Schedule", jsonString);
                    Log.d("API schedule", "Success");
                    displaySchedule(course);
                } else {
                    Log.d("API schedule", "Fail");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.d("Error: ", errorMessage);
            }
        });
    }

    private void displaySchedule(List<CourseScheduleDetail> courseScheduleDetails) {
        CalendarView calendarView = findViewById(R.id.calendarView);
        TextView dayNot = new TextView(course_user_calendar.this);
        dayNot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dayNot.setGravity(Gravity.CENTER);
        dayNot.setText("There is no class schedule for this day");

        LinearLayout courseLayout = findViewById(R.id.courseLayout);

        courseLayout.addView(dayNot);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                courseLayout.removeAllViews();
                TextView dayNotClone = new TextView(course_user_calendar.this);
                dayNotClone.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                dayNotClone.setGravity(Gravity.CENTER);
                dayNotClone.setText("There is no class schedule for this day");
                courseLayout.addView(dayNotClone);
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                selectedDate.set(Calendar.HOUR_OF_DAY, 0);
                selectedDate.set(Calendar.MINUTE, 0);
                selectedDate.set(Calendar.SECOND, 0);
                selectedDate.set(Calendar.MILLISECOND, 0);

                int courseScheduleCount = 0;
                for (CourseScheduleDetail courseScheduleDetail : courseScheduleDetails) {

                    List<CourseSchedule> courseSchedules = courseScheduleDetail.getCourseSchedule();
                    Course course = courseScheduleDetail.getCourseInfo();

                    for (CourseSchedule courseSchedule : courseSchedules) {

                        long fromDateTime = courseSchedule.getFromDateTime();
                        long toDateTime = courseSchedule.getToDateTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                        // Convert fromDateTime to string representation
                        String fromDate = dateFormat.format(new Date(fromDateTime));
                        String fromTime = timeFormat.format(new Date(fromDateTime));

                        // Convert toDateTime to string representation
                        String toDate = dateFormat.format(new Date(toDateTime));
                        String toTime = timeFormat.format(new Date(toDateTime));

                        // Convert selected to string
//                        String selectedDateString = dateFormat.format(new Date(selectedDateTime));
//                        Log.d("From date:" ,fromDate + ' ' + fromTime);
//                        Log.d("selected:", selectedDateString);
//                        Log.d("To date:" ,toDate + ' ' + toTime);

                        // Get the start and end dates of the course schedule without the hours and minutes
                        Calendar courseStartDate = Calendar.getInstance();
                        courseStartDate.setTimeInMillis(fromDateTime);
                        courseStartDate.set(Calendar.HOUR_OF_DAY, 0);
                        courseStartDate.set(Calendar.MINUTE, 0);
                        courseStartDate.set(Calendar.SECOND, 0);
                        courseStartDate.set(Calendar.MILLISECOND, 0);

                        Calendar courseEndDate = Calendar.getInstance();
                        courseEndDate.setTimeInMillis(toDateTime);
                        courseEndDate.set(Calendar.HOUR_OF_DAY, 0);
                        courseEndDate.set(Calendar.MINUTE, 0);
                        courseEndDate.set(Calendar.SECOND, 0);
                        courseEndDate.set(Calendar.MILLISECOND, 0);

                        // Check if the course schedule falls within the selected calendar view
                        if (selectedDate.equals(courseStartDate) && selectedDate.equals(courseEndDate)  ) {
                            courseScheduleCount++;
                            String title = course.getTitle();
                            String location = course.getLocation();

                            dayNot.setVisibility(View.GONE);

                            // Create a new LinearLayout
                            LinearLayout linearLayout = new LinearLayout(course_user_calendar.this);
                            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.setOrientation(LinearLayout.VERTICAL);

                            // Create a text view to display the course information
                            TextView courseTextView = new TextView(course_user_calendar.this);
                            courseTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            courseTextView.setGravity(Gravity.CENTER);
                            courseTextView.setText("Title: " + title + "\n\nLocation: " + location + "\n\nTime: " + fromTime + " to " + toTime + "\n");

                            // Create a TextView for the number of course schedules
                            TextView courseScheduleCountTextView = new TextView(course_user_calendar.this);
                            courseScheduleCountTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            courseScheduleCountTextView.setGravity(Gravity.CENTER);
                            courseScheduleCountTextView.setText(String.valueOf(courseScheduleCount) + "\n");
                            courseScheduleCountTextView.setTextSize(20);
                            courseScheduleCountTextView.setTextColor(getResources().getColor(R.color.green_700));

                            // Add the TextViews to the LinearLayout
                            linearLayout.addView(courseScheduleCountTextView);
                            linearLayout.addView(courseTextView);
                            courseLayout.removeView(dayNotClone);
                            courseLayout.addView(linearLayout);
                        }
                    }
                }
            }
        });
    }
}