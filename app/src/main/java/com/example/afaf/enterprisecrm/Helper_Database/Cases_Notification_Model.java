package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 26/12/16.
 */

public class Cases_Notification_Model {
    private int id;
    private String notifSubject;
    private String notifDesc;
    private String notifSentFlag;
    private String notifID;
    private String updatedDate;
    private String createdDate;


    public Cases_Notification_Model() {

    }


    public Cases_Notification_Model(int id, String notifSubject, String notifDesc, String notifSentFlag, String notifID, String updatedDate, String createdDate) {
        this.id = id;
        this.notifSubject = notifSubject;
        this.notifDesc = notifDesc;
        this.notifSentFlag = notifSentFlag;
        this.notifID = notifID;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotifSubject() {
        return notifSubject;
    }

    public void setNotifSubject(String notifSubject) {
        this.notifSubject = notifSubject;
    }

    public String getNotifDesc() {
        return notifDesc;
    }

    public void setNotifDesc(String notifDesc) {
        this.notifDesc = notifDesc;
    }

    public String getNotifSentFlag() {
        return notifSentFlag;
    }

    public void setNotifSentFlag(String notifSentFlag) {
        this.notifSentFlag = notifSentFlag;
    }

    public String getNotifID() {
        return notifID;
    }

    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Cases_Notification_Model{" +
                "id=" + id +
                ", notifSubject='" + notifSubject + '\'' +
                ", notifDesc='" + notifDesc + '\'' +
                ", notifSentFlag='" + notifSentFlag + '\'' +
                ", notifID='" + notifID + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

