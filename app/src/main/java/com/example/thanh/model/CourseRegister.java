package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class CourseRegister {
    @SerializedName("courseId")
    private int courseId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("registerDateTime")
    private long registerDateTime;

    @SerializedName("status")
    private boolean status;

    // Constructors, getter and setter methods

    public CourseRegister() {
    }

    public CourseRegister(int courseId, int userId, long registerDateTime, boolean status) {
        this.courseId = courseId;
        this.userId = userId;
        this.registerDateTime = registerDateTime;
        this.status = status;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getRegisterDateTime() {
        return registerDateTime;
    }

    public void setRegisterDateTime(long registerDateTime) {
        this.registerDateTime = registerDateTime;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}