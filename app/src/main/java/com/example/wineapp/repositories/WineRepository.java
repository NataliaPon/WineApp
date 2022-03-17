package com.example.wineapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wineapp.Interfaces.WineDao;
import com.example.wineapp.models.Wine;

import java.util.List;

public class WineRepository {
//    private static WineRepository instance;
    private WineDao wineDao;
    private LiveData<List<Wine>> allWines;//MutableLiveData - if we want to change this data, LiveData - if we only want to present this data

    public WineRepository(Application application){
        WineDatabase database = WineDatabase.getInstance(application);
        wineDao = database.wineDao();
        allWines = wineDao.getAllWines();
    }

    public void insert(Wine wine){
        new InsertWineAsyncTask(wineDao).execute(wine);
    }

    public void update(Wine wine){
        new UpdateWineAsyncTask(wineDao).execute(wine);
    }

    public void delete(Wine wine){
        new DeleteWineAsyncTask(wineDao).execute(wine);
    }

    public void deleteAllWines(){
        new DeleteAllWinesAsyncTask(wineDao).execute();
    }

    public LiveData<List<Wine>> getAllWines(){
        return allWines;
    }

    private static class InsertWineAsyncTask extends AsyncTask<Wine, Void, Void>{
        private WineDao wineDao;
        private InsertWineAsyncTask(WineDao wineDao){
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.insert(wines[0]);
            return null;
        }
    }

    private static class UpdateWineAsyncTask extends AsyncTask<Wine, Void, Void>{
        private WineDao wineDao;
        private UpdateWineAsyncTask(WineDao wineDao){
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.update(wines[0]);
            return null;
        }
    }

    private static class DeleteWineAsyncTask extends AsyncTask<Wine, Void, Void>{
        private WineDao wineDao;
        private DeleteWineAsyncTask(WineDao wineDao){
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Wine... wines) {
            wineDao.delete(wines[0]);
            return null;
        }
    }

    private static class DeleteAllWinesAsyncTask extends AsyncTask<Void, Void, Void>{
        private WineDao wineDao;
        private DeleteAllWinesAsyncTask(WineDao wineDao){
            this.wineDao = wineDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wineDao.deleteAllWines();
            return null;
        }
    }

}
