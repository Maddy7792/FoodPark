package com.foodpark.profile;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.TouchHelpers.RecyclerItemTouchHelperLister;
import com.foodpark.TouchHelpers.RecyclervItemTouchHelper;
import com.foodpark.Utils.AppConstants;
import com.foodpark.ViewHolders.FavouritesViewAdapter;
import com.foodpark.application.App;
import com.foodpark.database.Database;
import com.foodpark.model.Favourites;

import java.util.List;

public class FPFavouriteActivity extends AppCompatActivity implements RecyclerItemTouchHelperLister {

    RecyclerView fpRVFavourites;
    FavouritesViewAdapter favouritesViewAdapter;
    private Database localDb;
    List<Favourites> favourites;
    RelativeLayout rootView;
    private Toolbar toolbar;
    private TextView fpTVToolbarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_favourite);
        rootView = findViewById(R.id.root_view);
        toolbar = findViewById(R.id.fp_toolbar);
        fpTVToolbarName = toolbar.findViewById(R.id.fp_toolbar_name);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        fpTVToolbarName.setText(AppConstants.KEY_FAV);
        fpRVFavourites = findViewById(R.id.fp_rv_favourites);
        fpRVFavourites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fpRVFavourites.setHasFixedSize(true);
        localDb = new Database(this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclervItemTouchHelper(0,
                ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(fpRVFavourites);
        getFavouritesFoods();

    }

    private void getFavouritesFoods() {
        favourites = localDb.getFavourites(App.getInstance().getPhoneNumber());
        favouritesViewAdapter = new FavouritesViewAdapter(FPFavouriteActivity.this);
        favouritesViewAdapter.setFavData(favourites);
        fpRVFavourites.setAdapter(favouritesViewAdapter);
        favouritesViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int direction, int position) {
        if (holder instanceof FavouritesViewAdapter.FavouriteViewHolder){
            String name = ((FavouritesViewAdapter)fpRVFavourites.getAdapter()).getItem(position).getFoodName();
            final Favourites deleteItem = ((FavouritesViewAdapter)fpRVFavourites.getAdapter())
                    .getItem(holder.getAdapterPosition());
            final int deleteIndex = holder.getAdapterPosition();

            favouritesViewAdapter.removeFavourite(holder.getAdapterPosition());
            new Database(this).removeFavourite(deleteItem.getFoodId(),App.getInstance().getPhoneNumber());

            Snackbar snackbar = Snackbar.make(rootView,name+"removed from favourites",Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        favouritesViewAdapter.reInsertedFavouriteItem(deleteItem,deleteIndex);
                        new Database(getBaseContext()).addToFavourite(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
