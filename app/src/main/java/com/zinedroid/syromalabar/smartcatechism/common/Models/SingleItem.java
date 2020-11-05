package com.zinedroid.syromalabar.smartcatechism.common.Models;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public class SingleItem {
    String id;
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    String name;

    public SingleItem() {
    }
}
