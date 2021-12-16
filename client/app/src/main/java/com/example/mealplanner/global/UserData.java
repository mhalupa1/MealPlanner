package com.example.mealplanner.global;

import com.example.mealplanner.model.User;

public class UserData {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserData.user = user;
    }
}
