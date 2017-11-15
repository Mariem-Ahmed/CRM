package com.example.afaf.enterprisecrm;

/**
 * Created by enterprise on 19/01/17.
 */

public class title_Model {
    private int id;
    private String titleTranslationName;
    private String titleId;
    private String titleOrder;

    public title_Model() {
    }

    public title_Model(int id, String titleTranslationName, String titleId, String titleOrder) {
        this.id = id;
        this.titleTranslationName = titleTranslationName;
        this.titleId = titleId;
        this.titleOrder = titleOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleTranslationName() {
        return titleTranslationName;
    }

    public void setTitleTranslationName(String titleTranslationName) {
        this.titleTranslationName = titleTranslationName;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitleOrder() {
        return titleOrder;
    }

    public void setTitleOrder(String titleOrder) {
        this.titleOrder = titleOrder;
    }

    @Override
    public String toString() {
        return "title_Model{" +
                "id=" + id +
                ", titleTranslationName='" + titleTranslationName + '\'' +
                ", titleId='" + titleId + '\'' +
                ", titleOrder='" + titleOrder + '\'' +
                '}';
    }
}
