package com.example.travel;

import java.io.Serializable;

public class LocationAdmin implements Serializable {
     private String resourceImage;
     private String title;
     private int id_item;
     private String motaItem;
     private int mien_id;


    public LocationAdmin(String resourceImage, String title, int id_item, String motaItem, int mien_id) {
        this.resourceImage = resourceImage;
        this.title = title;
        this.id_item = id_item;
        this.motaItem = motaItem;
        this.mien_id = mien_id;
    }

    public String getMotaItem() {
        return motaItem;
    }

    public void setMotaItem(String motaItem) {
        this.motaItem = motaItem;
    }

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getMien_id() {
        return mien_id;
    }

    public void setMien_id(int mien_id) {
        this.mien_id = mien_id;
    }
    public String toString()  {
        return this.getTitle();
    }
}
