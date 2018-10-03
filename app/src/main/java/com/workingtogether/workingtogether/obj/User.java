package com.workingtogether.workingtogether.obj;

public class User {
    private int UIDUSER;
    private String NAME;
    private String LASTNAME;
    private String EMAIL;
    private String USERTYPE;

    public int getUIDUSER() {
        return UIDUSER;
    }

    public void setUIDUSER(int UIDTEACHER) {
        this.UIDUSER = UIDTEACHER;
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

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getUSERTYPE() {
        return USERTYPE;
    }

    public void setUSERTYPE(String USERTYPE) {
        this.USERTYPE = USERTYPE;
    }
}
