package com.foodpark.TouchHelpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.foodpark.ViewHolders.CartViewHolder;
import com.foodpark.Adapters.FavouritesViewAdapter;

public class RecyclervItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperLister lister;

    public RecyclervItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperLister lister) {
        super(dragDirs, swipeDirs);
        this.lister = lister;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (lister!=null){
            lister.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder)viewHolder).cart_item_name;
            getDefaultUIUtil().clearView(foregroundView);
        }else if (viewHolder instanceof FavouritesViewAdapter.FavouriteViewHolder){
            View foregroundView = ((FavouritesViewAdapter.FavouriteViewHolder)viewHolder).fpForeGround;
            getDefaultUIUtil().clearView(foregroundView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder)viewHolder).cart_item_name;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }else if (viewHolder instanceof FavouritesViewAdapter.FavouriteViewHolder){
            View foregroundView = ((FavouritesViewAdapter.FavouriteViewHolder)viewHolder).fpForeGround;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null){
            if (viewHolder instanceof CartViewHolder){
                View foregroundView = ((CartViewHolder)viewHolder).cart_item_name;
                getDefaultUIUtil().onSelected(foregroundView);
            }else if (viewHolder instanceof FavouritesViewAdapter.FavouriteViewHolder){
                View foregroundView = ((FavouritesViewAdapter.FavouriteViewHolder)viewHolder).fpForeGround;
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder)viewHolder).cart_item_name;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }else if (viewHolder instanceof FavouritesViewAdapter.FavouriteViewHolder){
            View foregroundView = ((FavouritesViewAdapter.FavouriteViewHolder)viewHolder).fpForeGround;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }
}
