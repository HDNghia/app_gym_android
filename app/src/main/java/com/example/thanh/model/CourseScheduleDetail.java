package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class CourseScheduleDetail {
    @SerializedName("courseSchedule")
    private CourseSchedule courseSchedule;

    @SerializedName("courseInfo")
    private Course courseInfo;

    public CourseSchedule getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(CourseSchedule courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public Course getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(Course courseInfo) {
        this.courseInfo = courseInfo;
    }
}