package com.example.wineapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wineapp.models.Wine;
import com.example.wineapp.repositories.WineListRepository;

import java.util.List;

public class WineListViewModel extends ViewModel {

    private MutableLiveData<List<Wine>> mWines;
    private WineListRepository wineListRepository;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
        if(mWines != null){
            return;
        }
        wineListRepository = WineListRepository.getInstance();
        mWines = wineListRepository.getWines();
    }

    public void addNewWine(final Wine wine){
        mIsUpdating.setValue(true);

    }

    public LiveData<List<Wine>> getWines(){
        return mWines;
    }
}
