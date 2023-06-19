package com.example.thanh.adapter;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.model.CourseUser;
import com.example.thanh.activity.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseUserAdapter extends RecyclerView.Adapter<CourseUserAdapter.CourseViewHolder> {

    private List<CourseUser> courses;

    // Constructor to initialize the list of courses
    public CourseUserAdapter(List<CourseUser> courses) {
        this.courses = courses;
    }

    // Create the ViewHolder for the course item
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewDate;
        public TextView textViewLocation;
        public TextView textViewFee;

        public CourseViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewFee = itemView.findViewById(R.id.textViewFee);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_user_item, parent, false);
        return new CourseViewHolder(view);
    }

    // Bind the data to the ViewHolder
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        CourseUser course = courses.get(position);

        Date dateS = new Date(course.getCourseInfo().getStartDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateStart = dateFormat.format(dateS);
        Date dateE = new Date(course.getCourseInfo().getEndDate());
        String formattedDateEnd = dateFormat.format(dateE);

        // Set the data to the views
        holder.imageView.setImageResource(R.drawable.course);
        holder.textViewTitle.setText(Html.fromHtml("<b>Khóa học:</b> " + course.getCourseInfo().getTitle()));
        holder.textViewDate.setText(formattedDateStart + " - " +formattedDateEnd);
        holder.textViewLocation.setText(Html.fromHtml("<b>Địa điểm:</b> " + course.getCourseInfo().getLocation()));
        holder.textViewFee.setText(String.valueOf(course.getCourseInfo().getFee()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                // Get the corresponding student
                CourseUser courseUser = courses.get(position);
                // Navigate to the student's profile page using an Intent
                Intent intent = new Intent(view.getContext(), course_user_signed_get.class);
                Log.d("Tuandeptraivaicalin", String.valueOf(courseUser.getCourseId()));
                intent.putExtra("_id", courseUser.getCourseId());
                view.getContext().startActivity(intent);
            }
        });

    }

    // Return the number of courses in the list
    @Override
    public int getItemCount() {
        return courses.size();
    }
}