package com.foodpark.Home.BottomNavigation.fragSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> topCategories;

    public SearchViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(context).inflate(R.layout.layout_categories_child_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return topCategories.size();
    }

    public void setMoreCategoriesData(ArrayList<Category> moreCategoriesList) {
    }

    public void setTopCategories(ArrayList<Category> topCategories){
        this.topCategories = topCategories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItemName;
        public ImageView imageURL;


        public ViewHolder(View itemView) {
            super(itemView);
            txtItemName = (TextView) itemView.findViewById(R.id.title);
            imageURL = (ImageView) itemView.findViewById(R.id.thumbnail);

        }

        public void onBind(int position) {
            txtItemName.setText(topCategories.get(position).getName());
            Picasso.with(context).load(topCategories.get(position).getImage()).into(imageURL);

        }

    }
}
