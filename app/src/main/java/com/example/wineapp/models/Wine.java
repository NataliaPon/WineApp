package com.example.wineapp.models;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "wine_table")
public class Wine implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private double alcohol;// %
    private double sugar;// g
    private double volume; //liter
    private double water; //liter
    private double fruit;// juice or blended fruits in liter
    @ColumnInfo(name = "fruit_sugar")
    private double fruitSugar;//sugar in fruits, in grams
    private double sweetener;//g
    @ColumnInfo(name = "yeast_tolerance")
    private double yeastTolerance;
    @ColumnInfo(name = "start_date")
    private String startDate;// start wine date
    @ColumnInfo(name = "bottling_date")
    private String bottlingDate;
    private String photo;//path to picture
    @ColumnInfo(name = "background_color")
    private int backgroundColor;



    public Wine(String name, String description, double alcohol, double sugar, double volume, double water, double fruit,
                double fruitSugar, double sweetener, double yeastTolerance, String startDate, String bottlingDate, int backgroundColor) {
        this.name = name;
        this.description = description;
        this.alcohol = alcohol;
        this.sugar = sugar;
        this.volume = volume;
        this.water = water;
        this.fruitSugar = fruitSugar;
        this.fruit = fruit;
        this.sweetener = sweetener;
        this.yeastTolerance = yeastTolerance;
        this.startDate = startDate;
        this.bottlingDate = bottlingDate;
        this.backgroundColor = backgroundColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public double getSugar() {
        return sugar;
    }

    public double getVolume() {
        return volume;
    }

    public double getWater() {
        return water;
    }

    public double getFruit() {
        return fruit;
    }

    public double getSweetener() {
        return sweetener;
    }

    public double getYeastTolerance() {
        return yeastTolerance;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getBottlingDate() {
        return bottlingDate;
    }

    public double getFruitSugar() {
        return fruitSugar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
