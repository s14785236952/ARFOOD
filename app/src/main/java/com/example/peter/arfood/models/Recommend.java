package com.example.peter.arfood.models;

public class Recommend {
    public final String name;
    public final String address;
    public final String phone;
    public final String type;
    public final String opening_hours;

    public Recommend(final String name, final String address, final String phone, final String type, final String opening_hours) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.opening_hours = opening_hours;
    }

}
