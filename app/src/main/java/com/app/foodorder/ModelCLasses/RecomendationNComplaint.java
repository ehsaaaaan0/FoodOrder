package com.app.foodorder.ModelCLasses;

public class RecomendationNComplaint {
    String name, email, message, userId, key;

    public RecomendationNComplaint(){}

    public RecomendationNComplaint(String name, String email, String message, String userId, String key) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.userId = userId;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
