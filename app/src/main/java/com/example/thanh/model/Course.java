package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("_id")
    private int id;

    @SerializedName("trainerId")
    private int trainerId;

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

    @SerializedName("attachment")
    private String attachment;

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
        this.fee = fee;
        this.isDeleted = isDeleted;
        this.serviceTypeId = serviceTypeId;
        this.lastModifyDate = lastModifyDate;
        this.version = version;
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
                ", version=" + version +
                '}';
    }
}