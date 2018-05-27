package com.foodpark.App;

import android.app.Application;

import com.foodpark.model.User;

import io.paperdb.Paper;

/**
 * Created by dennis on 10/5/18.
 */

public class SaveData extends Application {

    private static SaveData mInstance;
    public static boolean IS_DEBUG = true;
    private String searchQuery;
    private User user;
    private String phoneNumber;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized SaveData getInstance(){
        return mInstance;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
