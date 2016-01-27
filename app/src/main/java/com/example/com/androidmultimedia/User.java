package com.example.com.androidmultimedia;

import com.firebase.client.Firebase;

/**
 * Created by 48089748z on 27/01/16.
 */
public class User
{
    private int birthYear;
    private String fullName;
    public User(String fullName, int birthYear)
    {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }
}
