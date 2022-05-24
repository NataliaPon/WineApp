package com.example.wineapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wineapp.Interfaces.OnClickListenerCallBack;
import com.example.wineapp.R;
import com.example.wineapp.models.OperationType;
import com.example.wineapp.models.Wine;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WineListAdapter extends RecyclerView.Adapter<WineListAdapter.ViewHolder> {

    private List<Wine> data = new ArrayList<>();
    private OnClickListenerCallBack onClickListenerCallBack;
    private Context context;

    public WineListAdapter(Context context, OnClickListenerCallBack onClickListenerCallBack){
        this.onClickListenerCallBack = onClickListenerCallBack;
        this.context = context;
    }


    @Override
    public WineListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wine_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WineListAdapter.ViewHolder holder, int position) {
        final Wine wine = data.get(position);
        holder.nameTextView.setText(data.get(position).getName());
        holder.startDateTextView.setText(data.get(position).getStartDate());
        holder.alcoholTextView.setText(data.get(position).getAlcohol()+"%");
        holder.bottlingDateTextView.setText(data.get(position).getBottlingDate());
        Log.e("WineListAdapter",data.get(position).getName());

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerCallBack.onItemClick(wine, OperationType.View);
            }
        });

        holder.imageViewMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder, wine);
            }
        });

    }

    public void setData(List<Wine> data){
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView photoImageView;
        TextView nameTextView, startDateTextView, alcoholTextView, bottlingDateTextView;
        LinearLayout itemContainer;
        ImageView imageViewMenuItem;

        ViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            alcoholTextView = itemView.findViewById(R.id.alcoholTextView);
            bottlingDateTextView = itemView.findViewById(R.id.bottlingDateTextView);
            itemContainer = itemView.findViewById(R.id.itemContainer);
            imageViewMenuItem = itemView.findViewById(R.id.imageViewMenuItem);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void showPopupMenu (WineListAdapter.ViewHolder holder, final Wine wine){
        final PopupMenu popup = new PopupMenu(context, holder.imageViewMenuItem);
        popup.inflate(R.menu.menu_wine_list_item);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_item:
                        popup.dismiss();
                        onClickListenerCallBack.onItemClick(wine, OperationType.Edit);
                        return true;
                    case R.id.delete_item:
                        popup.dismiss();
                        onClickListenerCallBack.onItemClick(wine, OperationType.Delete);
                        return true;
                    default:
                        popup.dismiss();
                        return false;
                }
            }
        });
        popup.show();
    }
}


