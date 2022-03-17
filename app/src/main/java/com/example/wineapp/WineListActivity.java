package com.example.wineapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



import com.example.wineapp.adapters.WineListAdapter;
import com.example.wineapp.models.Wine;
import com.example.wineapp.viewmodels.WineListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WineListActivity extends AppCompatActivity {

    String TAG = "WineListActivity";
//    ArrayList<Wine> wines = new ArrayList<>();
    Context context;

    private WineListAdapter wineListAdapter;
    private RecyclerView wineListRecyclerView;
    private WineListViewModel wineListViewModel;
    private FloatingActionButton addWineButton;
    private Toolbar topToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);
        context = this;

        wineListRecyclerView = findViewById(R.id.wineListRecyclerView);
        addWineButton = findViewById(R.id.addWineButton);
        topToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolbar);

        initRecyclerView();

        wineListViewModel = ViewModelProviders.of(this).get(WineListViewModel.class);
//        wineListViewModel.init();
        wineListViewModel.getWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                wineListAdapter.setData(wines);
            }
        });

        addWineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.wineListContainer, new AddWineFragment()).commit();
            }
        });

    }

        private void initRecyclerView(){
//        Log.e(TAG,wineListViewModel.getWines().getValue().size()+"");
        wineListAdapter = new WineListAdapter();//wineListViewModel.getWines().getValue()
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        wineListRecyclerView.setLayoutManager(layoutManager);
        wineListRecyclerView.setAdapter(wineListAdapter);
    }
}
