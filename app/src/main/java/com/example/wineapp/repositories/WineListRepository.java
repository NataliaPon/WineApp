package com.example.wineapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.wineapp.models.Wine;

import java.util.ArrayList;
import java.util.List;

public class WineListRepository {
    private static WineListRepository instance;
    private ArrayList<Wine> dataSet = new ArrayList<>();

    public static WineListRepository getInstance(){
        if (instance == null){
            instance = new WineListRepository();
        }
        return instance;
    }

    //todo Get data from webservice or database
    public MutableLiveData<List<Wine>> getWines(){
        setWines();
        MutableLiveData<List<Wine>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setWines(){
        dataSet.add(new Wine(1,"Jabłkowe","Sok i woda",14,4,20,2,18,0.5,18,"05.12.2021"));
        dataSet.add(new Wine(1,"Aroniowe","Sam sok",13,4,20,0,20,0.5,18,"18.10.2021"));
    }
}
