package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("_id")
    private int id=1;

    @SerializedName("trainerId")
    private int trainerId=1;

    @SerializedName("title")
    private String title="";

    @SerializedName("description")
    private String description="";

    @SerializedName("location")
    private String location="";

    @SerializedName("status")
    private int status=0;

    @SerializedName("startDate")
    private long startDate=0;

    @SerializedName("endDate")
    private long endDate=0;

    @SerializedName("capacity")
    private int capacity=0;

    @SerializedName("attachment")
    private String attachment="";

    @SerializedName("fee")
    private int fee=0;

    @SerializedName("isDeleted")
    private boolean isDeleted= false;

    @SerializedName("serviceTypeId")
    private int serviceTypeId=0;

    @SerializedName("stringSchedule")
    private String stringSchedule="";

    @SerializedName("lastModifyDate")
    private long lastModifyDate=0;

    @SerializedName("__v")
    private int __v=0;

    public Course(){

    }

    public Course(int id, int trainerId, String title, String description, String location, int status, long startDate, long endDate, int capacity, String attachment, int fee, boolean isDeleted, int serviceTypeId, long lastModifyDate, int version) {
        this.id = id;
        this.trainerId = trainerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.attachment = attachment;
        this.stringSchedule = "";
        this.fee = fee;
        this.isDeleted = isDeleted;
        this.serviceTypeId = serviceTypeId;
        this.lastModifyDate = lastModifyDate;
        this.__v = version;
    }

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

    public String getStringSchedule() {
        return stringSchedule;
    }

    public void setStringSchedule(String stringSchedule) {
        this.stringSchedule = stringSchedule;
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
        return __v;
    }

    public void setVersion(int version) {
        this.__v = version;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", trainerId=" + trainerId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", capacity=" + capacity +
                ", attachment='" + attachment + '\'' +
                ", fee=" + fee +
                ", isDeleted=" + isDeleted +
                ", serviceTypeId=" + serviceTypeId +
                ", lastModifyDate=" + lastModifyDate +
                ", version=" + __v +
                '}';
    }
}