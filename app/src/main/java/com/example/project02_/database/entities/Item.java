// Item.java
package com.example.project02_.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double price;
    private String description;


    public Item(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;

    }
//// AUTOGEN

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}