package com.example.peter.arfood.models;


public class RequestBody {

    private String userEmail;
    private String userDisplayName;
    private String registrationId;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUsername(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
