package com.example.wineapp.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wineapp.Interfaces.WineDao;
import com.example.wineapp.models.Wine;

@Database(entities = {Wine.class}, version = 1)
public abstract class WineDatabase extends RoomDatabase {

    private static WineDatabase instance;
    public abstract WineDao wineDao();

    public static synchronized WineDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), WineDatabase.class,"wine_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private WineDao wineDao;

        private PopulateDbAsyncTask(WineDatabase db){
            wineDao = db.wineDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            wineDao.insert(new Wine("jabłkowo-aroniowe","sam sok",13.8,4,20,0,20,0.5,16,"2022-01-16",""));
            wineDao.insert(new Wine("jabłkowe","sam sok, pleśń ;(",12,4,20,0,20,0.5,16,"2021-10-25",""));
            wineDao.insert(new Wine("ziołowo-ryżowe","za słodkie, za mocne",15,4,20,18,2,0.5,16,"2021-06-18","2021-08-10"));
            return null;
        }
    }

}
