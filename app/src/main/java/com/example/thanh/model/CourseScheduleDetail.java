package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseScheduleDetail {
    @SerializedName("courseSheduleinfo")
    private List<CourseSchedule> courseSheduleinfo;

    @SerializedName("courseinfo")
    private Course courseInfo;

    public List<CourseSchedule> getCourseSchedule() {
        return courseSheduleinfo;
    }

    public void setCourseSchedule(List<CourseSchedule> courseSchedule) {
        this.courseSheduleinfo = courseSchedule;
    }

    public Course getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(Course courseInfo) {
        this.courseInfo = courseInfo;
    }
}