package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 08/01/17.
 */

public class callRecord_Model {
    private int id;
    private String recordName;
    private String recordPath;
    private String cashDFlag;

    public callRecord_Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getCashDFlag() {
        return cashDFlag;
    }

    public void setCashDFlag(String cashDFlag) {
        this.cashDFlag = cashDFlag;
    }

    @Override
    public String toString() {
        return "callRecord_Model{" +
                "id=" + id +
                ", recordName='" + recordName + '\'' +
                ", recordPath='" + recordPath + '\'' +
                ", cashDFlag='" + cashDFlag + '\'' +
                '}';
    }
}
