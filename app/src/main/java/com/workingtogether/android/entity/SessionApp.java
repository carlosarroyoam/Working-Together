package com.workingtogether.android.entity;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SessionApp {

    private int UIDUSER;
    private int SESSTATE;
    private String TYPEUSER;

    public int getUIDUSER() {
        return UIDUSER;
    }

    public void setUIDUSER(int UIDUSER) {
        this.UIDUSER = UIDUSER;
    }

    public int getSESSTATE() {
        return SESSTATE;
    }

    public void setSESSTATE(int SESSTATE) {
        this.SESSTATE = SESSTATE;
    }

    public String getTYPEUSER() {
        return TYPEUSER;
    }

    public void setTYPEUSER(String TYPEUSER) {
        this.TYPEUSER = TYPEUSER;
    }

}
