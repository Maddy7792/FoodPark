package com.foodpark.model;

/**
 * Created by Maddy on 2/4/2018.
 */

public class User {

    private String Name;
    private String Password;
    private String Phone;
    private String SurName;
    private String Email;
    private String IsStaff;
    private String HomeAddress;
    private String OfficeAddress;

    public User() {
    }

    public User(String name, String password,String surName,String email,String phone) {
        Name = name;
        Password = password;
        SurName = surName;
        Email = email;
        Phone = phone;
        IsStaff = "false";

    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        OfficeAddress = officeAddress;
    }
}
