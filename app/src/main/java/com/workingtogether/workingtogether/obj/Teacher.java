package com.workingtogether.workingtogether.obj;

public class Teacher {
    private int UIDTEACHER;
    private String NAME;
    private String LASTNAME;
    private String EMAIL;

    public int getUIDTEACHER() {
        return UIDTEACHER;
    }

    public void setUIDTEACHER(int UIDTEACHER) {
        this.UIDTEACHER = UIDTEACHER;
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
}
