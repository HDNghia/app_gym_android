package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.Course;
import com.example.thanh.model.CourseDetail;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_trainer_details extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_trainer_details);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        int courseId = getIntent().getIntExtra("id", -1);
        Log.d("CourseId", String.valueOf(courseId));
        Button deleteCourse = findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), course_delete_trainer.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
            }
        });
        Button courseScheduleList = findViewById(R.id.courseScheduleList);
        courseScheduleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), course_schedule_trainer_get.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
            }
        });
        Button updateCourse = findViewById(R.id.updateCourse);
        updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), update_course_trainer.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
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
                    displayCourse(course);
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

    private void displayCourse(CourseDetail course){

//        ImageView imageViewAva = findViewById(R.id.imageViewAva);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewRole = findViewById(R.id.textViewRole);
        TextView textViewStatus = findViewById(R.id.textViewStatus);
        TextView textViewCapacity = findViewById(R.id.textViewCapacity);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewFeeInfo = findViewById(R.id.textViewFeeInfo);
        TextView textViewDateStart = findViewById(R.id.textViewDateStart);
        TextView textViewDateEnd = findViewById(R.id.textViewDateEnd);
        TextView textViewDescInfo = findViewById(R.id.textViewDescInfo);

        Button deleteCourse = findViewById(R.id.deleteCourse);

//        imageViewAva.setImageResource(R.drawable.course_logo_img);
        textViewName.setText(Html.fromHtml("<b>Khóa học:</b> " + course.getCourses().getTitle()));
        textViewRole.setText(String.valueOf("Loại: " + course.getCourses().getServiceTypeString()));
        textViewStatus.setText(String.valueOf(course.getCourses().getTrainerId()));
        textViewFeeInfo.setText(String.valueOf("Học phí: " + course.getCourses().getFee()) + " VNĐ");
        textViewCapacity.setText(String.valueOf("Sĩ số: " + course.getCourses().getCapacity() + " học viên"));
        textViewStatus.setText(Html.fromHtml("Trạng thái: " + course.getCourses().getStringStatus() + "</font>"));
        Date dateS = new Date(course.getCourses().getStartDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateStart = dateFormat.format(dateS);
        Date dateE = new Date(course.getCourses().getEndDate());
        SimpleDateFormat dateEnd = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateEnd = dateEnd.format(dateE);
        textViewLocation.setText("Địa điểm: " + course.getCourses().getLocation());
        textViewDateStart.setText("Ngày bắt đầu: " + formattedDateStart);
        textViewDateEnd.setText("Ngày kết thúc: " + formattedDateEnd);
        textViewDescInfo.setText(course.getCourses().getDescription());
    }
}