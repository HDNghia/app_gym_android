package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseScheduleCalendar {
    @SerializedName("_id")
    private int id;

    @SerializedName("trainerId")
    private int trainerId;
    @SerializedName("attachment")
    private String attachment;
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("status")
    private int status;

    @SerializedName("startDate")
    private long startDate;

    @SerializedName("endDate")
    private long endDate;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("fee")
    private int fee;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    @SerializedName("serviceTypeId")
    private int serviceTypeId;

    @SerializedName("lastModifyDate")
    private long lastModifyDate;

    @SerializedName("__v")
    private int version;

    @SerializedName("courseSheduleinfo")
    private List<CourseSchedule> courseScheduleInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public String getStringStatus() {
        String statusString;
        switch (status) {
            case 0:
                statusString = "Chưa bắt đầu";
                break;
            case 1:
                statusString = "Đang diễn ra";
                break;
            case 2:
                statusString = "Hoàn thành";
                break;
            default:
                statusString = "Không xác định";
                break;
        }
        return statusString;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public String getServiceTypeString() {
        String typeString;
        switch (status) {
            case 0:
                typeString = "Thể hình";
                break;
            case 1:
                typeString = "Yoga";
                break;
            case 2:
                typeString = "Bóng đá";
                break;
            default:
                typeString = "Bơi lội";
                break;
        }
        return typeString;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public long getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(long lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public List<CourseSchedule> getCourseScheduleInfo() {
        return courseScheduleInfo;
    }

    public void setCourseScheduleInfo(List<CourseSchedule> courseScheduleInfo) {
        this.courseScheduleInfo = courseScheduleInfo;
    }
}
