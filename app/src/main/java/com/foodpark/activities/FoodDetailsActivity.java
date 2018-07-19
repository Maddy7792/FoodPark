package com.foodpark.activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.database.Database;
import com.foodpark.model.Food;
import com.foodpark.model.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mFoodName;
    private TextView mFoodPrice;
    private TextView mDescription;
    private ImageView mFoodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton mBtnCart;
    private ElegantNumberButton numberButton;
    private String foodId = "";
    private FirebaseDatabase foodDatabase;
    private DatabaseReference foodDataReference;
    private Food food;
    private CardView mViewCart;
    private TextView mCartMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        foodDatabase = FirebaseDatabase.getInstance();
        foodDataReference = foodDatabase.getReference(AppConstants.KEY_FOOD);
        mViewCart = findViewById(R.id.fp_card_view_cart);
        mCartMoney = findViewById(R.id.fp_tv_view_money);
        mFoodName = (TextView) findViewById(R.id.food_name);
        mFoodPrice = (TextView) findViewById(R.id.food_price);
        mDescription = (TextView) findViewById(R.id.food_desc);
        mFoodImage = (ImageView) findViewById(R.id.food_details_image);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        mBtnCart = (FloatingActionButton) findViewById(R.id.float_cart);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);


        mBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        food.getName(),
                        numberButton.getNumber(),
                        food.getPrice(),
                        food.getDiscount()
                ));
                mViewCart.setVisibility(View.VISIBLE);
                mCartMoney.setText("$" + (Integer.parseInt(numberButton.getNumber()) * Integer.parseInt(food.getPrice())));
                Toast.makeText(FoodDetailsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        if (getIntent() != null) {
            foodId = getIntent().getStringExtra(AppConstants.KEY_FOODID);
        }

        if (!foodId.isEmpty() && foodId != null) {
            getFoodDetails(foodId);
        }

        mViewCart.setOnClickListener(this);

    }

    private void getFoodDetails(final String foodId) {
        foodDataReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(mFoodImage);
                mFoodName.setText(food.getName());
                mDescription.setText(food.getDescription());
                mFoodPrice.setText(food.getPrice());
                collapsingToolbarLayout.setTitle(food.getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_card_view_cart) {
            Intent intent = new Intent(FoodDetailsActivity.this, FPViewCartDetailsActivity.class);
            startActivity(intent);
            mViewCart.setVisibility(View.GONE);
        }
    }
}
