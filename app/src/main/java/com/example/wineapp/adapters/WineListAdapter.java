package com.example.wineapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wineapp.R;
import com.example.wineapp.models.Wine;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WineListAdapter extends RecyclerView.Adapter<WineListAdapter.ViewHolder> {

    private List<Wine> data;

    public WineListAdapter(List<Wine> data){
        Log.e("WineListAdapter",data.size()+"");
        this.data = data;
        Log.e("WineListAdapter",this.data.size()+"");
    }


    @Override
    public WineListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wine_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( WineListAdapter.ViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).getName());
        Log.e("WineListAdapter",data.get(position).getName());

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView photoImageView;
        TextView nameTextView, startDateTextView, alcoholTextView, bottlingDateTextView;
        LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            alcoholTextView = itemView.findViewById(R.id.alcoholTextView);
            bottlingDateTextView = itemView.findViewById(R.id.bottlingDateTextView);
            container = itemView.findViewById(R.id.container);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


