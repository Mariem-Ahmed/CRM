package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 25/10/16.
 */

public class casePriority_spinner_model {
    private int id;
    private String casePeriorityValue;
    private String casePeriorityName;
    private String casePeriorityId;

    public casePriority_spinner_model() {
    }

    public casePriority_spinner_model(int id, String casePeriorityName, String casePeriorityValue, String casePeriorityId) {
        this.id = id;
        this.casePeriorityName = casePeriorityName;
        this.casePeriorityValue = casePeriorityValue;
        this.casePeriorityId = casePeriorityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCasePeriorityValue() {
        return casePeriorityValue;
    }

    public void setCasePeriorityValue(String casePeriorityValue) {
        this.casePeriorityValue = casePeriorityValue;
    }

    public String getCasePeriorityName() {
        return casePeriorityName;
    }

    public void setCasePeriorityName(String casePeriorityName) {
        this.casePeriorityName = casePeriorityName;
    }

    public String getCasePeriorityId() {
        return casePeriorityId;
    }

    public void setCasePeriorityId(String casePeriorityId) {
        this.casePeriorityId = casePeriorityId;
    }

    @Override
    public String toString() {
        return "casePriority_spinner_model{" +
                "id=" + id +
                ", casePeriorityValue='" + casePeriorityValue + '\'' +
                ", casePeriorityName='" + casePeriorityName + '\'' +
                ", casePeriorityId='" + casePeriorityId + '\'' +
                '}';
    }
}
