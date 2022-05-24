package com.example.wineapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.wineapp.models.Wine;
import com.example.wineapp.repositories.WineRepository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class AddWineViewModel extends AndroidViewModel {

    private WineRepository wineRepository;

    public AddWineViewModel(@NonNull Application application) {
        super(application);
        wineRepository = new WineRepository(application);
    }

    public void insert(Wine wine){
        wineRepository.insert(wine);
    }

    public void update(Wine wine){
        wineRepository.update(wine);
    }

    public double countAlcohol(double totalVolume, double totalSugar){
        DecimalFormat df = new DecimalFormat("#.#");
        Double alco = totalSugar/totalVolume/17;

        String formatAlco = df.format(alco);
        Double coutedAlco = Double.parseDouble(formatAlco.replace(",","."));
        return coutedAlco;
    }

    public double editTextToDouble(String text){
        if(text!= null && !text.equalsIgnoreCase("") && !text.equalsIgnoreCase("null")){
            return Double.parseDouble(text);
        }else
            return 0d;
    }

    public double countTotalVolume(String water, String fruits, String sugar){
        return editTextToDouble(water) + editTextToDouble(fruits) + (editTextToDouble(sugar)*0.0006);
    }

    public double countTotalSugar(String sugar, String fruitsSugar, String fruitsVolume){
        return editTextToDouble(sugar) +(editTextToDouble(fruitsSugar)*editTextToDouble(fruitsVolume));
    }

}
