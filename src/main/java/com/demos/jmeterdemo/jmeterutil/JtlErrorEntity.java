package com.demos.jmeterdemo.jmeterutil;

public class JtlErrorEntity {

    private String requesturl;
    private String responsemsg;
//    private String responsetime;
//    private String requesttime;

    public JtlErrorEntity() {
    }

    public JtlErrorEntity(String requesturl, String responsemsg) {
        this.requesturl = requesturl;
        this.responsemsg = responsemsg;
    }

    public String getRequesturl() {
        return requesturl;
    }

    public String getResponsemsg() {
        return responsemsg;
    }

    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl;
    }

    public void setResponsemsg(String responsemsg) {
        this.responsemsg = responsemsg;
    }
}
