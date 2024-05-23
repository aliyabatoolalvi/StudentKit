package com.aliya.studentkit.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String details;
    public String category;
    public String status;
    public int price;
    public String img;


    public Item(int id, String title, String details, String category, String status, int price, String img) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.category = category;
        this.status = status;
        this.price = price;
        this.img = img;
    }

    public Item() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
