package com.workingtogether.workingtogether.obj;

public class Homework {
    private int UIDHOMEWORK;
    private String TITLE;
    private String DESCRIPTION;
    private String DELIVERDATE;
    private String PUBLISHDATE;

    public int getUIDHOMEWORK() {
        return UIDHOMEWORK;
    }

    public void setUIDHOMEWORK(int UIDHOMEWORK) {
        this.UIDHOMEWORK = UIDHOMEWORK;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDELIVERDATE() {
        return DELIVERDATE;
    }

    public void setDELIVERDATE(String DELIVERDATE) {
        this.DELIVERDATE = DELIVERDATE;
    }

    public String getPUBLISHDATE() {
        return PUBLISHDATE;
    }

    public void setPUBLISHDATE(String PUBLISHDATE) {
        this.PUBLISHDATE = PUBLISHDATE;
    }
}
