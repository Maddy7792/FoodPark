package com.foodpark.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.foodpark.callback.OnItemClickListener;
import com.foodpark.R;

/**
 * Created by dennis on 18/3/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvOrderId;
    public TextView tvOrderPhone;
    public TextView tvOrderStatus;
    public TextView tvOrderAddress;

    OnItemClickListener onItemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        tvOrderId = (TextView)itemView.findViewById(R.id.order_Id);
        tvOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        tvOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        tvOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        itemView.setOnClickListener(this);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
            //onItemClickListener.OnClick(v,getAdapterPosition(),false);
    }
}
