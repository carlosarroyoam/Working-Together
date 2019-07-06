package com.workingtogether.android.entity;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class Notification {

    private int UIDNOTIFICATION;
    private String TITLE;
    private String DESCRIPTION;
    private String NOTIFICATIONTYPE;
    private String created_at;
    private String updated_at;
    private int UIDRESOURSE;

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

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String PUBLISHDATE) {
        this.created_at = PUBLISHDATE;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUIDRESOURSE() {
        return UIDRESOURSE;
    }

    public void setUIDRESOURSE(int UIDRESOURSE) {
        this.UIDRESOURSE = UIDRESOURSE;
    }

}
