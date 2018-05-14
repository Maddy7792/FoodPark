package com.foodpark.Home.BottomNavigation.fragSearch;


import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.customviews.GridSpacingItemDecoration;
import com.foodpark.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dennis on 10/5/18.
 */

public class SearchFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference refTopCatories;
    private DatabaseReference refMoreCatories;
    private RecyclerView rvTopCategories;
    private RecyclerView rvMoreCategories;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridMoreLayoutManager;
    private ArrayList<Category> topCategoriesList;
    private ArrayList<Category> moreCategoriesList;
    private SearchViewAdapter searchViewAdapter;
    private ProgressDialog progressDialog;


    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_search_fragment, container, false);
        rvTopCategories = view.findViewById(R.id.fp_rv_top_categories);
        rvMoreCategories = view.findViewById(R.id.fp_rv_more_categories);
        rvTopCategories.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance();
        refTopCatories = database.getReference(AppConstants.KEY_TOP_CATEGORIES);
        refMoreCatories = database.getReference(AppConstants.KEY_MORE_CATEGORIES);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvTopCategories.setLayoutManager(gridLayoutManager);
        rvTopCategories.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        rvTopCategories.setItemAnimator(new DefaultItemAnimator());
        gridMoreLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvMoreCategories.setLayoutManager(gridMoreLayoutManager);
        rvMoreCategories.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        rvMoreCategories.setItemAnimator(new DefaultItemAnimator());
        topCategoriesList = new ArrayList<>();
        moreCategoriesList = new ArrayList<>();
        progressDialog = Utils.showProgressDialog(getActivity());
        LoadTopCategories();
        LoadMoreCategories();
        return view;
    }

    private void LoadMoreCategories() {
        refMoreCatories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    moreCategoriesList.add(ds.getValue(Category.class));
                }

                setMoreData(moreCategoriesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void LoadTopCategories() {
        progressDialog.show();
        refTopCatories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    topCategoriesList.add(ds.getValue(Category.class));
                }

                setTopData(topCategoriesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    private void setTopData(ArrayList<Category> topCategoriesList) {
        progressDialog.dismiss();
        searchViewAdapter = new SearchViewAdapter(getActivity());
        searchViewAdapter.setTopCategories(topCategoriesList);
        rvTopCategories.setAdapter(searchViewAdapter);
    }

    private void setMoreData(ArrayList<Category> moreCategoriesList) {
        progressDialog.dismiss();
        searchViewAdapter = new SearchViewAdapter(getActivity());
        searchViewAdapter.setMoreCategoriesData(moreCategoriesList);
        rvMoreCategories.setAdapter(searchViewAdapter);
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
