package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 19/01/17.
 */

public class interestedIn_Model {

    private int id;
    private String interestedInTranslationName;
    private String interestedInId;
    private String interestedInOrder;

    public interestedIn_Model() {
    }

    public interestedIn_Model(int id, String interestedInTranslationName, String interestedInId, String interestedInOrder) {
        this.id = id;
        this.interestedInTranslationName = interestedInTranslationName;
        this.interestedInId = interestedInId;
        this.interestedInOrder = interestedInOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterestedInTranslationName() {
        return interestedInTranslationName;
    }

    public void setInterestedInTranslationName(String interestedInTranslationName) {
        this.interestedInTranslationName = interestedInTranslationName;
    }

    public String getInterestedInId() {
        return interestedInId;
    }

    public void setInterestedInId(String interestedInId) {
        this.interestedInId = interestedInId;
    }

    public String getInterestedInOrder() {
        return interestedInOrder;
    }

    public void setInterestedInOrder(String interestedInOrder) {
        this.interestedInOrder = interestedInOrder;
    }

    @Override
    public String toString() {
        return "interestedIn_Model{" +
                "id=" + id +
                ", interestedInTranslationName='" + interestedInTranslationName + '\'' +
                ", interestedInId='" + interestedInId + '\'' +
                ", interestedInOrder='" + interestedInOrder + '\'' +
                '}';
    }
}
