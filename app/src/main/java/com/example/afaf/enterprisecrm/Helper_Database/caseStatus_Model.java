package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 17/01/17.
 */

public class caseStatus_Model {

    private int id;
    private String caseStatusValue;
    private String caseStatusName;
    private String caseStatusId;

    public caseStatus_Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseStatusValue() {
        return caseStatusValue;
    }

    public void setCaseStatusValue(String caseStatusValue) {
        this.caseStatusValue = caseStatusValue;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getCaseStatusId() {
        return caseStatusId;
    }

    public void setCaseStatusId(String caseStatusId) {
        this.caseStatusId = caseStatusId;
    }

    @Override
    public String toString() {
        return "caseStatus_Model{" +
                "id=" + id +
                ", caseStatusValue='" + caseStatusValue + '\'' +
                ", caseStatusName='" + caseStatusName + '\'' +
                ", caseStatusId='" + caseStatusId + '\'' +
                '}';
    }
}
