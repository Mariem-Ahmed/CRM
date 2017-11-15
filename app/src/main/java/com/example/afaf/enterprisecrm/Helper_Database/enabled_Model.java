package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by ibrahimfouad on 8/23/2017.
 */

public class enabled_Model {
    int enbled_ID;
    String enabled_name;
    String lead_access_ID;

    public enabled_Model() {
    }

    public enabled_Model(int enbled_ID, String enabled_name, String lead_access_ID) {
        this.enbled_ID = enbled_ID;
        this.enabled_name = enabled_name;
        this.lead_access_ID = lead_access_ID;
    }

    public int getEnbled_ID() {
        return enbled_ID;
    }

    public void setEnbled_ID(int enbled_ID) {
        this.enbled_ID = enbled_ID;
    }

    public String getEnabled_name() {
        return enabled_name;
    }

    public void setEnabled_name(String enabled_name) {
        this.enabled_name = enabled_name;
    }

    public String getLead_access_ID() {
        return lead_access_ID;
    }

    public void setLead_access_ID(String lead_access_ID) {
        this.lead_access_ID = lead_access_ID;
    }

    @Override
    public String toString() {
        return "enabled_Model{" +
                "enbled_ID=" + enbled_ID +
                ", enabled_name='" + enabled_name + '\'' +
                ", lead_access_ID='" + lead_access_ID + '\'' +
                '}';
    }
}
