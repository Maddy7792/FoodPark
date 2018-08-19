package com.foodpark.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.foodpark.R;

public class FPCartViewDetailsViewHolder extends RecyclerView.ViewHolder {
    public TextView mCartFoodName;
    public TextView mCartPrice;
    public TextView mCartTextRightPrice;
    public ElegantNumberButton mOrderNumber;

    public FPCartViewDetailsViewHolder(View itemView) {
        super(itemView);
        mCartFoodName = itemView.findViewById(R.id.fp_tv_cart_food_name);
        mCartPrice = itemView.findViewById(R.id.fp_tv_food_left_price_text);
        mCartTextRightPrice = itemView.findViewById(R.id.fp_tv_food_right_price_text);
        mOrderNumber = itemView.findViewById(R.id.fp_number_button);
    }
}
