package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.adapter.CourseScheduleAdapter;
import com.example.thanh.model.CourseSchedule;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_schedule_trainer_get extends AppCompatActivity {

    private ApiService apiService;

    private boolean isFormVisible = false;
    private Button buttonAdd;
    private LinearLayout formLayout;
    private Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_schedule_trainer_get);
        // Khởi tạo các view
        buttonAdd = findViewById(R.id.buttonAdd);
        formLayout = findViewById(R.id.formLayout);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFormVisibility();
            }
        });

        // Xử lý sự kiện nhấp vào button "Thêm" trong form
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện thao tác thêm dữ liệu
                // TODO: Xử lý thêm dữ liệu vào cơ sở dữ liệu

                // Ẩn form và hiển thị lại button "Thêm"
                toggleFormVisibility();
            }
        });
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int courseId = getIntent().getIntExtra("id", -1);
        Log.d("CourseID", String.valueOf(courseId));
//        courseCalendarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(course_schedule_trainer_get.this, course_calendar_view.class);
//                startActivity(intent);
//            }
//        });

//        FloatingActionButton createCourseButton = findViewById(R.id.createCourseButton);
//        createCourseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openCreateCourseActivity();
//            }
//        });

        getCourseSchedulesByTrainerID(courseId);
    }
    private void toggleFormVisibility() {
        if (isFormVisible) {
            // Nếu form đang hiển thị, ẩn form và hiển thị lại button "Thêm"
            formLayout.setVisibility(View.GONE);
            buttonAdd.setVisibility(View.VISIBLE);
            isFormVisible = false;
        } else {
            // Nếu form không hiển thị, ẩn button "Thêm" và hiển thị form
            buttonAdd.setVisibility(View.GONE);
            formLayout.setVisibility(View.VISIBLE);
            isFormVisible = true;
        }
    }
    public class GetCourseSchedule {
        private List<CourseSchedule> GetCourseSchedule;

        public List<CourseSchedule> getGetCourseSchedule() {
            return GetCourseSchedule;
        }

        public void setGetCourseSchedule(List<CourseSchedule> GetCourseSchedule) {
            this.GetCourseSchedule = GetCourseSchedule;
        }
    }
    private void getCourseSchedulesByTrainerID(int courseId) {
        Call<GetCourseSchedule> call = apiService.getCourseScheduleByCourseId(courseId);
        call.enqueue(new Callback<GetCourseSchedule>() {
            @Override
            public void onResponse(Call<GetCourseSchedule> call, Response<GetCourseSchedule> response) {
                if (response.isSuccessful()) {
                    GetCourseSchedule getCourseSchedule = response.body();
                    List<CourseSchedule> courseSchedules = getCourseSchedule.getGetCourseSchedule();
                    displayCourseSchedules(courseSchedules);
                    // Tiếp tục xử lý danh sách lịch học
                    // ...
                } else {
                    Log.w("API", "Failed");
                }
            }

            @Override
            public void onFailure(Call<GetCourseSchedule> call, Throwable t) {
                Log.d("Failure:", "Failed");
            }
        });
    }

    public void openCreateCourseActivity() {
        Intent intent = new Intent(course_schedule_trainer_get.this, create_course_trainer.class);
        startActivity(intent);
    }

    private void displayCourseSchedules(List<CourseSchedule> courseSchedules) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourseSchedules);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CourseScheduleAdapter adapter = new CourseScheduleAdapter(courseSchedules);
        recyclerView.setAdapter(adapter);
    }
}
