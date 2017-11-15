package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by enterprise on 19/01/17.
 */

public class project_Model {

    private int id;
    private String projectSearchkey;
    private String projectName;
    private String projectId;

    public project_Model() {
    }

    public project_Model(int id, String projectSearchkey, String projectName, String projectId) {
        this.id = id;
        this.projectSearchkey = projectSearchkey;
        this.projectName = projectName;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectSearchkey() {
        return projectSearchkey;
    }

    public void setProjectSearchkey(String projectSearchkey) {
        this.projectSearchkey = projectSearchkey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "project_Model{" +
                "id=" + id +
                ", projectSearchkey='" + projectSearchkey + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
