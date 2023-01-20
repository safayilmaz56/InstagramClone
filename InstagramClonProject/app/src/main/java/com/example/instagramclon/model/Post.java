package com.example.instagramclon.model;

public class Post {
    public String email;
    public String comment;
    public String downloadUrl;
    public String date;
    public String userName;
    public String fullName;
    public String pphoto;



    public Post(String email, String comment, String downloadUrl,String date,String userName,String fullName,String pphoto) {
        this.email = email;
        this.comment = comment;
        this.downloadUrl = downloadUrl;
        this.userName=userName;
        this.fullName=fullName;
        this.date=date;
        this.pphoto=pphoto;

    }
}
