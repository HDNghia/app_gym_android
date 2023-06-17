package com.example.thanh.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.thanh.R;
import com.example.thanh.model.Course;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class create_course_trainer extends AppCompatActivity {
    private EditText titleEditText, startDateEditText, endDateEditText,endTimeEditText,
            locationEditText, feeEditText, descriptionEditText,capacityEditText,startTimeEditText;
    private Spinner serviceSpinner;
    private Button attachmentButton, createCourseButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    private Button buttonAttachImage;
    private CheckBox mondayCheckBox,tuesdayCheckBox, wednesdayCheckBox,thursdayCheckBox,
            fridayCheckBox, saturdayCheckBox,sundayCheckBox;

    private ApiService courseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_trainer_post);
        // Attach
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed(); // Navigate back to the previous screen
            }
        });
        // Tạo đối tượng CourseApi

        // Initialize views
        titleEditText = findViewById(R.id.title_edit_text);
        startDateEditText = findViewById(R.id.start_date_edit_text);
        endDateEditText = findViewById(R.id.end_date_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        feeEditText = findViewById(R.id.fee_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        serviceSpinner = findViewById(R.id.service_spinner);
        createCourseButton = findViewById(R.id.create_course_button);
        capacityEditText = findViewById(R.id.capacity_edit_text);
        startTimeEditText = findViewById(R.id.start_time_edit_text);
        endTimeEditText = findViewById(R.id.end_time_edit_text);
        mondayCheckBox = findViewById(R.id.monday_check_box);
        tuesdayCheckBox = findViewById(R.id.tuesday_check_box);
        wednesdayCheckBox = findViewById(R.id.wednesday_check_box);
        thursdayCheckBox = findViewById(R.id.thursday_check_box);
        fridayCheckBox = findViewById(R.id.friday_check_box);
        saturdayCheckBox = findViewById(R.id.saturday_check_box);
        sundayCheckBox = findViewById(R.id.sunday_check_box);


        // Set up date pickers for start and end dates
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startDateEditText);
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endDateEditText);
            }
        });
        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(startTimeEditText);
            }
        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(endTimeEditText);
            }
        });

        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showDatePicker(final EditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the selected date in the corresponding EditText
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String selectedDate = dateFormat.format(calendar.getTime());
                editText.setText(selectedDate);
            }
        };

        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
    private void showTimePicker(final EditText editText) {
        // Lấy giờ và phút hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Tạo đối tượng TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Cập nhật giờ đã chọn vào EditText tương ứng
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                editText.setText(selectedTime);
            }
        }, hour, minute, true);

        // Hiển thị TimePickerDialog
        timePickerDialog.show();
    }

    private void createCourse() {
        // Lấy thông tin từ các EditText và Spinner
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String startDateString = startDateEditText.getText().toString();
        String endDateString = endDateEditText.getText().toString();
        String startTimeString = startTimeEditText.getText().toString();
        String endTimeString = endTimeEditText.getText().toString();
        int capacity = Integer.parseInt(capacityEditText.getText().toString());
        int fee = Integer.parseInt(feeEditText.getText().toString());
        int serviceTypeId = serviceSpinner.getSelectedItemPosition();
        String mondayValue = mondayCheckBox.isChecked() ? "1" : "0";
        String tuesdayValue = tuesdayCheckBox.isChecked() ? "1" : "0";
        String wednesdayValue = wednesdayCheckBox.isChecked() ? "1" : "0";
        String thursdayValue = thursdayCheckBox.isChecked() ? "1" : "0";
        String fridayValue = fridayCheckBox.isChecked() ? "1" : "0";
        String saturdayValue = saturdayCheckBox.isChecked() ? "1" : "0";
        String sundayValue = sundayCheckBox.isChecked() ? "1" : "0";

        // Chuyển đổi chuỗi ngày thành đối tượng Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate, endDate;
        try {
            startDate = dateFormat.parse(startDateString);
            endDate = dateFormat.parse(endDateString);
        } catch (ParseException e) {
            // Xử lý khi định dạng ngày không hợp lệ
            Toast.makeText(create_course_trainer.this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String stringSchedule = startDate.getTime() + "-" + endDate.getTime() + "-" + mondayValue + "-" + tuesdayValue + "-"
                + wednesdayValue + "-" + thursdayValue + "-" + fridayValue + "-" + saturdayValue + "-" + sundayValue + "-"
                + startTimeString + "-" + endTimeString;

        Log.d("bugg",stringSchedule);
        Log.d("TAG","Hi");
        // Tạo đối tượng Course
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setLocation(location);
        course.setStartDate(startDate.getTime());
        course.setEndDate(endDate.getTime());
        course.setStringSchedule(stringSchedule);
        course.setFee(fee);
        course.setCapacity(capacity);
        course.setServiceTypeId(serviceTypeId);
        course.setTrainerId(13);
        course.setStatus(0);
        Gson gson = new Gson();
        String courseJson = gson.toJson(course);

        // In ra giá trị JSON của đối tượng course
        Log.d("Course JSON", courseJson);

        courseApi = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        // Gửi yêu cầu POST để tạo khóa học
        Call<Course> call = courseApi.createCourse(course);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if (response.isSuccessful()) {
                    Course createdCourse = response.body();
                    String jsonString = new Gson().toJson(createdCourse);
                    Log.d("RES", jsonString);
                    // Xử lý khi tạo khóa học thành công
                    Toast.makeText(create_course_trainer.this, "Tạo khóa học thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(create_course_trainer.this, course_trainer_details.class);
                    intent.putExtra("id", createdCourse.getId());
                    startActivity(intent);
                } else {
                    // Xử lý khi tạo khóa học thất bại
                    Toast.makeText(create_course_trainer.this, "Tạo khóa học thất bại", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(create_course_trainer.this, course_trainer_get.class);
                    startActivity(intent);
                }
//                finish();
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                // Xử lý khi gặp lỗi
                String errorMessage = "Lỗi: " + t.getMessage();
                Log.d("ERROR", errorMessage);
                Toast.makeText(create_course_trainer.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(create_course_trainer.this, course_trainer_get.class);
                startActivity(intent);
                finish();
            }
        });


    }
}