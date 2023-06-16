package com.example.thanh.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.model.CourseUser;
import com.example.thanh.activity.*;
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
        CourseUser course = courses.get(position);

        // Set the data to the views
        holder.imageView.setImageResource(R.drawable.course);
        holder.textViewTitle.setText("Lop tap the hinh");
        holder.textViewDescription.setText("Nang cao suc khoe");
        holder.textViewLocation.setText("Da Nang");
        holder.textViewFee.setText(String.valueOf(123));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                // Get the corresponding student
                CourseUser courseUser = courses.get(position);
                // Navigate to the student's profile page using an Intent
                Intent intent = new Intent(view.getContext(), course_user_signed_get.class);
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