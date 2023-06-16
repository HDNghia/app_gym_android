package com.example.thanh.model;

public class Invoice {
    private int _id;
    private int userId;
    private int tranQty;
    private int tranDate;
    private int status;
    private String tranContent;
    private String note;
    private int tranType;

    public Invoice(int _id, int userId, int tranQty, int tranDate, int status, String tranContent, String note, int tranType) {
        this._id = _id;
        this.userId = userId;
        this.tranQty = tranQty;
        this.tranDate = tranDate;
        this.status = status;
        this.tranContent = tranContent;
        this.note = note;
        this.tranType = tranType;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTranQty() {
        return tranQty;
    }

    public void setTranQty(int tranQty) {
        this.tranQty = tranQty;
    }

    public int getTranDate() {
        return tranDate;
    }

    public void setTranDate(int tranDate) {
        this.tranDate = tranDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTranContent() {
        return tranContent;
    }

    public void setTranContent(String tranContent) {
        this.tranContent = tranContent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }
}

