package com.workingtogether.workingtogether.obj;

public class Notification {
    private int UIDNOTIFICATION;
    private String TITLE;
    private String DESCRIPTION;
    private String NOTIFICATIONTYPE;
    private String PUBLISHDATE;

    public int getUIDNOTIFICATION() {
        return UIDNOTIFICATION;
    }

    public void setUIDNOTIFICATION(int UIDNOTIFICATION) {
        this.UIDNOTIFICATION = UIDNOTIFICATION;
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

    public String getNOTIFICATIONTYPE() {
        return NOTIFICATIONTYPE;
    }

    public void setNOTIFICATIONTYPE(String NOTIFICATIONTYPE) {
        this.NOTIFICATIONTYPE = NOTIFICATIONTYPE;
    }

    public String getPUBLISHDATE() {
        return PUBLISHDATE;
    }

    public void setPUBLISHDATE(String PUBLISHDATE) {
        this.PUBLISHDATE = PUBLISHDATE;
    }

}
