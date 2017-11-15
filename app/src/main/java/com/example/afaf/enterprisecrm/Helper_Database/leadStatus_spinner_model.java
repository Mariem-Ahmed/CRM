package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 25/10/16.
 */

public class leadStatus_spinner_model {

    private int id;
    private String leadStatusValue;
    private String leadStatusName;
    private String leadStatusId;

    public leadStatus_spinner_model() {
    }

    public leadStatus_spinner_model(int id, String leadStatusValue, String leadStatusName, String leadStatusId) {
        this.id = id;
        this.leadStatusValue = leadStatusValue;
        this.leadStatusName = leadStatusName;
        this.leadStatusId = leadStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeadStatusValue() {
        return leadStatusValue;
    }

    public void setLeadStatusValue(String leadStatusValue) {
        this.leadStatusValue = leadStatusValue;
    }

    public String getLeadStatusName() {
        return leadStatusName;
    }

    public void setLeadStatusName(String leadStatusName) {
        this.leadStatusName = leadStatusName;
    }

    public String getLeadStatusId() {
        return leadStatusId;
    }

    public void setLeadStatusId(String leadStatusId) {
        this.leadStatusId = leadStatusId;
    }

    @Override
    public String toString() {
        return "leadStatus_spinner_model{" +
                "id=" + id +
                ", leadStatusValue='" + leadStatusValue + '\'' +
                ", leadStatusName='" + leadStatusName + '\'' +
                ", leadStatusId='" + leadStatusId + '\'' +
                '}';
    }
}
