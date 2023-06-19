package com.example.thanh.model;

public class PostItem {
    private String userImage;
    private String email;
    private String title;
    private String posterImage;
    private String commentCount;
    private String heartCount;


    public PostItem(String userImage, String email, String title, String posterImage, String commentCount, String heartCount) {
        this.userImage = userImage;
        this.email = email;
        this.title = title;
        this.posterImage = posterImage;
        this.commentCount = commentCount;
        this.heartCount = heartCount;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getHeartCount() {
        return heartCount;
    }
}


