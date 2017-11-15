package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 19/01/17.
 */

public class round_Model {

    private int id;
    private String roundSearchkey;
    private String roundName;
    private String roundId;

    public round_Model() {
    }

    public round_Model(int id, String roundSearchkey, String roundName, String roundId) {
        this.id = id;
        this.roundSearchkey = roundSearchkey;
        this.roundName = roundName;
        this.roundId = roundId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoundSearchkey() {
        return roundSearchkey;
    }

    public void setRoundSearchkey(String roundSearchkey) {
        this.roundSearchkey = roundSearchkey;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    @Override
    public String toString() {
        return "round_Model{" +
                "id=" + id +
                ", roundSearchkey='" + roundSearchkey + '\'' +
                ", roundName='" + roundName + '\'' +
                ", roundId='" + roundId + '\'' +
                '}';
    }
}
