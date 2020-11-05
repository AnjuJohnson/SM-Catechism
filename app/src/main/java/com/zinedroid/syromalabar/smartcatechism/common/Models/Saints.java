package com.zinedroid.syromalabar.smartcatechism.common.Models;

/**
 * Created by Cecil Paul on 12/10/17.
 */

public class Saints {
    String id, name, date, image, details;

    public Saints(String id, String name, String date, String image, String details) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.image = image;
        this.details = details;
    }

    public Saints() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
