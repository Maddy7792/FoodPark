package com.foodpark.home.BottomNavigation.fragSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Maddy on 2/4/2018.
 */

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> topCategories;
    private ArrayList<Category> moreCategories;
    private String type;

    public SearchViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (type.equals(AppConstants.KEY_TOP)) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_categories_child_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else if (type.equals(AppConstants.KEY_MORE)) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_categories_more_item, null);
            ViewHolder moreHolder = new ViewHolder(view);
            return moreHolder;
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (type.equals(AppConstants.KEY_TOP)) {
            holder.onBind(position);
        } else if (type.equals(AppConstants.KEY_MORE)) {
            holder.onBindMore(position);
        }

    }

    @Override
    public int getItemCount() {

        if (type.equals(AppConstants.KEY_TOP)) {
            return topCategories.size();
        } else if (type.equals(AppConstants.KEY_MORE)) {
            return moreCategories.size();
        } else {
            return 0;
        }

    }

    public void setMoreCategoriesData(ArrayList<Category> moreCategoriesList) {
        this.moreCategories = moreCategoriesList;
    }

    public void setTopCategories(ArrayList<Category> topCategories) {
        this.topCategories = topCategories;
    }

    public void setType(String key) {
        this.type = key;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItemName;
        public ImageView imageURL;
        public ViewHolder(View itemView) {
            super(itemView);
            if (type.equals(AppConstants.KEY_TOP)) {
                txtItemName = (TextView) itemView.findViewById(R.id.title);
                imageURL = (ImageView) itemView.findViewById(R.id.thumbnail);
            } else if (type.equals(AppConstants.KEY_MORE)) {
                txtItemName = (TextView) itemView.findViewById(R.id.fp_more_title);
                imageURL = (ImageView) itemView.findViewById(R.id.fp_more_thumbnail);
            }


        }

        public void onBind(int position) {
            txtItemName.setText(topCategories.get(position).getName());
            Picasso.with(context).load(topCategories.get(position).getImage()).into(imageURL);

        }

        public void onBindMore(int position) {
            txtItemName.setText(moreCategories.get(position).getName());
            Picasso.with(context).load(moreCategories.get(position).getImage()).into(imageURL);
        }
    }


}
