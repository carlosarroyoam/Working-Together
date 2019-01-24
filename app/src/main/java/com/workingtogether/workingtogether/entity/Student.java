package com.workingtogether.workingtogether.entity;

public class Student {
    private int UIDSTUDENT;
    private String NAME;
    private String LASTNAME;
    private String GRADE;
    private String GROUP;
    private int UIDPARENT;
    private int UIDSCHOOL;

    public int getUIDSTUDENT() {
        return UIDSTUDENT;
    }

    public void setUIDSTUDENT(int UIDSTUDENT) {
        this.UIDSTUDENT = UIDSTUDENT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getGROUP() {
        return GROUP;
    }

    public void setGROUP(String GROUP) {
        this.GROUP = GROUP;
    }

    public int getUIDPARENT() {
        return UIDPARENT;
    }

    public void setUIDPARENT(int UIDPARENT) {
        this.UIDPARENT = UIDPARENT;
    }

    public int getUIDSCHOOL() {
        return UIDSCHOOL;
    }

    public void setUIDSCHOOL(int UIDSCHOOL) {
        this.UIDSCHOOL = UIDSCHOOL;
    }
}
