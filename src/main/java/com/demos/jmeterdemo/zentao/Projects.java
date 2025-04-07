package com.demos.jmeterdemo.zentao;

import java.util.List;

public class Projects {
    private String projectName;
    private int projectId;
    private List<Integer> execution;

    public Projects() {

    }
    public Projects(String projectName, int projectId, List<Integer> execution) {
        this.projectName = projectName;
        this.projectId = projectId;
        this.execution = execution;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setExecution(List<Integer> execution) {
        this.execution = execution;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public List<Integer> getExecution() {
        return execution;
    }
}
