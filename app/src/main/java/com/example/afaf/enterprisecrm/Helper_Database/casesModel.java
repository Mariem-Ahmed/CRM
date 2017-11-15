package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 11/10/16.
 */

public class casesModel {

    private int id;
    private String leadName;
    private String subjectName;
    private String timeSpent;
    private String priority;
    private String deadLine;
    private String caseID;
    private String status;
    private String assignedTo;
    private String caseRelatedLead;
    private String spenthours;
    private String spentmintues;
    private String caseActivity;
    private String caseActivityId;


    public casesModel() {

    }

    public casesModel(int id, String leadName, String subjectName, String timeSpent, String priority, String deadLine, String caseID, String status, String assignedTo, String caseRelatedLead, String spenthours, String spentmintues, String caseActivity, String caseActivityId) {
        this.id = id;
        this.leadName = leadName;
        this.subjectName = subjectName;
        this.timeSpent = timeSpent;
        this.priority = priority;
        this.deadLine = deadLine;
        this.caseID = caseID;
        this.status = status;
        this.assignedTo = assignedTo;
        this.caseRelatedLead = caseRelatedLead;
        this.spenthours = spenthours;
        this.spentmintues = spentmintues;
        this.caseActivity = caseActivity;
        this.caseActivityId = caseActivityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCaseRelatedLead() {
        return caseRelatedLead;
    }

    public void setCaseRelatedLead(String caseRelatedLead) {
        this.caseRelatedLead = caseRelatedLead;
    }

    public String getSpenthours() {
        return spenthours;
    }

    public void setSpenthours(String spenthours) {
        this.spenthours = spenthours;
    }

    public String getSpentmintues() {
        return spentmintues;
    }

    public void setSpentmintues(String spentmintues) {
        this.spentmintues = spentmintues;
    }

    public String getCaseActivity() {
        return caseActivity;
    }

    public void setCaseActivity(String caseActivity) {
        this.caseActivity = caseActivity;
    }

    public String getCaseActivityId() {
        return caseActivityId;
    }

    public void setCaseActivityId(String caseActivityId) {
        this.caseActivityId = caseActivityId;
    }

    @Override
    public String toString() {
        return "casesModel{" +
                "id=" + id +
                ", leadName='" + leadName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", timeSpent='" + timeSpent + '\'' +
                ", priority='" + priority + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", caseID='" + caseID + '\'' +
                ", status='" + status + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", caseRelatedLead='" + caseRelatedLead + '\'' +
                ", spenthours='" + spenthours + '\'' +
                ", spentmintues='" + spentmintues + '\'' +
                ", caseActivity='" + caseActivity + '\'' +
                ", caseActivityId='" + caseActivityId + '\'' +
                '}';
    }
}
