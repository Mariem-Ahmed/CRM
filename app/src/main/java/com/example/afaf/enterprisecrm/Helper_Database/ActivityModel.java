package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by ibrahimfouad on 02/10/16.
 */
public class ActivityModel {
    private int id;
    private String activitySubject;
    private String activityStartdate;
    private String startHour;
    private String startMinute;
    private String durationHours;
    private String activityType;
    private String activityStatus;
    private String relatedLead;
    private String activityId;
    private String actRelatdLead;
    private String leadStatus;
    private String description;
    private String assigeTo;

   // private String activityClientId;

    public ActivityModel() {
    }

    public ActivityModel(int id, String activitySubject, String activityStartdate, String startHour, String startMinute, String durationHours, String activityType, String activityStatus, String relatedLead, String activityId, String actRelatdLead, String leadStatus, String description, String assigeTo) {
        this.id = id;
        this.activitySubject = activitySubject;
        this.activityStartdate = activityStartdate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.durationHours = durationHours;
        this.activityType = activityType;
        this.activityStatus = activityStatus;
        this.relatedLead = relatedLead;
        this.activityId = activityId;
        this.actRelatdLead = actRelatdLead;
        this.leadStatus = leadStatus;
        this.description = description;
        this.assigeTo = assigeTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivitySubject() {
        return activitySubject;
    }

    public void setActivitySubject(String activitySubject) {
        this.activitySubject = activitySubject;
    }

    public String getActivityStartdate() {
        return activityStartdate;
    }

    public void setActivityStartdate(String activityStartdate) {
        this.activityStartdate = activityStartdate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(String durationHours) {
        this.durationHours = durationHours;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getRelatedLead() {
        return relatedLead;
    }

    public void setRelatedLead(String relatedLead) {
        this.relatedLead = relatedLead;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActRelatdLead() {
        return actRelatdLead;
    }

    public void setActRelatdLead(String actRelatdLead) {
        this.actRelatdLead = actRelatdLead;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigeTo() {
        return assigeTo;
    }

    public void setAssigeTo(String assigeTo) {
        this.assigeTo = assigeTo;
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "id=" + id +
                ", activitySubject='" + activitySubject + '\'' +
                ", activityStartdate='" + activityStartdate + '\'' +
                ", startHour='" + startHour + '\'' +
                ", startMinute='" + startMinute + '\'' +
                ", durationHours='" + durationHours + '\'' +
                ", activityType='" + activityType + '\'' +
                ", activityStatus='" + activityStatus + '\'' +
                ", relatedLead='" + relatedLead + '\'' +
                ", activityId='" + activityId + '\'' +
                ", actRelatdLead='" + actRelatdLead + '\'' +
                ", leadStatus='" + leadStatus + '\'' +
                ", description='" + description + '\'' +
                ", assigeTo='" + assigeTo + '\'' +
                '}';
    }
}
