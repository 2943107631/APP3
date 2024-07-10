package com.bean;

import java.io.Serializable;

public class XiaoyuanBean implements Serializable {
    private int id;
    private String userid;
    private String username;
    private String title;
    private String content;
    private String image;
    private String degree;
    private String price;

    public XiaoyuanBean(int id, String userid, String username, String title, String content, String image, String degree, String price) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.title = title;
        this.content = content;
        this.image = image;
        this.degree = degree;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
