package com.example.wineapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.Toast;


import com.example.wineapp.Interfaces.OnClickListenerCallBack;
import com.example.wineapp.adapters.WineListAdapter;
import com.example.wineapp.models.OperationType;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);
        context = this;

        wineListRecyclerView = findViewById(R.id.wineListRecyclerView);
        addWineButton = findViewById(R.id.addWineButton);

        Toolbar topToolbar = findViewById(R.id.toolbar);
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
//                v.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.wineListContainer, AddWineFragment.newInstance(OperationType.View,null))
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

        private void initRecyclerView(){
//        Log.e(TAG,wineListViewModel.getWines().getValue().size()+"");
        wineListAdapter = new WineListAdapter(context, new OnClickListenerCallBack() {
            @Override
            public void onItemClick(Wine itemClicked, OperationType operationType) {
                switch (operationType){
                    case View:
                        getSupportFragmentManager().beginTransaction().replace(R.id.wineListContainer, ViewWineFragment.newInstance(itemClicked))
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case Edit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.wineListContainer, AddWineFragment.newInstance(OperationType.Edit,itemClicked))
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case Delete:
                        wineListViewModel.delete(itemClicked);
                        wineListAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });//wineListViewModel.getWines().getValue()
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        wineListRecyclerView.setLayoutManager(layoutManager);
        wineListRecyclerView.setAdapter(wineListAdapter);
    }

    public void hideFloatingActionButton(){
        addWineButton.hide();
    }

    public void showFloatingActionButton(){
        addWineButton.show();
    }
}
