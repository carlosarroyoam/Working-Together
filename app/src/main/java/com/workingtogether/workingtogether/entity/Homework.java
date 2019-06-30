package com.workingtogether.workingtogether.entity;

public class Homework {
    private int id;
    private String title;
    private String description;
    private String created_at;
    private String updated_at;
    private String delivery_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDeliveryDate() {
        return delivery_date;
    }

    public void setDeliveryDate(String delivered_at) {
        this.delivery_date = delivered_at;
    }
    
}
