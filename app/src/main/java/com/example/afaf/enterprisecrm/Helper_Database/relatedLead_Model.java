package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 19/01/17.
 */

public class relatedLead_Model {

    private int id;
    private String rLeadName;
    private String rLeadId;

    public relatedLead_Model() {
    }

    public relatedLead_Model(int id, String rLeadName, String rLeadId) {
        this.id = id;
        this.rLeadName = rLeadName;
        this.rLeadId = rLeadId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getrLeadName() {
        return rLeadName;
    }

    public void setrLeadName(String rLeadName) {
        this.rLeadName = rLeadName;
    }

    public String getrLeadId() {
        return rLeadId;
    }

    public void setrLeadId(String rLeadId) {
        this.rLeadId = rLeadId;
    }

    @Override
    public String toString() {
        return "relatedLead_Model{" +
                "id=" + id +
                ", rLeadName='" + rLeadName + '\'' +
                ", rLeadId='" + rLeadId + '\'' +
                '}';
    }
}
