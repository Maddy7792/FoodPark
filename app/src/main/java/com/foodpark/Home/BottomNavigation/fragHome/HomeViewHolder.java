package com.foodpark.Home.BottomNavigation.fragHome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Maddy on 2/4/2018.
 */

public class HomeViewHolder extends RecyclerView.Adapter<HomeViewHolder.ViewHolder> {
    private Context context;
    private ArrayList<Category> categories;
    private boolean isFavourite = false;

    public HomeViewHolder(Context context, ArrayList<Category> list) {
        this.context = context;
        this.categories = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_food_item_ui, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtItemName;
        public ImageView imageURL;
        public ImageView fpIVEmptyHeart;
        public ImageView fpIVFilledHeart;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemName = (TextView) itemView.findViewById(R.id.food_home_text);
            imageURL = (ImageView) itemView.findViewById(R.id.food_home_image);
            fpIVEmptyHeart = (ImageView) itemView.findViewById(R.id.fp_iv_empty_heart);
            fpIVFilledHeart = (ImageView) itemView.findViewById(R.id.fp_iv_filled_heart);

        }

        public void onBind(int position) {
            txtItemName.setText(categories.get(position).getName().toLowerCase());
            Picasso.with(context).load(categories.get(position).getImage()).into(imageURL);
            fpIVFilledHeart.setOnClickListener(this);
            fpIVEmptyHeart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int Id = v.getId();

            if (Id == R.id.fp_iv_empty_heart) {
                if (!isFavourite) {
                    isFavourite = true;
                    Log.d("Empty", "1........");
                    fpIVEmptyHeart.setVisibility(View.GONE);
                    fpIVFilledHeart.setVisibility(View.VISIBLE);
                } else {
                    isFavourite = false;
                    Log.d("Empty", "2........");
                    fpIVEmptyHeart.setVisibility(View.GONE);
                    fpIVFilledHeart.setVisibility(View.VISIBLE);
                }
            }
            if (Id == R.id.fp_iv_filled_heart) {
                if (isFavourite) {
                    isFavourite = false;
                    Log.d("Empty", "3........");
                    fpIVEmptyHeart.setVisibility(View.VISIBLE);
                    fpIVFilledHeart.setVisibility(View.GONE);
                } else {
                    Log.d("Empty", "4........");
                    isFavourite = true;
                    fpIVEmptyHeart.setVisibility(View.VISIBLE);
                    fpIVFilledHeart.setVisibility(View.GONE);
                }
            }
        }
    }
}
