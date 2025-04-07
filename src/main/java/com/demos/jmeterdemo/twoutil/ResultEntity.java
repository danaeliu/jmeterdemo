package com.demos.jmeterdemo.twoutil;

import java.util.List;

/**
 * 结果返回的实体
 * @author liujuan
 */
public class ResultEntity {
    private String projectName;
    //private int bugcount;
    private String url;

    public ResultEntity() {

    }

    public ResultEntity(String projectName,String url) {
        this.projectName = projectName;
        this.url = url;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getUrl() {
        return url;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
