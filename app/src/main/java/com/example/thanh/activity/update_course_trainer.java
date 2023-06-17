package com.example.thanh.activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.thanh.R;
import com.example.thanh.model.*;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class update_course_trainer extends AppCompatActivity {

    private ApiService courseApi;
    private EditText titleEditText, startDateEditText, endDateEditText, locationEditText, feeEditText, descriptionEditText,capacityEditText;
    private Spinner serviceSpinner,statusSpinner;
    private Button attachmentButton, updateCourseButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_update_trainer);
        titleEditText = findViewById(R.id.title_edit_text);
        serviceSpinner = findViewById(R.id.service_spinner);
        locationEditText = findViewById(R.id.location_edit_text);
        feeEditText = findViewById(R.id.fee_edit_text);
        capacityEditText= findViewById(R.id.capacity_edit_text);
        startDateEditText= findViewById(R.id.start_date_edit_text);
        endDateEditText= findViewById(R.id.end_date_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        statusSpinner = findViewById(R.id.status_spinner);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        Button btnCancel = findViewById(R.id.back_button);
        int courseId = getIntent().getIntExtra("id", -1);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                Intent intent = new Intent(update_course_trainer.this, course_trainer_details.class);
                intent.putExtra("id", courseId);
                startActivity(intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        courseApi = RetrofitClient.getRetrofitInstance().create(ApiService.class);


        Call<CourseDetail> call = courseApi.getCourseById(courseId);
        call.enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(Call<CourseDetail> call, Response<CourseDetail> response) {
                if (response.isSuccessful()) {
                    CourseDetail course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES", jsonString);
                    Log.d("API", "Success");
                    updateCourse(course);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                // Handle failure
            }
        });
//        updateCourseButton = findViewById(R.id.update_course_button);
//        updateCourseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateCourseFunc(courseId);
//            }
//        });

    }

    private void updateCourse(CourseDetail course){

        TextView editTextTitle = findViewById(R.id.title_edit_text);
        Spinner spinnerService = findViewById(R.id.service_spinner);
        TextView editTextLocation = findViewById(R.id.location_edit_text);
        TextView editTextFee= findViewById(R.id.fee_edit_text);
        TextView editTextCapacity= findViewById(R.id.capacity_edit_text);
        TextView editTextStartDate= findViewById(R.id.start_date_edit_text);
        TextView editTextEndDate= findViewById(R.id.end_date_edit_text);
        TextView editTextDescription = findViewById(R.id.description_edit_text);
        Spinner statusSpinner = findViewById(R.id.status_spinner);


        editTextTitle.setText(course.getCourses().getTitle());
        int selectedStatusPosition = course.getCourses().getStatus();
        statusSpinner.setSelection(selectedStatusPosition);
        int selectedPosition = course.getCourses().getServiceTypeId();
        spinnerService.setSelection(selectedPosition);
        editTextLocation.setText(course.getCourses().getLocation());
        editTextFee.setText(String.valueOf(course.getCourses().getFee()));
        editTextCapacity.setText(String.valueOf(course.getCourses().getCapacity()));
        // Chuyển đổi startDate từ kiểu long sang Date
        Date startDateObject = new Date(course.getCourses().getStartDate());
        // Định dạng ngày theo định dạng mong muốn (vd: "dd/MM/yyyy")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Chuyển đổi Date thành chuỗi ngày đã định dạng
        String formattedStartDate = dateFormat.format(startDateObject);
        // Gán giá trị đã định dạng vào editTextStartDate
        editTextStartDate.setText(formattedStartDate);
        Date endDateObject = new Date(course.getCourses().getEndDate());
        // Chuyển đổi Date thành chuỗi ngày đã định dạng
        String formattedEndDate = dateFormat.format(endDateObject);
        // Gán giá trị đã định dạng vào editTextStartDate
        editTextEndDate.setText(formattedEndDate);
        editTextDescription.setText(course.getCourses().getDescription());
        updateCourseButton = findViewById(R.id.update_course_button);
        updateCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCourseFunc(course.getCourses().getId());
            }
        });

    }
    private void updateCourseFunc(int courseID){
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String startDateString = startDateEditText.getText().toString();
        String endDateString = endDateEditText.getText().toString();
        int capacity = Integer.parseInt(capacityEditText.getText().toString());
        int fee = Integer.parseInt(feeEditText.getText().toString());
        int serviceTypeId = serviceSpinner.getSelectedItemPosition();
        int status = statusSpinner.getSelectedItemPosition();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate, endDate;
        try {
            startDate = dateFormat.parse(startDateString);
            endDate = dateFormat.parse(endDateString);
        } catch (ParseException e) {
            // Xử lý khi định dạng ngày không hợp lệ
            Toast.makeText(update_course_trainer.this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        Course course = new Course();
        course.setId(courseID);
        course.setTitle(title);
        course.setDescription(description);
        course.setLocation(location);
        course.setStartDate(startDate.getTime());  // Chuyển đổi thành số nguyên
        course.setEndDate(endDate.getTime());  // Chuyển đổi thành số nguyên
        course.setFee(fee);
        course.setCapacity(capacity);
        course.setServiceTypeId(serviceTypeId);
        course.setStatus(status);
        course.setTrainerId(13);
        course.setAttachment("Teo");

        Log.d("MyApp", "Title: " + course.getTitle());
        Log.d("MyApp", "Description: " + course.getDescription());
        Log.d("MyApp", "Location: " + course.getLocation());
        Log.d("MyApp", "StartDate: " + course.getStartDate());
        Log.d("MyApp", "EndDate: " + course.getEndDate());
        Log.d("MyApp", "Fee: " + course.getFee());
        Log.d("MyApp", "Capacity: " + course.getCapacity());
        Log.d("MyApp", "ServiceTypeId: " + course.getServiceTypeId());
        Log.d("MyApp", "TrainerId: " + course.getTrainerId());
        Log.d("MyApp", "Status: " + course.getStatus());


        String jsonString = new Gson().toJson(course);
        Log.d("RES", jsonString);
        Call<Course> callUpdate = courseApi.updateCourse(course, course.getId());
        callUpdate.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> callUpdate, Response<Course> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(update_course_trainer.this, "Cập nhật khóa học thành công", Toast.LENGTH_SHORT).show();

                } else {
                    int statusCode = response.code();
                    String errorMessage = "Cập nhật khóa học không thành công. Mã phản hồi: " + statusCode;
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            // Check if the errorBody is not empty
                            if (!TextUtils.isEmpty(errorBody)) {
                                errorMessage += " Nội dung lỗi: " + errorBody;
                                Log.e("MyApp", errorMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(update_course_trainer.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                Log.e("MyApp", "Cập nhật khóa học thất bại: " + t.getMessage());
                // Hiển thị thông báo lỗi cho người dùng
                Toast.makeText(update_course_trainer.this, "Cập nhật khóa học thành công", Toast.LENGTH_SHORT).show();

            }

        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quay trở về trang khóa học");
        builder.setMessage("Bạn có muốn quay trở về trang khóa học không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý sự kiện khi bấm nút "Đồng ý"
                // Thực hiện hành động quay trở về trang khóa học
                // Ví dụ:
                Intent intent = new Intent(update_course_trainer.this, course_trainer_details.class);
                intent.putExtra("id", courseID);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
