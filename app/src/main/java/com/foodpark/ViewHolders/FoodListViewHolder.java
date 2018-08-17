package com.foodpark.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.callback.OnItemClickListener;
import com.foodpark.R;

/**
 * Created by dennis on 18/2/18.
 */

public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mFoodName;
    public ImageView mFoodImageURL;
    public ImageView mFoodFavouriteImage;
    public OnItemClickListener onItemClickListener;

    public FoodListViewHolder(View itemView) {
        super(itemView);
        mFoodName = (TextView) itemView.findViewById(R.id.food_text);
        mFoodImageURL = (ImageView) itemView.findViewById(R.id.food_image);
        mFoodFavouriteImage = (ImageView) itemView.findViewById(R.id.favourite_image);
        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.OnClick(v, getAdapterPosition(), false);
    }
}
