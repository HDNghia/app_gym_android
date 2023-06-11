package com.example.thanh.model;

public class Post {
    private int _id;
    private int ownerId;
    private String caption;
    private String attachmentId1;
    private String attachmentId2;
    private long createdDate;
    private long lastModifyDate;
    private boolean isDeleted;
    private int Countlike;
    private int __v;
    private int CountComment;
    private UserDetail userInfo;

    // Các phương thức getter và setter
    // ...
    public int getCountlike() {
        return Countlike;
    }

    public void setCountlike(int countlike) {
        Countlike = countlike;
    }

    public int getId() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setAttachmentId1(String attachmentId1) {
        this.attachmentId1 = attachmentId1;
    }

    public void setAttachmentId2(String attachmentId2) {
        this.attachmentId2 = attachmentId2;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifyDate(long lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void setCountComment(int countComment) {
        CountComment = countComment;
    }

    public void setUserInfo(UserDetail userInfo) {
        this.userInfo = userInfo;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getCaption() {
        return caption;
    }

    public String getAttachmentId1() {
        return attachmentId1;
    }

    public String getAttachmentId2() {
        return attachmentId2;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getLastModifyDate() {
        return lastModifyDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public int get__v() {
        return __v;
    }

    public int getCountComment() {
        return CountComment;
    }

    public UserDetail getUserInfo() {
        return userInfo;
    }
}


