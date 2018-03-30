package com.foodpark.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.Interfaces.OnItemClickListener;
import com.foodpark.R;

/**
 * Created by Maddy on 2/4/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtItemName;
    public ImageView imageURL;
    public OnItemClickListener onItemClickListener;
    public MenuViewHolder(View itemView) {
        super(itemView);
        txtItemName = (TextView)itemView.findViewById(R.id.menu_text);
        imageURL = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener= onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        onItemClickListener.OnClick(view,getAdapterPosition(),false);
    }
}
