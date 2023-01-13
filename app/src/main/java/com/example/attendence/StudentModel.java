package com.example.attendence;

public class StudentModel {

    //student details

    String addstudentname;
    String addstudentusn,batch;

    public StudentModel(String addstudentbatch, String addstudentname, String addstudentusn) {
        this.batch = addstudentbatch;
        this.addstudentname = addstudentname;
        this.addstudentusn = addstudentusn;
    }

    public String getAddstudentname() {
        return addstudentname;
    }

    public void setAddstudentname(String addstudentname) {
        this.addstudentname = addstudentname;
    }

    public String getAddstudentusn() {
        return addstudentusn;
    }

    public void setAddstudentusn(String addstudentusn) {
        this.addstudentusn = addstudentusn;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public StudentModel() {

    }

}
