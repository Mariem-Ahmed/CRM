package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 25/10/16.
 */

public class assignedTo_spinner_model {

    private int id;
    private String userName;
    private String userId;

    public assignedTo_spinner_model() {
    }

    public assignedTo_spinner_model(int id, String userName, String userId) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "assignedTo_spinner_model{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
