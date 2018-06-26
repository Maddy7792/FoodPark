package com.foodpark.location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodpark.R;


public class FPLocationAdressAdapter extends RecyclerView.Adapter<FPLocationAdressAdapter.LocationViewHolder> {
    private Context context;
    private String[] locationList;

    public FPLocationAdressAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_location_address_item_layout, null);
        LocationViewHolder viewHolder = new LocationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.onBindData(holder,position);
    }

    @Override
    public int getItemCount() {
        return locationList.length;
    }

    public void setLocationData(String[] addressList) {
        this.locationList = addressList;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationAddress;

        public LocationViewHolder(View itemView) {
            super(itemView);
            tvLocationAddress = itemView.findViewById(R.id.fp_tv_location_address);
        }

        public void onBindData(LocationViewHolder holder, int position) {
            tvLocationAddress.setText(locationList[position]);
        }
    }
}
