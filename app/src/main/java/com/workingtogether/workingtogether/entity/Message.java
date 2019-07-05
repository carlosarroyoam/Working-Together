package com.workingtogether.workingtogether.entity;

public class Message {

    private int UIDMESSAGE;
    private int UIDCONVERSATION;
    private int UIDUSERFROM;
    private int UIDUSERTO;
    private String DATA;
    private String created_at;
    private String updated_at;
    private String SENDDATE;
    private String DELIVERDATE;
    private String READEDDATE;
    private boolean SENDSTATE;
    private boolean DELIVERSTATE;
    private boolean READEDSTATE;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public boolean getSENDSTATE() {
        return SENDSTATE;
    }

    public void setSENDSTATE(boolean SENDSTATE) {
        this.SENDSTATE = SENDSTATE;
    }

    public boolean getDELIVERSTATE() {
        return DELIVERSTATE;
    }

    public void setDELIVERSTATE(boolean DELIVERSTATE) {
        this.DELIVERSTATE = DELIVERSTATE;
    }

    public boolean getREADEDSTATE() {
        return READEDSTATE;
    }

    public void setREADEDSTATE(boolean READEDSTATE) {
        this.READEDSTATE = READEDSTATE;
    }

}
