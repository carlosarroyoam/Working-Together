package com.workingtogether.workingtogether.entity;

public class Note {
    private int UIDNOTE;
    private int UIDSUBJECT;
    private int UIDSTUDENT;
    private double NOTE;
    private String SUBJECT;
    private String created_at;
    private String updated_at;

    public int getUIDNOTE() {
        return UIDNOTE;
    }

    public void setUIDNOTE(int UIDNOTE) {
        this.UIDNOTE = UIDNOTE;
    }

    public int getUIDSUBJECT() {
        return UIDSUBJECT;
    }

    public void setUIDSUBJECT(int UIDSUBJECT) {
        this.UIDSUBJECT = UIDSUBJECT;
    }

    public int getUIDSTUDENT() {
        return UIDSTUDENT;
    }

    public void setUIDSTUDENT(int UIDSTUDENT) {
        this.UIDSTUDENT = UIDSTUDENT;
    }

    public double getNOTE() {
        return NOTE;
    }

    public void setNOTE(double NOTE) {
        this.NOTE = NOTE;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String deliverdate) {
        this.created_at = deliverdate;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}
