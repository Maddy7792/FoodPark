package com.foodpark.callback;

import android.support.v7.widget.RecyclerView;

public interface OnDeleteItem {

    void OnDelete(RecyclerView.ViewHolder viewHolder,int position,int numberValue);
    void OnUpdate(RecyclerView.ViewHolder viewHolder,int position,int numberValue);
}
