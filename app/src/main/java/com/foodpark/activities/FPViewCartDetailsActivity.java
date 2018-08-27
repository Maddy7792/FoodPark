package com.foodpark.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.foodpark.Adapters.FPCartViewDetailsAdapter;
import com.foodpark.Adapters.FavouritesViewAdapter;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Logger;
import com.foodpark.Utils.Utils;
import com.foodpark.ViewHolders.FPCartViewDetailsViewHolder;
import com.foodpark.callback.OnDeleteItem;
import com.foodpark.database.Database;
import com.foodpark.model.Favourites;
import com.foodpark.model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FPViewCartDetailsActivity extends AppCompatActivity
        implements View.OnClickListener ,OnDeleteItem{

    private Toolbar mToolBar;
    private ImageView mBack;
    private TextView mCardTitle;
    private RecyclerView fpRVCartDetails;
    private FPCartViewDetailsAdapter fpCartViewDetailsAdapter;
    private Database localDb;
    List<Order> orderList;
    private LinearLayout fpLLAmountDetails;
    private TextView fpTVSubTotalAmount;
    private TextView fpTVTaxesAmount;
    private TextView fpTVGrandAmount;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpview_cart_details);
        mToolBar = findViewById(R.id.fp_viewcart_toolbar);
        fpLLAmountDetails = findViewById(R.id.fp_ll_amount_details);
        fpTVGrandAmount = findViewById(R.id.fp_tv_grand_amount);
        fpTVSubTotalAmount = findViewById(R.id.fp_tv_sub_total_amount);
        fpTVTaxesAmount = findViewById(R.id.fp_tv_taxes_amount);
        fpRVCartDetails = findViewById(R.id.fp_rv_cardview_details);
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mBack = mToolBar.findViewById(R.id.fp_tv_viewcart_toolbar_back);
        mCardTitle = mToolBar.findViewById(R.id.fp_tv_viewcart_toolbar_text);
        fpRVCartDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fpRVCartDetails.setHasFixedSize(true);
        mCardTitle.setText(AppConstants.KEY_YOUR_CART);
        mBack.setOnClickListener(this);
        getOrderDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (localDb.getCountCards()>0){
            fpLLAmountDetails.setVisibility(View.VISIBLE);
        }else {
            fpLLAmountDetails.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fp_tv_viewcart_toolbar_back:
                FPViewCartDetailsActivity.this.finish();
                break;
        }
    }

    private void getOrderDetails() {
        localDb = new Database(this);
        orderList = localDb.getCarts();
        fpCartViewDetailsAdapter = new FPCartViewDetailsAdapter(this);
        fpCartViewDetailsAdapter.setData(orderList);
        fpRVCartDetails.setAdapter(fpCartViewDetailsAdapter);
        fpCartViewDetailsAdapter.notifyDataSetChanged();

        for (Order order:orderList)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        fpTVSubTotalAmount.setText(fmt.format(total));
        fpTVTaxesAmount.setText(fmt.format(Utils.percentageCalculation(total)));
        fpTVGrandAmount.setText(fmt.format(total+Utils.percentageCalculation(total)));
    }

    @Override
    public void OnDelete(RecyclerView.ViewHolder viewHolder, int position, int numberValue) {
        if (viewHolder instanceof FPCartViewDetailsViewHolder){
            Logger.d("Number--ElegantNumber","---"+numberValue);
            if (numberValue<1){
                final Order deleteItem = ((FPCartViewDetailsAdapter)fpRVCartDetails.getAdapter())
                        .getItem(viewHolder.getAdapterPosition());
                fpCartViewDetailsAdapter.remove(position);
                new Database(this).removeCartItem(deleteItem.getProductId());

            }
        }
    }
}
