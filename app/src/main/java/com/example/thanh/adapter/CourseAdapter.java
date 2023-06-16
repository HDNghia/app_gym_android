package com.example.thanh.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.activity.course_user_get;
import com.example.thanh.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;

    // Constructor to initialize the list of courses
    public CourseAdapter(List<Course> courses) {
        this.courses = courses;
    }

    // Create the ViewHolder for the course item
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewDescription;
        public TextView textViewLocation;
        public TextView textViewFee;

        public CourseViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewFee = itemView.findViewById(R.id.textViewFee);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    // Bind the data to the ViewHolder
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        // Set the data to the views
        holder.imageView.setImageResource(R.drawable.course);
        holder.textViewTitle.setText(course.getTitle());
        holder.textViewDescription.setText(course.getDescription());
        holder.textViewLocation.setText(course.getLocation());
        holder.textViewFee.setText(String.valueOf(course.getFee()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Course course = courses.get(position);
                Intent intent = new Intent(view.getContext(), course_user_get.class);
                intent.putExtra("_id", course.getId());
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