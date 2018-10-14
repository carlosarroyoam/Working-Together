package com.workingtogether.workingtogether.obj;

public class Message {
    private int UIDMESSAGE;
    private int UIDCONVERSATION;
    private int UIDUSERFROM;
    private int UIDUSERTO;
    private String DATA;
    private String SENDDATE;
    private String DELIVERDATE;
    private String READEDDATE;
    private int SENDSTATE;
    private int DELIVERSTATE;
    private int READEDSTATE;

    public int getUIDMESSAGE() {
        return UIDMESSAGE;
    }

    public void setUIDMESSAGE(int UIDMESSAGE) {
        this.UIDMESSAGE = UIDMESSAGE;
    }

    public int getUIDCONVERSATION() {
        return UIDCONVERSATION;
    }

    public void setUIDCONVERSATION(int UIDCONVERSATION) {
        this.UIDCONVERSATION = UIDCONVERSATION;
    }

    public int getUIDUSERFROM() {
        return UIDUSERFROM;
    }

    public void setUIDUSERFROM(int UIDUSERFROM) {
        this.UIDUSERFROM = UIDUSERFROM;
    }

    public int getUIDUSERTO() {
        return UIDUSERTO;
    }

    public void setUIDUSERTO(int UIDUSERTO) {
        this.UIDUSERTO = UIDUSERTO;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getSENDDATE() {
        return SENDDATE;
    }

    public void setSENDDATE(String SENDDATE) {
        this.SENDDATE = SENDDATE;
    }

    public String getDELIVERDATE() {
        return DELIVERDATE;
    }

    public void setDELIVERDATE(String DELIVERDATE) {
        this.DELIVERDATE = DELIVERDATE;
    }

    public String getREADEDDATE() {
        return READEDDATE;
    }

    public void setREADEDDATE(String READEDDATE) {
        this.READEDDATE = READEDDATE;
    }

    public int getSENDSTATE() {
        return SENDSTATE;
    }

    public void setSENDSTATE(int SENDSTATE) {
        this.SENDSTATE = SENDSTATE;
    }

    public int getDELIVERSTATE() {
        return DELIVERSTATE;
    }

    public void setDELIVERSTATE(int DELIVERSTATE) {
        this.DELIVERSTATE = DELIVERSTATE;
    }

    public int getREADEDSTATE() {
        return READEDSTATE;
    }

    public void setREADEDSTATE(int READEDSTATE) {
        this.READEDSTATE = READEDSTATE;
    }
}
