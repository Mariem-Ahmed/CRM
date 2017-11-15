package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 19/01/17.
 */

public class nationality_Model {

    private int id;
    private String isoCountrycode;
    private String countryName;
    private String nationalityId;

    public nationality_Model() {
    }

    public nationality_Model(int id, String isoCountrycode, String countryName, String nationalityId) {
        this.id = id;
        this.isoCountrycode = isoCountrycode;
        this.countryName = countryName;
        this.nationalityId = nationalityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsoCountrycode() {
        return isoCountrycode;
    }

    public void setIsoCountrycode(String isoCountrycode) {
        this.isoCountrycode = isoCountrycode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    @Override
    public String toString() {
        return "nationality_Model{" +
                "id=" + id +
                ", isoCountrycode='" + isoCountrycode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", nationalityId='" + nationalityId + '\'' +
                '}';
    }
}
