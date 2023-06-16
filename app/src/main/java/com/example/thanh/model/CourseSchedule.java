package com.example.thanh.model;

import com.google.gson.annotations.SerializedName;

public class CourseSchedule {
    @SerializedName("_id")
    private int id;
    @SerializedName("courseId")
    private int courseId;
    @SerializedName("note")
    private String note;
    @SerializedName("fromDateTime")
    private long fromDateTime;
    @SerializedName("toDateTime")
    private long toDateTime;
    @SerializedName("__v")
    private int version;

    // Constructors
    public CourseSchedule(int id, int courseId, String note, long fromDateTime, long toDateTime, int version) {
        this.id = id;
        this.courseId = courseId;
        this.note = note;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.version = version;
    }

    // Getters and Setters (các phương thức truy cập và thiết lập dữ liệu)

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(long fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public long getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(long toDateTime) {
        this.toDateTime = toDateTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}