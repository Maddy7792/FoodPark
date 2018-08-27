package com.foodpark.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Logger;
import com.foodpark.ViewHolders.FPCartViewDetailsViewHolder;
import com.foodpark.activities.FPViewCartDetailsActivity;
import com.foodpark.callback.OnDeleteItem;
import com.foodpark.model.Favourites;
import com.foodpark.model.Order;

import java.util.List;

public class FPCartViewDetailsAdapter extends RecyclerView.Adapter<FPCartViewDetailsViewHolder>{

    private Context context;
    private List<Order> orderList;
    private OnDeleteItem onDeleteItem;

    public FPCartViewDetailsAdapter(Context context) {
        this.context = context;
        onDeleteItem = (OnDeleteItem)context;
    }

    @NonNull
    @Override
    public FPCartViewDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_card_view_item_layout,null);
        FPCartViewDetailsViewHolder detailsViewHolder = new FPCartViewDetailsViewHolder(view);
        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FPCartViewDetailsViewHolder holder, final int position) {
        holder.mCartFoodName.setText(orderList.get(position).getProductName());
        holder.mOrderNumber.setNumber(orderList.get(position).getQuantity());
        holder.mCartPrice.setText(getPrice(orderList.get(position).getPrice()));
        holder.mCartTextRightPrice.setText(getPrice(orderList.get(position).getPrice()));
        holder.mOrderNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                onDeleteItem.OnDelete(holder,position,newValue);
                /*if (newValue<1){
                    remove(position);
                }*/
            }
        });
    }



    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setData(List<Order> orderList) {
        this.orderList = orderList;
    }

    public String getPrice(String price){
        return AppConstants.RUPEE_SYMBOL+price+".00";
    }

    public void remove(int position){
        orderList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public Order getItem(int position){
        return orderList.get(position);
    }
}
