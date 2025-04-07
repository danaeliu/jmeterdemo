package com.demos.jmeterdemo.zentao;

import java.util.Date;

public class ZentaoResult {
    private String bugid;
    private String bugtitle;
    private String severity;//严重程度
    private String pri;//优先级
    private String statusName;//状态
    private String qarealname;//创建人
    private String devrealname;//指派人
    private String openedDate;//创建时间
    private String resolvedDate;//解决时间
    private String resolution;//解决方案

    public ZentaoResult() {

    }

    public ZentaoResult(String bugid, String bugtitle, String severity, String pri, String statusName, String qarealname, String devrealname, String openedDate, String resolvedDate, String resolution) {
        this.bugid = bugid;
        this.bugtitle = bugtitle;
        this.severity = severity;
        this.pri = pri;
        this.statusName = statusName;
        this.qarealname = qarealname;
        this.devrealname = devrealname;
        this.openedDate = openedDate;
        this.resolvedDate = resolvedDate;
        this.resolution = resolution;
    }

    public String getBugid() {
        return bugid;
    }

    public String getBugtitle() {
        return bugtitle;
    }

    public String getSeverity() {
        return severity;
    }

    public String getPri() {
        return pri;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getQarealname() {
        return qarealname;
    }

    public String getDevrealname() {
        return devrealname;
    }

    public String getOpenedDate() {
        return openedDate;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setBugid(String bugid) {
        this.bugid = bugid;
    }

    public void setBugtitle(String bugtitle) {
        this.bugtitle = bugtitle;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setQarealname(String qarealname) {
        this.qarealname = qarealname;
    }

    public void setDevrealname(String devrealname) {
        this.devrealname = devrealname;
    }

    public void setOpenedDate(String openedDate) {
        this.openedDate = openedDate;
    }

    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
