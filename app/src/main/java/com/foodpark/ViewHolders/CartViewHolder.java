package com.foodpark.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.foodpark.Interfaces.OnItemClickListener;
import com.foodpark.R;
import com.foodpark.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dennis on 18/2/18.
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView cart_item_name;
    public TextView cart_item_price;
    //public ImageView cart_item_count;


    public CartViewHolder(View itemView) {
        super(itemView);
        //cart_item_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
        cart_item_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        cart_item_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}

