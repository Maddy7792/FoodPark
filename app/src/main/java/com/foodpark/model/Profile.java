package com.foodpark.model;

/**
 * Created by dennis on 24/5/18.
 */

public class Profile {

    private String Name;
    private String avatarUrl;

    public Profile() {
    }

    public Profile(String name, String avatarUrl) {
        this.Name = name;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
