package com.example.wineapp.Interfaces;

import com.example.wineapp.models.OperationType;
import com.example.wineapp.models.Wine;

public interface OnClickListenerCallBack {
    void onItemClick(Wine itemClicked, OperationType operationType);
}
