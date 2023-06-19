package com.example.thanh.model;

public class LikeRequest {
    private int postId;
    private int userId;

    public LikeRequest(int postId, int userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }
}
