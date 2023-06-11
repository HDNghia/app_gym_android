package com.example.thanh.activity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.R;
import com.example.thanh.model.CourseDetail;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_user_signed_get extends NavActivity {

    private ApiService apiService;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.course_user_signed_get;
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

        ImageButton btnCalendar = findViewById(R.id.btnCal);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_user_signed_get.this, course_user_calendar.class);
                intent.putExtra("_id", courseId);
                startActivity(intent);
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
                    Log.d("RES signed_get: ", jsonString);
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
        TextView textViewFeeInfo = findViewById(R.id.textViewFeeInfo);

        TextView textViewDescInfo = findViewById(R.id.textViewDescInfo);

        TextView textViewServiceName = findViewById(R.id.serviceName);
        TextView textViewServiceDesc = findViewById(R.id.serviceDesc);

        TextView textViewDateStart = findViewById(R.id.dateStart);

        TextView textViewCapacity = findViewById(R.id.capacityValue);

        TextView textViewLocation = findViewById(R.id.locationValue);

        imageViewAva.setImageResource(R.drawable.course);
        textViewName.setText(course.getCourses().getTitle());
        textViewRole.setText("Fitness Coach");
        textViewAuthor.setText(course.getTrainerinfo().getLastName() + course.getTrainerinfo().getFirstName());
        textViewUpdated.setText("Last Updated: " + String.valueOf(course.getCourses().getLastModifyDate()));
        textViewFeeInfo.setText(String.valueOf(course.getCourses().getFee()) + "đ");

        textViewDescInfo.setText(course.getCourses().getDescription());

        textViewServiceName.setText(course.getServiceinfo().getName());
        textViewServiceDesc.setText(course.getServiceinfo().getDescription());

        long from = course.getCourses().getStartDate();
        long to = course.getCourses().getEndDate();
        Date dateFrom = new Date(from * 1000);
        Date dateTo = new Date(to * 1000);

        // Tạo định dạng cho đối tượng Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Chuyển đổi đối tượng Date thành chuỗi ngày tháng
        String fromDate = dateFormat.format(dateFrom);
        String toDate = dateFormat.format(dateTo);
        Log.d("debug", "5");
        textViewDateStart.setText("Từ " + fromDate + " đến " + toDate );
        Log.d("debug", "6");
        textViewCapacity.setText(String.valueOf(course.getCourses().getCapacity()));

        textViewLocation.setText(course.getCourses().getLocation());
    }
}