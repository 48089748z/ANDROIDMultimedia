package com.example.com.androidmultimedia;


/**
 * Created by 48089748z on 27/01/16.
 */
public class User
{
    private int birthYear;
    private String fullName;
    public User() {}
    public int getBirthYear() {return birthYear;}
    public void setBirthYear(int birthYear) {this.birthYear = birthYear;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public User(String fullName, int birthYear) {
        this.birthYear = birthYear;
        this.fullName = fullName;
    }
}
