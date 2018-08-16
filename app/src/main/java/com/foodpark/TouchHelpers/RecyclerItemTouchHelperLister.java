package com.foodpark.TouchHelpers;


import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchHelperLister {

    public void onSwiped(RecyclerView.ViewHolder holder,int direction,int position);
}
