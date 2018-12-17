package com.workingtogether.workingtogether.models;

public class Activity {
    private int UIDACTIVITY;
    private String TITLE;
	private String DESCRIPTION;
	private String DELIVERDATE;
    private String PUBLISHDATE;

    public int getUIDACTIVITY() {
        return UIDACTIVITY;
    }

    public void setUIDACTIVITY(int UIDACTIVITY) {
        this.UIDACTIVITY = UIDACTIVITY;
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
