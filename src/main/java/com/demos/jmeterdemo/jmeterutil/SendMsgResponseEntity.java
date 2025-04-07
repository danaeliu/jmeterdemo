package com.demos.jmeterdemo.jmeterutil;

public class SendMsgResponseEntity {
    private String key;
    private String jmeterHome;
    private String jmxFilePath;
    private String reportDir;

    public SendMsgResponseEntity(String key) {
        this.key = key;
    }

    public SendMsgResponseEntity(String key, String jmeterHome, String jmxFilePath, String reportDir) {
        this.key = key;
        this.jmeterHome = jmeterHome;
        this.jmxFilePath = jmxFilePath;
        this.reportDir = reportDir;
    }

    public String getKey() {
        return key;
    }

    public String getJmeterHome() {
        return jmeterHome;
    }

    public String getJmxFilePath() {
        return jmxFilePath;
    }

    public String getReportDir() {
        return reportDir;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setJmeterHome(String jmeterHome) {
        this.jmeterHome = jmeterHome;
    }

    public void setJmxFilePath(String jmxFilePath) {
        this.jmxFilePath = jmxFilePath;
    }

    public void setReportDir(String reportDir) {
        this.reportDir = reportDir;
    }
}
