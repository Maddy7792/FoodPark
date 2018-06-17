package com.foodpark.home.BottomNavigation.fragOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodpark.R;

/**
 * Created by dennis on 26/5/18.
 */

public class OrdersStatusFragment extends Fragment {

    public OrdersStatusFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order_status, null);
        return view;
    }
}
