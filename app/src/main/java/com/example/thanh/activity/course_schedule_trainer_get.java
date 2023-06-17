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

import java.util.Collections;
import java.util.Comparator;
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
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int courseId = getIntent().getIntExtra("id", -1);
        Log.d("CourseID", String.valueOf(courseId));
        ImageButton btnGoBack = findViewById(R.id.btnBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                Intent intent = new Intent(course_schedule_trainer_get.this, course_trainer_details.class);
                intent.putExtra("id", courseId);
                startActivity(intent);
                finish();
            }
        });
        getCourseSchedulesByTrainerID(courseId);
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
        // Sort the list based on the start time
        Collections.sort(courseSchedules, new Comparator<CourseSchedule>() {
            @Override
            public int compare(CourseSchedule schedule1, CourseSchedule schedule2) {
                return Long.compare(schedule1.getFromDateTime(), schedule2.getFromDateTime());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourseSchedules);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CourseScheduleAdapter adapter = new CourseScheduleAdapter(courseSchedules);
        recyclerView.setAdapter(adapter);
    }

}
