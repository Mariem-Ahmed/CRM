package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 24/10/16.
 */

public class activity_Type_spinner_model {

    private int id;
    private String activitykey;
    private String activityname;
    private String activityId;



    public activity_Type_spinner_model() {
    }


    public activity_Type_spinner_model(int id, String activitykey, String activityId, String activityname) {
        this.id = id;
        this.activitykey = activitykey;
        this.activityId = activityId;
        this.activityname = activityname;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivitykey() {
        return activitykey;
    }

    public void setActivitykey(String activitykey) {
        this.activitykey = activitykey;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    @Override
    public String toString() {
        return "activity_Type_spinner_model{" +
                "id=" + id +
                ", activitykey='" + activitykey + '\'' +
                ", activityname='" + activityname + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
