package com.example.mealplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Pantry implements Serializable {
    private int id;
    private String name;
    private User user;

    public Pantry(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Pantry(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
