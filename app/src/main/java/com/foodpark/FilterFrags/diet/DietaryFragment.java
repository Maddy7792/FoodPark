package com.foodpark.FilterFrags.diet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;
import com.foodpark.FilterFrags.FilterList;
import com.foodpark.FilterFrags.sort.FilterAdapter;

import java.util.ArrayList;


/**
 * Created by dennis on 23/4/18.
 */

public class DietaryFragment extends Fragment {
    private RecyclerView dietRVList;
    private ArrayList<String> dietList;
    private FilterAdapter filterAdapter;
    public DietaryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dietary, container, false);
        dietRVList = view.findViewById(R.id.fp_diet_rv_list);
        dietRVList.setHasFixedSize(true);
        dietRVList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        dietList = new ArrayList<>();
        dietList = FilterList.getDietList();
        setDietData(dietList);
        return view;
    }

    private void setDietData(ArrayList<String> dietList) {
        filterAdapter = new FilterAdapter(getActivity(), dietList, FilterList.getDietImages());
        dietRVList.setAdapter(filterAdapter);
    }
}
