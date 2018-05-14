package com.foodpark.FilterFrags.sort;

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

import java.util.ArrayList;


/**
 * Created by dennis on 23/4/18.
 */

public class SortFragment extends Fragment {

    private RecyclerView sortRVList;
    private ArrayList<String> sortList;
    private FilterAdapter filterAdapter;
    private int[] imageList = {R.drawable.recommended,
            R.drawable.most_popular,
            R.drawable.rating,
            R.drawable.delivery};

    public SortFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sort, container, false);
        sortRVList = view.findViewById(R.id.fp_sort_rv_list);
        sortRVList.setHasFixedSize(true);
        sortRVList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        sortList = new ArrayList<>();
        sortList = FilterList.getSortList();
        setSortData(sortList);
        return view;
    }

    private void setSortData(ArrayList<String> sortList) {
        filterAdapter = new FilterAdapter(getActivity(), sortList,imageList);
        sortRVList.setAdapter(filterAdapter);

    }
}
