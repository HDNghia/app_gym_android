package com.example.thanh.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.model.Course;
import com.example.thanh.R;
import com.example.thanh.activity.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseTrainerAdapter extends RecyclerView.Adapter<CourseTrainerAdapter.CourseViewHolder> {

    private List<Course> courses;

    // Constructor to initialize the list of courses
    public CourseTrainerAdapter(List<Course> courses) {
        this.courses = courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }
    // Create the ViewHolder for the course item
    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewDate;
        public TextView textViewLocation;
        public  TextView textViewCapacity;


        public CourseViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_trainer_item, parent, false);
        return new CourseViewHolder(view);
    }

    // Bind the data to the ViewHolder
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        Date dateS = new Date(course.getStartDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateStart = dateFormat.format(dateS);
        Date dateE = new Date(course.getEndDate());
        String formattedDateEnd = dateFormat.format(dateE);
        // Set the data to the views
        holder.imageView.setImageResource(R.drawable.course_logo_img);
        Log.d("DEBUG:", "1");
        holder.textViewTitle.setText(Html.fromHtml("<b>Khóa học:</b> " + course.getTitle()));
        Log.d("DEBUG:", "2");
        holder.textViewDate.setText(formattedDateStart + " - " +formattedDateEnd);
        Log.d("DEBUG:", "3");
        holder.textViewLocation.setText(Html.fromHtml("<b>Địa điểm:</b> " + course.getLocation()));
        Log.d("DEBUG:", "4");
        holder.textViewCapacity.setText(Html.fromHtml("<b>Số lượng học viên:</b> " + course.getCapacity()));
        Log.d("DEBUG:", "5");
        int status = course.getStatus();
        String statusString = course.getStringStatus();

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append("Trạng thái: ");

        ForegroundColorSpan colorSpan;
        switch (status) {
            case 0:
                // Màu xám cho trạng thái 0
                colorSpan = new ForegroundColorSpan(Color.GRAY);
                break;
            case 1:
                // Màu đỏ cho trạng thái 1
                colorSpan = new ForegroundColorSpan(Color.RED);
                break;
            case 2:
                // Màu xanh dương cho trạng thái 2
                colorSpan = new ForegroundColorSpan(Color.BLUE);
                break;
            default:
                // Màu mặc định cho các trạng thái khác (đỏ)
                colorSpan = new ForegroundColorSpan(Color.RED);
                break;
        }

        stringBuilder.append(statusString, colorSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.textViewCapacity.setText(stringBuilder);
        Log.d("CourseId", String.valueOf(course.getId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Log.d("DEBUG POS: ", String.valueOf(position));
                // Get the corresponding student
                Course course = courses.get(position);
                // Navigate to the student's profile page using an Intent
                Intent intent = new Intent(view.getContext(), course_trainer_details.class);
                Log.d("CourseId", String.valueOf(course.getId()));
                intent.putExtra("id", course.getId());
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).finish();
            }
        });

    }

    // Return the number of courses in the list
    @Override
    public int getItemCount() {
        return courses.size();
    }
}