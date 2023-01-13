package com.example.attendence;

public class BatchModel {

    String addbatch;
    String addsem;
    String subject;


    public BatchModel(String addbatch, String addsem) {
        this.addbatch = addbatch;
        this.addsem = addsem;
    }

    public String getAddbatch() {
        return addbatch;
    }

    public void setAddbatch(String addbatch) {
        this.addbatch = addbatch;
    }

    public String getAddsem() {
        return addsem;
    }

    public void setAddsem(String addsem) {
        this.addsem = addsem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }




    public BatchModel() {

    }
}



