package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class CourseDetail {
    @SerializedName("courses")
    private Course course;

    @SerializedName("trainerinfo")
    private UserDetail trainerinfo;

    @SerializedName("serviceinfo")
    private ServiceDetail serviceinfo;

    public Course getCourses() {
        return course;
    }

    public void setCourses(Course courses) {
        this.course = courses;
    }

    public UserDetail getTrainerinfo() {
        return trainerinfo;
    }

    public void setTrainerinfo(UserDetail trainerinfo) {
        this.trainerinfo = trainerinfo;
    }

    public ServiceDetail getServiceinfo() {
        return serviceinfo;
    }

    public void setServiceinfo(ServiceDetail serviceinfo) {
        this.serviceinfo = serviceinfo;
    }
}