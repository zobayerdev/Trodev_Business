package com.trodev.trodevbusiness.website;

public class LinkModel {

    String web_name, web_link, date, time, image;

    public LinkModel() {
    }

    public LinkModel(String web_name, String web_link, String date, String time, String image) {
        this.web_name = web_name;
        this.web_link = web_link;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public String getWeb_name() {
        return web_name;
    }

    public void setWeb_name(String web_name) {
        this.web_name = web_name;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
