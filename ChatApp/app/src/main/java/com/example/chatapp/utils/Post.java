package com.example.chatapp.utils;

public class Post {

    private String date, postDes, postImageUrl, userProfileImageUrl, username;


    public Post() {

    }

    public Post(String date, String postDes, String postImageUrl, String userProfileImageUrl, String username) {
        this.date = date;
        this.postDes = postDes;
        this.postImageUrl = postImageUrl;
        this.userProfileImageUrl = userProfileImageUrl;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostDes() {
        return postDes;
    }

    public void setPostDes(String postDes) {
        this.postDes = postDes;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
