package com.foodpark.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.foodpark.Common.Common;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.ViewHolders.OrderViewHolder;
import com.foodpark.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatusActivity extends AppCompatActivity {


    private RecyclerView listOfOrders;
    private RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase ordersDatabase;
    DatabaseReference orderRefDatabase;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        ordersDatabase = FirebaseDatabase.getInstance();
        orderRefDatabase = ordersDatabase.getReference(AppConstants.KEY_REQUESTS);
        listOfOrders = (RecyclerView) findViewById(R.id.list_of_orders);
        layoutManager = new LinearLayoutManager(this);
        listOfOrders.setHasFixedSize(true);
        listOfOrders.setLayoutManager(layoutManager);

        loadOrdersBasedOnPhone(Common.currentUser.getPhone());
    }

    private void loadOrdersBasedOnPhone(String phone) {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
              Request.class,
                R.layout.order_status_item_layout,
                OrderViewHolder.class,
                orderRefDatabase.orderByChild("phone").equalTo(phone)

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.tvOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.tvOrderPhone.setText(Common.currentUser.getPhone());
                viewHolder.tvOrderAddress.setText(model.getAddress());
                viewHolder.tvOrderStatus.setText(convertCodeToStatus(model.getStatus()));
            }
        };

        listOfOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("O"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getIntent().equals(AppConstants.KEY_UPDATE)){
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if (item.getIntent().equals(AppConstants.KEY_DELETE)){
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {

    }

    private void showUpdateDialog(String key, Request item) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(OrderStatusActivity.this);
        dialog.setTitle("");
    }
}
