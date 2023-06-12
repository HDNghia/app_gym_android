package com.example.thanh.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanh.R;
import com.example.thanh.activity.course_trainer_details;
import com.example.thanh.model.Course;
import com.example.thanh.model.CourseSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseScheduleAdapter extends RecyclerView.Adapter<CourseScheduleAdapter.CourseScheduleViewHolder> {

    private List<CourseSchedule> courseSchedules;

    public CourseScheduleAdapter(List<CourseSchedule> courseSchedules) {
        this.courseSchedules = courseSchedules;
    }

    public void setCourseSchedules(List<CourseSchedule> courseSchedules) {
        this.courseSchedules = courseSchedules;
        notifyDataSetChanged();
    }


    @Override
    public CourseScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_schedule, parent, false);
        return new CourseScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseScheduleViewHolder holder, int position) {
        CourseSchedule courseSchedule = courseSchedules.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy", new Locale("vi", "VN"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date dateF = new Date(courseSchedule.getFromDateTime());
        String formattedDateStart = dateFormat.format(dateF);
        String formattedTimeStart = timeFormat.format(dateF);

        Date dateT = new Date(courseSchedule.getToDateTime());
        String formattedDateEnd = dateFormat.format(dateT);
        String formattedTimeEnd = timeFormat.format(dateT);

        String timeRange = formattedTimeStart + " - " + formattedTimeEnd;

        holder.dateTextView.setText(formattedDateStart);
        holder.timeTextView.setText(timeRange);
        holder.noteTextView.setText(courseSchedule.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Log.d("DEBUG POS: ", String.valueOf(position));
                // Get the corresponding student
                CourseSchedule courseSchedule = courseSchedules.get(position);
                // Navigate to the student's profile page using an Intent
                Intent intent = new Intent(view.getContext(), course_trainer_details.class);
//                Log.d("CourseId", String.valueOf(courseSchedule.getId()));
//                intent.putExtra("id", courseSchedule.getId());
//                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseSchedules.size();
    }

    public static class CourseScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTextView;
        private TextView timeTextView;
        private TextView dateTextView;

        public CourseScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
