package com.foodpark.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.App.SaveData;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.customviews.BottomNavigationViewBehaviour;
import com.foodpark.dialogs.FPFilterDialog;
import com.foodpark.Home.BottomNavigation.fragHome.HomeFragment;
import com.foodpark.Home.BottomNavigation.fragOrder.OrdersFragment;
import com.foodpark.Home.BottomNavigation.fragProfile.ProfileFragment;
import com.foodpark.Home.BottomNavigation.fragSearch.SearchFragment;

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
        fpIVFilter.setOnClickListener(this);
        layoutParams = (CoordinatorLayout.LayoutParams) fpBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());
        fpTVToolbarName.setText(AppConstants.HOME);
        loadFragment(new HomeFragment(), AppConstants.HOME);
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

   /* @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }*/

    private void hideViews() {
        fpIVFilter.setVisibility(View.GONE);
    }

    private void showViews() {
        fpIVFilter.setVisibility(View.VISIBLE);
    }

}
