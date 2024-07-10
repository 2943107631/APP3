package com.bean;

import java.io.Serializable;

public class JianzhiBean implements Serializable {
    private int id;
    private String userId;
    private String username;
    private String title;
    private String content;
    private String image;
    private String location;
    private String price;
    private String druation;
    private String type;

    public JianzhiBean(int id, String userId, String username, String title, String content, String image, String location, String price, String druation, String type) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.image = image;
        this.location = location;
        this.price = price;
        this.druation = druation;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDruation() {
        return druation;
    }

    public void setDruation(String druation) {
        this.druation = druation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}