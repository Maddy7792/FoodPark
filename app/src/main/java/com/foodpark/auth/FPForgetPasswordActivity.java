package com.foodpark.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.R;

public class FPForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView fpIVBack;
    private TextView toolbarName;
    private EditText etForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpforget_password);
        toolbar = findViewById(R.id.fp_forget_toolbar);
        fpIVBack = toolbar.findViewById(R.id.fp_forget_toolbar_back);
        toolbarName = toolbar.findViewById(R.id.fp_forget_toolbar_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        toolbarName.setText(getString(R.string.forget_password));
        fpIVBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_forget_toolbar_back) {
            finish();
        }
    }
}
