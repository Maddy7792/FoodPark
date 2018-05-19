package com.foodpark.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.App.SaveData;
import com.foodpark.R;
import com.foodpark.Utils.Utils;
import com.foodpark.auth.SignInActivity;
import com.foodpark.model.User;

public class FPSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar fpToolBar;
    private TextView fpTVEdit;
    private ImageView fpIVBack;
    private EditText etName;
    private EditText etEmail;
    private EditText etSurName;
    private EditText etPhoneNumber;
    private TextView fpSignOut;
    private TextView fpHomeAddress;
    private TextView fpWorkAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_settings);
        Utils.hideKeyboard(this);
        fpToolBar = findViewById(R.id.fp_settings_toolbar);
        fpIVBack = fpToolBar.findViewById(R.id.fp_iv_tb_settings_back);
        fpTVEdit = fpToolBar.findViewById(R.id.fp_tv_setting_edit);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        etName = findViewById(R.id.et_setting_Name);
        etEmail = findViewById(R.id.et_setting_email);
        etPhoneNumber = findViewById(R.id.et_setting_phone_number);
        etSurName = findViewById(R.id.et_setting_SurName);
        fpSignOut = findViewById(R.id.fp_tv_signout);
        fpWorkAddress = findViewById(R.id.fp_tv_address_work);
        fpHomeAddress = findViewById(R.id.fp_tv_address_home);

        if (SaveData.getInstance().getUser() != null) {
            User user = SaveData.getInstance().getUser();
            etName.setText(user.getName());
            etSurName.setText(user.getSurName());
            if (SaveData.getInstance().getPhoneNumber() != null) {
                etPhoneNumber.setText(user.getPhone());
                Log.d("Phone", "" + user.getPhone());
            }
            etEmail.setText(user.getEmail());
        }

        fpIVBack.setOnClickListener(this);
        fpTVEdit.setOnClickListener(this);
        fpSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_iv_tb_settings_back) {
            finish();
        }

        if (Id == R.id.fp_tv_setting_edit) {
            Utils.setNormalIntent(this, FPProfileEditActivity.class);
        }

        if (Id == R.id.fp_tv_signout) {
            Utils.signOut(this, SignInActivity.class);
        }
    }
}
