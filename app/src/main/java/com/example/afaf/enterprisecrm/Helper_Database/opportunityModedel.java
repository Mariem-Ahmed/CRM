package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 11/10/16.
 */

public class opportunityModedel {
    private int id;
    private String oppoSubject;
    private String oppoCloseDate;
    private String oppoAmount;
    private String oppoProbablity;
    private String oppoRelatedLead;
    private String oppoAssignedto;
    private String oppoID;
    private String oppoRelatedLeadID;
    private String oppoAssignedToID;


    public opportunityModedel() {
    }


    public opportunityModedel(int id, String oppoSubject, String oppoCloseDate, String oppoAmount, String oppoProbablity, String oppoRelatedLead, String oppoAssignedto, String oppoID, String oppoRelatedLeadID, String oppoAssignedToID) {
        this.id = id;
        this.oppoSubject = oppoSubject;
        this.oppoCloseDate = oppoCloseDate;
        this.oppoAmount = oppoAmount;
        this.oppoProbablity = oppoProbablity;
        this.oppoRelatedLead = oppoRelatedLead;
        this.oppoAssignedto = oppoAssignedto;
        this.oppoID = oppoID;
        this.oppoRelatedLeadID = oppoRelatedLeadID;
        this.oppoAssignedToID = oppoAssignedToID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOppoSubject() {
        return oppoSubject;
    }

    public void setOppoSubject(String oppoSubject) {
        this.oppoSubject = oppoSubject;
    }

    public String getOppoCloseDate() {
        return oppoCloseDate;
    }

    public void setOppoCloseDate(String oppoCloseDate) {
        this.oppoCloseDate = oppoCloseDate;
    }

    public String getOppoAmount() {
        return oppoAmount;
    }

    public void setOppoAmount(String oppoAmount) {
        this.oppoAmount = oppoAmount;
    }

    public String getOppoProbablity() {
        return oppoProbablity;
    }

    public void setOppoProbablity(String oppoProbablity) {
        this.oppoProbablity = oppoProbablity;
    }

    public String getOppoRelatedLead() {
        return oppoRelatedLead;
    }

    public void setOppoRelatedLead(String oppoRelatedLead) {
        this.oppoRelatedLead = oppoRelatedLead;
    }

    public String getOppoAssignedto() {
        return oppoAssignedto;
    }

    public void setOppoAssignedto(String oppoAssignedto) {
        this.oppoAssignedto = oppoAssignedto;
    }

    public String getOppoID() {
        return oppoID;
    }

    public void setOppoID(String oppoID) {
        this.oppoID = oppoID;
    }

    public String getOppoRelatedLeadID() {
        return oppoRelatedLeadID;
    }

    public void setOppoRelatedLeadID(String oppoRelatedLeadID) {
        this.oppoRelatedLeadID = oppoRelatedLeadID;
    }

    public String getOppoAssignedToID() {
        return oppoAssignedToID;
    }

    public void setOppoAssignedToID(String oppoAssignedToID) {
        this.oppoAssignedToID = oppoAssignedToID;
    }


    @Override
    public String toString() {
        return "opportunityModedel{" +
                "id=" + id +
                ", oppoSubject='" + oppoSubject + '\'' +
                ", oppoCloseDate='" + oppoCloseDate + '\'' +
                ", oppoAmount='" + oppoAmount + '\'' +
                ", oppoProbablity='" + oppoProbablity + '\'' +
                ", oppoRelatedLead='" + oppoRelatedLead + '\'' +
                ", oppoAssignedto='" + oppoAssignedto + '\'' +
                ", oppoID='" + oppoID + '\'' +
                ", oppoRelatedLeadID='" + oppoRelatedLeadID + '\'' +
                ", oppoAssignedToID='" + oppoAssignedToID + '\'' +
                '}';
    }
}
