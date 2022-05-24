package com.example.wineapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wineapp.models.Wine;
import com.example.wineapp.repositories.WineRepository;

import java.util.List;

public class WineListViewModel extends AndroidViewModel {

    private LiveData<List<Wine>> mWines;
    private WineRepository wineRepository;

    public WineListViewModel(@NonNull Application application) {
        super(application);
        wineRepository = new WineRepository(application);
        mWines = wineRepository.getAllWines();
    }

    public void delete(Wine wine){
        wineRepository.delete(wine);
    }

    public void deleteAllWines(){
        wineRepository.deleteAllWines();
    }

    public LiveData<List<Wine>> getWines(){
        return mWines;
    }
}
