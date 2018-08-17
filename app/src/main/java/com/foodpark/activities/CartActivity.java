package com.foodpark.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpark.Common.Common;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Adapters.CartAdapter;
import com.foodpark.database.Database;
import com.foodpark.model.Order;
import com.foodpark.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {


    private RecyclerView cartRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalAmount;
    private Button btnPlaceOrder;
    private FirebaseDatabase cartDatabase;
    private DatabaseReference cartReference;
    private String status = AppConstants.KEY_ORDER_PLACED;

    List<Order> carts;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartDatabase = FirebaseDatabase.getInstance();
        cartReference = cartDatabase.getReference(AppConstants.KEY_REQUESTS);
        totalAmount = (TextView)findViewById(R.id.total_price);
        btnPlaceOrder=(Button)findViewById(R.id.btn_place_order);
        cartRecyclerView=(RecyclerView)findViewById(R.id.listCart);
        cartRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(layoutManager);
        carts = new ArrayList<>();
        loadListFoodFromSqlite();
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogForAddress();
            }
        });
    }

    private void showAlertDialogForAddress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setMessage("Enter Your Address");
        alertDialog.setTitle("One More Step!");

        final EditText etAddress = new EditText(CartActivity.this);

        LinearLayout.LayoutParams alertLinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.MATCH_PARENT);

        etAddress.setLayoutParams(alertLinear);

        alertDialog.setView(etAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        etAddress.getText().toString(),
                        carts,
                        totalAmount.getText().toString(),"0"
                );

                cartReference.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(CartActivity.this).deleteToCart();
                Toast.makeText(CartActivity.this, "Thank You, Order place", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFoodFromSqlite() {

        carts = new Database(this).getCarts();
        cartAdapter = new CartAdapter(carts,this);
        cartRecyclerView.setAdapter(cartAdapter);

        int total = 0;

        for (Order order:carts)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        totalAmount.setText(fmt.format(total));
    }
}
