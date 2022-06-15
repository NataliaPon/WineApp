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

@Database(entities = {Wine.class}, version = 3)
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
        }
    };

}
