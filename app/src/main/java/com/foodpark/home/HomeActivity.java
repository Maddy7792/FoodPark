package com.foodpark.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.customviews.BottomNavigationViewBehaviour;
import com.foodpark.customviews.BottomNotificationBadge;
import com.foodpark.database.Database;
import com.foodpark.dialogs.FPFilterDialog;
import com.foodpark.home.BottomNavigation.fragHome.HomeFragment;
import com.foodpark.home.BottomNavigation.fragOrder.OrdersFragment;
import com.foodpark.home.BottomNavigation.fragProfile.ProfileFragment;
import com.foodpark.home.BottomNavigation.fragSearch.SearchFragment;
import com.foodpark.home.BottomNavigation.fragcart.CartFragment;
import com.foodpark.model.Order;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FPFilterDialog filterDialog;
    private BottomNavigationView fpBottomNavigationView;
    private ActionBar actionBar;
    private TextView fpTVToolbarName;
    private CoordinatorLayout.LayoutParams layoutParams;
    private ImageView fpIVFilter;

    private Database localDb;
    private List<Order> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.fp_home_toolbar);
        fpIVFilter = toolbar.findViewById(R.id.fp_filter);
        fpTVToolbarName = toolbar.findViewById(R.id.fp_tv_home_toolbar_name);
        fpBottomNavigationView = findViewById(R.id.fp_bottomnavigation);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        fragmentManager = getSupportFragmentManager();
        fpBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigation);
        Utils.disableShiftMode(fpBottomNavigationView);
        localDb = new Database(this);
        fpIVFilter.setOnClickListener(this);
        layoutParams = (CoordinatorLayout.LayoutParams) fpBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());
        fpTVToolbarName.setText(AppConstants.HOME);
        loadFragment(new HomeFragment(), AppConstants.HOME);



    }

    @Override
    protected void onStart() {
        super.onStart();
        ordersList = localDb.getCarts();
        if (ordersList.size() > 0) {
            BottomNotificationBadge.setBottomNotificationBadge(this, ordersList.size(),
                    fpBottomNavigationView);
        }
    }

    @Override
    public void onClick(View view) {
        int Id = view.getId();

        if (Id == R.id.fp_filter) {
            filterDialog = new FPFilterDialog();
            filterDialog.show(fragmentManager, "filter");
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.fp_bn_home:
                            fpTVToolbarName.setText(AppConstants.HOME);
                            showViews();
                            fragment = new HomeFragment();
                            loadFragment(fragment, AppConstants.HOME);
                            return true;
                        case R.id.fp_bn_search:
                            fpTVToolbarName.setText(AppConstants.SEARCH);
                            hideViews();
                            fragment = new SearchFragment();
                            loadFragment(fragment, AppConstants.FRAGMENT_OTHER);
                            return true;
                        case R.id.fp_bn_orders:
                            fpTVToolbarName.setText(AppConstants.ORDERS);
                            hideViews();
                            fragment = new OrdersFragment();
                            loadFragment(fragment, AppConstants.FRAGMENT_OTHER);
                            return true;
                        case R.id.fp_bn_profile:
                            fpTVToolbarName.setText(AppConstants.PROFILE);
                            hideViews();
                            fragment = new ProfileFragment();
                            loadFragment(fragment, AppConstants.FRAGMENT_OTHER);
                            return true;

                        case R.id.fp_bn_cart:
                            fpTVToolbarName.setText(AppConstants.CART);
                            hideViews();
                            BottomNotificationBadge.removeBottomNotificationBadge(HomeActivity.this,
                                    fpBottomNavigationView);
                            fragment = new CartFragment();
                            loadFragment(fragment, AppConstants.FRAGMENT_OTHER);
                            return true;
                    }
                    return false;
                }
            };


    private void loadFragment(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fp_home, fragment);
        final int count = fragmentManager.getBackStackEntryCount();
        if (TAG.equals(AppConstants.FRAGMENT_OTHER)) {
            transaction.addToBackStack(TAG);
        }
        transaction.commit();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(AppConstants.FRAGMENT_OTHER, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    fpTVToolbarName.setText(AppConstants.HOME);
                    showViews();
                    fpBottomNavigationView.getMenu().getItem(0).setChecked(true);

                }
            }
        });
    }


    private void hideViews() {
        fpIVFilter.setVisibility(View.GONE);
    }

    private void showViews() {
        fpIVFilter.setVisibility(View.VISIBLE);
    }


}
