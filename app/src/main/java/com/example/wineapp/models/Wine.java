package com.example.wineapp.models;

import android.graphics.Bitmap;

public class Wine {
    int id;
    String name;
    String description;
    double alcohol;// w %
    double sugar;// w kg
    double volume; //objętość w L
    double water; //dodana woda w L
    double fruit;// sok owocowy lub miazga owocowa w L, zawartość cukru dodać do sugar
    double sweetener;//słodzik w kg
    double yeastTolerance;//?
    String startDate;// data nastawienia wina
    String bottlingDate;//data butelkowania
    Bitmap photo;

    public Wine() {
    }

    public Wine(int id, String name, String description, double alcohol, double sugar, double volume, double water, double fruit, double sweetener, double yeastTolerance, String startDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.alcohol = alcohol;
        this.sugar = sugar;
        this.volume = volume;
        this.water = water;
        this.fruit = fruit;
        this.sweetener = sweetener;
        this.yeastTolerance = yeastTolerance;
        this.startDate = startDate;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getFruit() {
        return fruit;
    }

    public void setFruit(double fruit) {
        this.fruit = fruit;
    }

    public double getSweetener() {
        return sweetener;
    }

    public void setSweetener(double sweetener) {
        this.sweetener = sweetener;
    }

    public double getYeastTolerance() {
        return yeastTolerance;
    }

    public void setYeastTolerance(double yeastTolerance) {
        this.yeastTolerance = yeastTolerance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBottlingDate() {
        return bottlingDate;
    }

    public void setBottlingDate(String bottlingDate) {
        this.bottlingDate = bottlingDate;
    }
}
