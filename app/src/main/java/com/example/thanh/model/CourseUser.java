package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;
public class CourseUser {
    @SerializedName("_id")
    private int id;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("registerDateTime")
    private long registerDateTime;

    @SerializedName("status")
    private boolean status;

    @SerializedName("__v")
    private int version;

    @SerializedName("courseinfo")
    private Course courseInfo;

    @SerializedName("userinfo")
    private UserDetail userDetail;

    @SerializedName("ServiceInfo")
    private ServiceDetail serviceDetail;

    // Getter and Setter methods for the class members

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Course getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(Course courseInfo) {
        this.courseInfo = courseInfo;
    }

    public UserDetail getUserInfo() {
        return userDetail;
    }

    public void setUserInfo(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public ServiceDetail getServiceInfo() {
        return serviceDetail;
    }

    public void setServiceDetail(ServiceDetail serviceDetail) {
        this.serviceDetail = serviceDetail;
    }
}