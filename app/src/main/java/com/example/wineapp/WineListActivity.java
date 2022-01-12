package com.example.wineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.wineapp.adapters.WineListAdapter;
import com.example.wineapp.models.Wine;
import com.example.wineapp.viewmodels.WineListViewModel;

import java.util.ArrayList;
import java.util.List;

public class WineListActivity extends AppCompatActivity {

    String TAG = "WineListActivity";
//    ArrayList<Wine> wines = new ArrayList<>();
    Context context;

    private WineListAdapter wineListAdapter;
    private RecyclerView wineListRecyclerView;
    private WineListViewModel wineListViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_wine_list);
        wineListRecyclerView = findViewById(R.id.wineListRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        wineListViewModel = ViewModelProviders.of(this).get(WineListViewModel.class);
        wineListViewModel.init();
        wineListViewModel.getWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                wineListAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.e(TAG,wineListViewModel.getWines().getValue().size()+"");
        wineListAdapter = new WineListAdapter(wineListViewModel.getWines().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        wineListRecyclerView.setLayoutManager(layoutManager);
        wineListRecyclerView.setAdapter(wineListAdapter);
    }
}
