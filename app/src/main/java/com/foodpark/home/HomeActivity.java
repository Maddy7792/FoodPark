package com.foodpark.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.foodpark.R;
import com.foodpark.dialogs.FPFilterDialog;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FPFilterDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.fp_home_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View view) {
        int Id = view.getId();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_item, menu);
        MenuItem filter = menu.findItem(R.id.fp_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int Id = item.getItemId();
        if (Id == R.id.fp_filter) {
            filterDialog = new FPFilterDialog();
            filterDialog.show(fragmentManager, "filter");
        }
        return super.onOptionsItemSelected(item);
    }
}
