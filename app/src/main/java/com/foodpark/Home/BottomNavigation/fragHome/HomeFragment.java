package com.foodpark.Home.BottomNavigation.fragHome;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;
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

public class HomeFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recycler_menu;
    ArrayList<Category> list;
    private ProgressDialog progressDialog;
    private HomeViewHolder homeViewHolder;

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_menu = (RecyclerView) view.findViewById(R.id.fp_home_rv_list);
        recycler_menu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setItemAnimator(new DefaultItemAnimator());
        recycler_menu.setNestedScrollingEnabled(false);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Categories");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);
        loadMenu();
    }

    private void loadMenu() {
        progressDialog.show();
        list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    list.add(ds.getValue(Category.class));
                }
                setData(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    private void setData(ArrayList<Category> list) {
        progressDialog.dismiss();
        homeViewHolder = new HomeViewHolder(getActivity(), list);
        recycler_menu.setAdapter(homeViewHolder);

    }


}
