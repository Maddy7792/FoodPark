package com.foodpark.FilterFrags.sort;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.foodpark.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dennis on 8/5/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> sortList;
    private int[] iconList;

    public FilterAdapter(Context context, ArrayList<String> sortList, int[] imageList) {
        this.context = context;
        this.sortList = sortList;
        this.iconList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sort_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return sortList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckedTextView sortCheck;
        private ImageView fpIVSortIcons;

        public ViewHolder(View itemView) {
            super(itemView);
            sortCheck = (CheckedTextView) itemView.findViewById(R.id.fp_sort_check);
            fpIVSortIcons = (ImageView) itemView.findViewById(R.id.fp_iv_icon);
            sortCheck.setOnClickListener(this);
        }

        public void onBind(int position) {
            sortCheck.setText(sortList.get(position));
            Picasso.with(context).load(iconList[position]).into(fpIVSortIcons);
        }

        @Override
        public void onClick(View v) {
            if (sortCheck.isChecked()) {
                sortCheck.setCheckMarkDrawable(null);
                sortCheck.setChecked(false);
            } else {
                sortCheck.setCheckMarkDrawable(R.drawable.check_tick);
                sortCheck.setChecked(true);
            }
        }
    }


}
