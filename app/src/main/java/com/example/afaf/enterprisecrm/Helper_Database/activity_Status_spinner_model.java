package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 26/10/16.
 */

public class activity_Status_spinner_model {

    private int id;
    private String activityStatusname;
    private String activityStatusId;
    private String activityStatusSearchKey;


    public activity_Status_spinner_model() {
    }

    public activity_Status_spinner_model(int id, String activityStatusname, String activityStatusId, String activityStatusSearchKey) {
        this.id = id;
        this.activityStatusname = activityStatusname;
        this.activityStatusId = activityStatusId;
        this.activityStatusSearchKey = activityStatusSearchKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityStatusname() {
        return activityStatusname;
    }

    public void setActivityStatusname(String activityStatusname) {
        this.activityStatusname = activityStatusname;
    }

    public String getActivityStatusId() {
        return activityStatusId;
    }

    public void setActivityStatusId(String activityStatusId) {
        this.activityStatusId = activityStatusId;
    }

    public String getActivityStatusSearchKey() {
        return activityStatusSearchKey;
    }

    public void setActivityStatusSearchKey(String activityStatusSearchKey) {
        this.activityStatusSearchKey = activityStatusSearchKey;
    }

    @Override
    public String toString() {
        return "activity_Status_spinner_model{" +
                "id=" + id +
                ", activityStatusname='" + activityStatusname + '\'' +
                ", activityStatusId='" + activityStatusId + '\'' +
                ", activityStatusSearchKey='" + activityStatusSearchKey + '\'' +
                '}';
    }
}
