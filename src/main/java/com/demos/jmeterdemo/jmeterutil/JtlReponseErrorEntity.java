package com.demos.jmeterdemo.jmeterutil;

import java.util.List;

public class JtlReponseErrorEntity {
    private int total;
    private int successtotal;
    private int failtotal;
    private List<JtlErrorEntity> data;

    public JtlReponseErrorEntity(){
    }

    public JtlReponseErrorEntity(int total, int successtotal, int failtotal, List<JtlErrorEntity> data) {
        this.total = total;
        this.successtotal = successtotal;
        this.failtotal = failtotal;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public int getSuccesstotal() {
        return successtotal;
    }

    public int getFailtotal() {
        return failtotal;
    }

    public List<JtlErrorEntity> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSuccesstotal(int successtotal) {
        this.successtotal = successtotal;
    }

    public void setFailtotal(int failtotal) {
        this.failtotal = failtotal;
    }

    public void setData(List<JtlErrorEntity> data) {
        this.data = data;
    }
}
