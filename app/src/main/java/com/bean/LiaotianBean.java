package com.bean;

public class LiaotianBean {
    private String username;
    private String userId;
    private String myId;
    private String context;

    public LiaotianBean(String username, String userId, String myId, String context) {
        this.username = username;
        this.userId = userId;
        this.myId = myId;
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
