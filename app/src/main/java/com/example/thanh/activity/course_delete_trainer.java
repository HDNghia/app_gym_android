package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.Course;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_delete_trainer extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int courseID = getIntent().getIntExtra("_id", -1);
        deleteCourseByCourseId(courseID);
        Intent intent = new Intent(course_delete_trainer.this, course_trainer_get.class);
        startActivity(intent);
}
    private void deleteCourseByCourseId(int courseID) {
        Call<Course> call = apiService.deleteCourseByID(courseID);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if (response.isSuccessful()) {
                    Course course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES", jsonString);
                    Log.d("API", "Success");
                    Toast.makeText(course_delete_trainer.this, "Xóa khóa học thành công", Toast.LENGTH_SHORT).show();

                } else {
                    // Handle unsuccessful response
                    Log.d("API", "Failed");
                    Toast.makeText(course_delete_trainer.this, "Xóa khóa học không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                // Handle failure
                Log.d("Failure:", "Failed");
                Toast.makeText(course_delete_trainer.this, "Xóa khóa học không thành công", Toast.LENGTH_SHORT).show();
            }
        });
       // Đặt thời gian chờ là 10 giây (10000 milliseconds)


    }}