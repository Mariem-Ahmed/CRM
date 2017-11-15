package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 25/10/16.
 */

public class leadSource_spinner_model {

    private int id;
    private String lSTranslationName;
    private String lSId;
    private String lSOrder;

    public leadSource_spinner_model() {
    }

    public leadSource_spinner_model(int id, String lSTranslationName, String lSId, String lSOrder) {
        this.id = id;
        this.lSTranslationName = lSTranslationName;
        this.lSId = lSId;
        this.lSOrder = lSOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getlSTranslationName() {
        return lSTranslationName;
    }

    public void setlSTranslationName(String lSTranslationName) {
        this.lSTranslationName = lSTranslationName;
    }

    public String getlSId() {
        return lSId;
    }

    public void setlSId(String lSId) {
        this.lSId = lSId;
    }

    public String getlSOrder() {
        return lSOrder;
    }

    public void setlSOrder(String lSOrder) {
        this.lSOrder = lSOrder;
    }

    @Override
    public String toString() {
        return "leadSource_spinner_model{" +
                "id=" + id +
                ", lSTranslationName='" + lSTranslationName + '\'' +
                ", lSId='" + lSId + '\'' +
                ", lSOrder='" + lSOrder + '\'' +
                '}';
    }
}
