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
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public WineListViewModel(@NonNull Application application) {
        super(application);
        wineRepository = new WineRepository(application);
        mWines = wineRepository.getAllWines();
    }

    public void init(){
        if(mWines != null){
            return;
        }
//        wineRepository = WineRepository();
//        mWines = wineRepository.getWines();
    }

    public void insert(Wine wine){
        wineRepository.insert(wine);
    }

    public void update(Wine wine){
        wineRepository.update(wine);
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
