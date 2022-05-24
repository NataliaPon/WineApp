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
    private double alcohol;// w %
    private double sugar;// cukier dodany w kg
    private double volume; //objętość w L
    private double water; //dodana woda w L
    private double fruit;// sok owocowy lub miazga owocowa w L, zawartość cukru dodać do sugar
    @ColumnInfo(name = "fruit_sugar")
    private double fruitSugar;//cukier w owocach
    private double sweetener;//słodzik w kg
    @ColumnInfo(name = "yeast_tolerance")
    private double yeastTolerance;//tolerancja drożdży
    @ColumnInfo(name = "start_date")
    private String startDate;// data nastawienia wina
    @ColumnInfo(name = "bottling_date")
    private String bottlingDate;//data butelkowania
    //todo add image
//    private Bitmap photo;


    public Wine(String name, String description, double alcohol, double sugar, double volume, double water, double fruit, double fruitSugar, double sweetener, double yeastTolerance, String startDate, String bottlingDate) {
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
}
