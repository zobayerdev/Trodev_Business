package com.trodev.trodevbusiness.password;

public class PasswordModel {

    String username,website, password, time, date;

    public PasswordModel() {
    }

    public PasswordModel(String username, String website, String password, String time, String date) {
        this.username = username;
        this.website = website;
        this.password = password;
        this.time = time;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
