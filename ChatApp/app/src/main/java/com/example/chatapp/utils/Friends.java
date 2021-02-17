package com.example.chatapp.utils;

public class Friends {
    private String bio,profileImageUrl,username,status;

    public Friends(String bio, String profileImageUrl, String username, String status) {
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
        this.status = status;
    }

    public Friends() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
