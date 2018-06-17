package com.foodpark.home.BottomNavigation.fragSearch;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodpark.application.SaveData;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.customviews.GridSpacingItemDecoration;
import com.foodpark.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dennis on 10/5/18.
 */

public class SearchFragment extends Fragment implements
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

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
    private SearchView fpSearchView;
    private ArrayList<Category> searchList;
    private TextView fpTVMoreCategory;
    private ArrayList<String> autoSuggestList;


    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_search_fragment, container, false);
        rvTopCategories = view.findViewById(R.id.fp_rv_top_categories);
        rvMoreCategories = view.findViewById(R.id.fp_rv_more_categories);
        fpSearchView = view.findViewById(R.id.fp_categories_search);
        fpTVMoreCategory = view.findViewById(R.id.fp_tv_more_categories);
        rvTopCategories.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance();
        refTopCatories = database.getReference(AppConstants.KEY_TOP_CATEGORIES);
        refMoreCatories = database.getReference(AppConstants.KEY_MORE_CATEGORIES);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvTopCategories.setLayoutManager(gridLayoutManager);
        rvTopCategories.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(8, getActivity()), true));
        rvTopCategories.setItemAnimator(new DefaultItemAnimator());
        gridMoreLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvMoreCategories.setLayoutManager(gridMoreLayoutManager);
        rvMoreCategories.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(8, getActivity()), true));
        rvMoreCategories.setItemAnimator(new DefaultItemAnimator());
        rvTopCategories.setNestedScrollingEnabled(false);
        rvMoreCategories.setNestedScrollingEnabled(false);
        topCategoriesList = new ArrayList<>();
        moreCategoriesList = new ArrayList<>();
        searchList = new ArrayList<>();
        autoSuggestList = new ArrayList<>();
        progressDialog = Utils.showProgressDialog(getActivity());
        fpSearchView.setOnQueryTextListener(this);
        fpSearchView.setQueryHint(getString(R.string.search_hint));
        fpSearchView.setOnCloseListener(this);
        LoadTopCategories();
        LoadAllCategories();
        return view;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        startSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void startSearch(final String query) {
        Log.d("Query", "" + query);
        SaveData.getInstance().setSearchQuery(query);
        Query topReference = refTopCatories.orderByChild(AppConstants.SEARCH_KEY).startAt(query).endAt(query + "\uf8ff");
        topReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                searchList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    searchList.add(ds.getValue(Category.class));
                }
                setTopData(searchList);
                hideViews();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                LoadMoreCategories();

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
        searchViewAdapter.setType(AppConstants.KEY_TOP);
        searchViewAdapter.setTopCategories(topCategoriesList);
        rvTopCategories.setAdapter(searchViewAdapter);
    }

    private void setMoreData(ArrayList<Category> moreCategoriesList) {
        progressDialog.dismiss();
        searchViewAdapter = new SearchViewAdapter(getActivity());
        searchViewAdapter.setType(AppConstants.KEY_MORE);
        searchViewAdapter.setMoreCategoriesData(moreCategoriesList);
        rvMoreCategories.setAdapter(searchViewAdapter);
    }

    private void LoadAllCategories() {
        refTopCatories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoSuggestList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    autoSuggestList.add(ds.getValue(Category.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onClose() {
        LoadTopCategories();
        showViews();
        return false;
    }

    private void showViews() {
        rvMoreCategories.setVisibility(View.VISIBLE);
        fpTVMoreCategory.setVisibility(View.VISIBLE);

    }

    private void hideViews() {
        rvMoreCategories.setVisibility(View.GONE);
        fpTVMoreCategory.setVisibility(View.GONE);

    }


}
