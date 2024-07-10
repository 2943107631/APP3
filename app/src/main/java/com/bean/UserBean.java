package com.bean;

public class UserBean {
    private String id;
    private String username;
    private String phone;
    private String school;
    private int gender;

    public UserBean(String id, String username, String phone, String school, int gender) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.school = school;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
