package com.foodpark.FilterFrags.price;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;
import com.foodpark.customviews.RecyclerviewItemDecoration;
import com.foodpark.FilterFrags.FilterList;
import com.foodpark.model.Price;

import java.util.ArrayList;


/**
 * Created by dennis on 23/4/18.
 */

public class PriceFragment extends Fragment {
    private RecyclerView fpRVPriceList;
    private ArrayList<Price> priceList;
    private PriceAdapter priceAdapter;
    private RecyclerviewItemDecoration recyclerviewItemDecoration;

    public PriceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_price, container, false);
        fpRVPriceList = (RecyclerView) view.findViewById(R.id.fp_price_rv_list);
        fpRVPriceList.setHasFixedSize(true);
        fpRVPriceList.setLayoutManager(new GridLayoutManager(getActivity(), 2,LinearLayoutManager.HORIZONTAL, false));
        recyclerviewItemDecoration = new RecyclerviewItemDecoration(getContext(),R.dimen.offset);
        fpRVPriceList.addItemDecoration(recyclerviewItemDecoration);
        priceList = FilterList.getPrices();
        setPriceData(priceList);
        return view;
    }

    private void setPriceData(ArrayList<Price> priceList) {
        priceAdapter = new PriceAdapter(getActivity(), priceList);
        fpRVPriceList.setAdapter(priceAdapter);
    }
}
