package com.example.thanh.activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.Course;
import com.example.thanh.model.CourseDetail;
import com.example.thanh.model.CourseSchedule;
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

public class course_schedule_trainer_update extends AppCompatActivity {
    private EditText startDateEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText notesEditText;
    private ApiService courseApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_schedule_trainer_update);
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed();
            }
        });
        int courseScheduleId = getIntent().getIntExtra("id", -1);
        int courseId = getIntent().getIntExtra("courseId", -1);

        startDateEditText = findViewById(R.id.start_date_edit_text);
        startTimeEditText = findViewById(R.id.start_time_edit_text);
        endTimeEditText = findViewById(R.id.end_time_edit_text);
        notesEditText = findViewById(R.id.notes_edit_text);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
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
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourseSchedule(courseScheduleId,courseId);
            }
        });
    }

    private void updateCourseSchedule(int courseScheduleId,int courseId) {
        String startDate = startDateEditText.getText().toString();
        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();
        String notes = notesEditText.getText().toString();

        if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date startDateTimeObject;
        Date endDateTimeObject;

        try {
            // Kết hợp startDate và startTime để tạo startDateTimeObject
            startDateTimeObject = dateFormat.parse(startDate + " " + startTime);

            // Kết hợp startDate và endTime để tạo endDateTimeObject
            endDateTimeObject = dateFormat.parse(startDate + " " + endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi định dạng ngày/giờ", Toast.LENGTH_SHORT).show();
            return;
        }
        long startDateTime = startDateTimeObject.getTime() / 1000;
        long endDateTime = endDateTimeObject.getTime() / 1000;
        Log.d("StartEnd",String.valueOf(startDateTime));
        Log.d("StartEnd",String.valueOf(endDateTime));
        CourseSchedule courseSchedule = new CourseSchedule();
        courseSchedule.setFromDateTime(startDateTime);
        courseSchedule.setToDateTime(endDateTime);
        courseSchedule.setNote(notes);
        courseSchedule.setCourseId(courseId);
        courseSchedule.setId(courseScheduleId);
        String jsonString = new Gson().toJson(courseSchedule);
        Log.d("courseSchedule:", jsonString);
        courseApi = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<CourseSchedule> callUpdate = courseApi.updateCourseSchedule(courseSchedule, courseScheduleId);
        callUpdate.enqueue(new Callback<CourseSchedule>() {
            @Override
            public void onResponse(Call<CourseSchedule> callUpdate, Response<CourseSchedule> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(course_schedule_trainer_update.this, "Cập nhật khóa học thành công", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(course_schedule_trainer_update.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CourseSchedule> call, Throwable t) {
                Log.e("MyApp", "Cập nhật khóa học thất bại: " + t.getMessage());
                // Hiển thị thông báo lỗi cho người dùng
                Toast.makeText(course_schedule_trainer_update.this, "Cập nhật khóa học thành công", Toast.LENGTH_SHORT).show();

            }

        });
        Toast.makeText(this, "Cập nhật lịch học thành công", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quay trở về trang chi tiết khóa học");
        builder.setMessage("Bạn có muốn quay trở về trang chi tiết khóa học không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý sự kiện khi bấm nút "Đồng ý"
                // Thực hiện hành động quay trở về trang chi tiết khóa học
                // Ví dụ:
                Intent intent = new Intent(course_schedule_trainer_update.this, course_schedule_trainer_get.class);
                intent.putExtra("id",courseId);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Hủy bỏ", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                course_schedule_trainer_update.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý giá trị ngày được chọn
                        // Cập nhật trường nhập liệu hoặc làm bất kỳ công việc khác
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        startDateEditText.setText(selectedDate);
                    }
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }

    private void showTimePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                course_schedule_trainer_update.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Xử lý giá trị giờ được chọn
                        // Cập nhật trường nhập liệu hoặc làm bất kỳ công việc khác
                        String selectedTime = hourOfDay + ":" + minute;
                        editText.setText(selectedTime);
                    }
                },
                hour,
                minute,
                false
        );
        timePickerDialog.show();
    }
}
