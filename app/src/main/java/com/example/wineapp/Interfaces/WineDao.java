package com.example.wineapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wineapp.models.Wine;

import java.util.List;

@Dao
public interface WineDao {

    @Insert
    void insert(Wine wine);

    @Update
    void update(Wine wine);

    @Delete
    void delete(Wine wine);

    @Query("DELETE FROM wine_table")
    void deleteAllWines();

    @Query("SELECT * FROM wine_table ORDER BY start_date DESC")
    LiveData<List<Wine>> getAllWines();



}
