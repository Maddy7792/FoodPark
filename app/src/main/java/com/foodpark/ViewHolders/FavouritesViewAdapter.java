package com.foodpark.ViewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.model.Favourites;
import com.foodpark.profile.FPFavouriteActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesViewAdapter extends RecyclerView.Adapter<FavouritesViewAdapter.FavouriteViewHolder> {

    private Context context;
    private List<Favourites> favourites;

    public FavouritesViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item_layout, null);
        FavouriteViewHolder viewHolder = new FavouriteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.onBind(favourites, position);
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public void setFavData(List<Favourites> favourites) {
        this.favourites = favourites;
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout fpForeGround;
        public ImageView fpIVImage;
        public TextView fFoodName;
        public TextView fFoodDesc;

        public FavouriteViewHolder(View itemView) {
            super(itemView);
            fpIVImage = itemView.findViewById(R.id.fp_fav_image);
            fFoodName = itemView.findViewById(R.id.fp_tv_fav_food_name);
            fFoodDesc = itemView.findViewById(R.id.fp_tv_fav_desc);
            fpForeGround = itemView.findViewById(R.id.fp_ll_foreground);
        }

        public void onBind(List<Favourites> favourites, int position) {
            Picasso.with(context).load(favourites.get(position).getFoodImage()).placeholder(R.drawable.food).into(fpIVImage);
            fFoodDesc.setText(favourites.get(position).getFoodDescription());
            fFoodName.setText(favourites.get(position).getFoodName());
        }
    }

    public void removeFavourite(int position) {
        favourites.remove(position);
        notifyItemRemoved(position);
    }

    public void reInsertedFavouriteItem(Favourites item, int position) {
        favourites.add(position, item);
        notifyItemInserted(position);
    }

    public Favourites getItem(int position){
        return favourites.get(position);
    }
}
