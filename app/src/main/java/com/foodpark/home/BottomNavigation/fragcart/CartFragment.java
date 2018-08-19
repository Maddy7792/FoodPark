package com.foodpark.home.BottomNavigation.fragcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpark.Common.Common;
import com.foodpark.Common.Config;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Adapters.CartAdapter;
import com.foodpark.database.Database;
import com.foodpark.model.Order;
import com.foodpark.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dennis on 26/5/18.
 */

public class CartFragment extends Fragment {

    private static final int PAYPAL_REQ_CODE = 7792;
    private RecyclerView cartRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalAmount;
    private Button btnPlaceOrder;
    private FirebaseDatabase cartDatabase;
    private DatabaseReference cartReference;
    private String status = AppConstants.KEY_ORDER_PLACED;
    private String address;
    int total;

    List<Order> carts;
    CartAdapter cartAdapter;

    static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_KEY);

    public CartFragment() {
    }

    @Nullable
    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cart_fragment, null);
        cartDatabase = FirebaseDatabase.getInstance();
        cartReference = cartDatabase.getReference(AppConstants.KEY_REQUESTS);
        totalAmount = (TextView) view.findViewById(R.id.total_price);
        btnPlaceOrder = (Button) view.findViewById(R.id.btn_place_order);
        cartRecyclerView = (RecyclerView) view.findViewById(R.id.listCart);
        cartRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        cartRecyclerView.setLayoutManager(layoutManager);
        carts = new ArrayList<>();
        loadListFoodFromSqlite();
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalAmount != null && total > 0) {
                    showAlertDialogForAddress();
                }

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        if (getActivity() != null) {
            getActivity().startService(intent);
        } else {
            Toast.makeText(getActivity(), "Activity is null", Toast.LENGTH_SHORT).show();
        }

    }

    private void showAlertDialogForAddress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Enter Your Address");
        alertDialog.setTitle("One More Step!");
        final EditText etAddress = new EditText(getActivity());
        LinearLayout.LayoutParams alertLinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT);

        etAddress.setLayoutParams(alertLinear);

        alertDialog.setView(etAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                address = etAddress.getText().toString();
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        address,
                        carts,
                        totalAmount.getText().toString(), "0"
                );
                cartReference.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(getActivity()).deleteToCart();
                restartFragment();
                Toast.makeText(getActivity(), "Thank You, Order place", Toast.LENGTH_SHORT).show();
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
        carts = new Database(getActivity()).getCarts();
        cartAdapter = new CartAdapter(carts, getActivity());
        cartRecyclerView.setAdapter(cartAdapter);
        total = 0;

        for (Order order : carts)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        totalAmount.setText(fmt.format(total));
    }


    private void restartFragment() {
        CartFragment fragment = null;
        if (getFragmentManager() != null) {
            fragment = (CartFragment)
                    getFragmentManager().findFragmentById(R.id.fp_home);
            getFragmentManager().beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .commit();
        }

    }

}
