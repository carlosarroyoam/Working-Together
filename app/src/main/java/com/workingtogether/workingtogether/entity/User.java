package com.workingtogether.workingtogether.entity;

public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String user_type;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return user_type;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String deliverdate) {
        this.created_at = deliverdate;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public static class UserTypes {
        public static final String TEACHER_USER = "TEACHER_USER";
        public static final String PARENT_USER = "PARENT_USER";

    }
}
