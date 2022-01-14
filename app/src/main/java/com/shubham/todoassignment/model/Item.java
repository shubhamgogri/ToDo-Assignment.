package com.shubham.todoassignment.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Item {

    private String description;
    private String date;
    private boolean isCompleted;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Item() {
    }

    public Item(String description, String date, boolean isCompleted) {
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
