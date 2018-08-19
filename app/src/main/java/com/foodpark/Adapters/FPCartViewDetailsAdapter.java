package com.foodpark.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;
import com.foodpark.ViewHolders.FPCartViewDetailsViewHolder;
import com.foodpark.activities.FPViewCartDetailsActivity;
import com.foodpark.model.Order;

import java.util.List;

public class FPCartViewDetailsAdapter extends RecyclerView.Adapter<FPCartViewDetailsViewHolder>{

    private Context context;
    private List<Order> orderList;

    public FPCartViewDetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FPCartViewDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_card_view_item_layout,null);
        FPCartViewDetailsViewHolder detailsViewHolder = new FPCartViewDetailsViewHolder(view);
        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FPCartViewDetailsViewHolder holder, int position) {
        holder.mCartFoodName.setText(orderList.get(position).getProductName());
        holder.mOrderNumber.setNumber(orderList.get(position).getQuantity());
        holder.mCartPrice.setText(orderList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setData(List<Order> orderList) {
        this.orderList = orderList;
    }
}
