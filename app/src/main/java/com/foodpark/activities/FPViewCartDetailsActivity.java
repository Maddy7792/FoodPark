package com.foodpark.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.foodpark.Adapters.FPCartViewDetailsAdapter;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.database.Database;
import com.foodpark.model.Order;

import java.util.List;

public class FPViewCartDetailsActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Toolbar mToolBar;
    private ImageView mBack;
    private TextView mCardTitle;
    private RecyclerView fpRVCartDetails;
    private FPCartViewDetailsAdapter fpCartViewDetailsAdapter;
    private Database localDb;
    List<Order> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpview_cart_details);
        mToolBar = findViewById(R.id.fp_viewcart_toolbar);
        fpRVCartDetails = findViewById(R.id.fp_rv_cardview_details);
        setSupportActionBar(mToolBar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mBack = mToolBar.findViewById(R.id.fp_tv_viewcart_toolbar_back);
        mCardTitle = mToolBar.findViewById(R.id.fp_tv_viewcart_toolbar_text);
        fpRVCartDetails.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fpRVCartDetails.setHasFixedSize(true);
        mCardTitle.setText(AppConstants.KEY_YOUR_CART);
        mBack.setOnClickListener(this);
        getOrderDetails();
    }

    private void getOrderDetails() {
        localDb = new Database(this);
        orderList = localDb.getCarts();
        fpCartViewDetailsAdapter = new FPCartViewDetailsAdapter(this);
        fpCartViewDetailsAdapter.setData(orderList);
        fpRVCartDetails.setAdapter(fpCartViewDetailsAdapter);
        fpCartViewDetailsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fp_tv_viewcart_toolbar_back:
                FPViewCartDetailsActivity.this.finish();
                break;
        }
    }

}
