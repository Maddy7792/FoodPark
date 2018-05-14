package com.foodpark.FilterFrags.price;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.model.Price;

import java.util.ArrayList;

/**
 * Created by dennis on 9/5/18.
 */

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Price> priceList;
    private int lastSelectedPosition = -1;
    private Price price;

    public PriceAdapter(Context context, ArrayList<Price> priceList) {
        this.context = context;
        this.priceList = priceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_price_item, null);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        price = priceList.get(position);
        // holder.fpTVRuppee.setBackgroundColor(price.isSelected() ? Color.BLUE : Color.RED);
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView fpTVRuppee;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            fpTVRuppee = (TextView) itemView.findViewById(R.id.fp_tv_price);
            fpTVRuppee.setOnClickListener(this);
        }

        public void onBind(int position) {
            fpTVRuppee.setText(priceList.get(position).getRupee());
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            if (lastSelectedPosition > 0) {
                priceList.get(lastSelectedPosition).setSelected(false);
            }
            price.setSelected(!price.isSelected());
            if (price.isSelected()) {
                fpTVRuppee.setTextColor(Color.WHITE);
                fpTVRuppee.setBackgroundResource(R.drawable.circular_filled_background);

            } else {
                fpTVRuppee.setBackground(null);
                fpTVRuppee.setTextColor(R.color.colorCoffee);
                fpTVRuppee.setBackgroundResource(R.drawable.circular_background_line);
            }


        }
    }
}
