package com.foodpark.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.foodpark.Interfaces.OnItemClickListener;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.ViewHolders.FoodListViewHolder;
import com.foodpark.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerFoodList;
    private LinearLayoutManager mlayoutManager;
    private FirebaseDatabase mFoodDataBase;
    private DatabaseReference mFoodDataReference;
    private String categoryId = "";
    private FirebaseRecyclerAdapter<Food, FoodListViewHolder> foodAdapter;

    //Search Functionality
    private MaterialSearchBar searchBar;
    private List<String> suggestList = new ArrayList<>();
    private FirebaseRecyclerAdapter<Food, FoodListViewHolder> searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        mRecyclerFoodList = (RecyclerView) findViewById(R.id.recycler_food_list);
        mRecyclerFoodList.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(FoodListActivity.this);
        mRecyclerFoodList.setLayoutManager(mlayoutManager);

        mFoodDataBase = FirebaseDatabase.getInstance();
        mFoodDataReference = mFoodDataBase.getReference().child(AppConstants.KEY_FOOD);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra(AppConstants.KEY_CATEGORY_ID);
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadCategoryItems(categoryId);
        }

        searchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        searchBar.setHint(getString(R.string.hint_serach_bar));

        loadSuggest();
        searchBar.setLastSuggestions(suggestList);
        searchBar.setCardViewElevation(10);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();

                for (String search : suggestList) {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    mRecyclerFoodList.setAdapter(foodAdapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodListViewHolder>(Food.class,
                R.layout.layout_food_item,
                FoodListViewHolder.class,
                mFoodDataReference.orderByChild(AppConstants.KEY_NAME).equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(FoodListViewHolder viewHolder, Food model, final int position) {
                viewHolder.mFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.mFoodImageURL);
                final Food local = model;

                viewHolder.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void OnClick(View view, int Position, boolean isLongClick) {
                        Intent foodDetails = new Intent(FoodListActivity.this, FoodDetailsActivity.class);
                        foodDetails.putExtra(AppConstants.KEY_FOODID, searchAdapter.getRef(position).getKey());
                        startActivity(foodDetails);
                    }
                });

            }
        };

        mRecyclerFoodList.setAdapter(searchAdapter);
    }

    private void loadSuggest() {

        mFoodDataReference.orderByChild(AppConstants.KEY_MENU_ID).equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food item = postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadCategoryItems(String categoryId) {

        foodAdapter = new FirebaseRecyclerAdapter<Food, FoodListViewHolder>(Food.class,
                R.layout.layout_food_item,
                FoodListViewHolder.class,
                mFoodDataReference.orderByChild(AppConstants.KEY_MENU_ID).equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodListViewHolder viewHolder, Food model, final int position) {
                viewHolder.mFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.mFoodImageURL);
                final Food local = model;

                viewHolder.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void OnClick(View view, int Position, boolean isLongClick) {
                        Intent foodDetails = new Intent(FoodListActivity.this, FoodDetailsActivity.class);
                        foodDetails.putExtra(AppConstants.KEY_FOODID, foodAdapter.getRef(position).getKey());
                        startActivity(foodDetails);
                    }
                });

            }
        };

        mRecyclerFoodList.setAdapter(foodAdapter);
    }
}
