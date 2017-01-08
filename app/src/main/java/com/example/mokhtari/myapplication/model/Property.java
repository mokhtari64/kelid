package com.example.mokhtari.myapplication.model;

/**
 * Created by Mahdi on 15/07/2016.
 */
public class Property {
    public String name,email,address;

    @Override
    public String toString() {
        return name+" : "+email;
    }
}
