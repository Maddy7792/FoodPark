package com.foodpark.auth;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.FPPermissionUtils;
import com.foodpark.Utils.Utils;
import com.foodpark.application.SaveData;
import com.foodpark.dialogs.ChangePasswordDialog;

import io.paperdb.Paper;

public class FPForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView fpIVBack;
    private TextView toolbarName;
    private EditText etForget;
    private Button btnReset;
    private String phoneNumber;
    private ChangePasswordDialog changePasswordDialog;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpforget_password);
        if (!FPPermissionUtils.checkSmsPermissionStatus(FPForgetPasswordActivity.this)){
            FPPermissionUtils.EnableSmsPermissions(FPForgetPasswordActivity.this);
        }

        btnReset = findViewById(R.id.btn_forget);
        etForget = findViewById(R.id.et_forget_phone);
        toolbar = findViewById(R.id.fp_forget_toolbar);
        fpIVBack = toolbar.findViewById(R.id.fp_forget_toolbar_back);
        toolbarName = toolbar.findViewById(R.id.fp_forget_toolbar_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        toolbarName.setText(getString(R.string.forget_password));
        fpIVBack.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        phoneNumber = Utils.getPhoneNumber(FPForgetPasswordActivity.this);
        Log.d("phoneNumber-Forget",""+Utils.getPhoneNumber(FPForgetPasswordActivity.this));
        etForget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etForget.getText().toString().length()<=3){
                    btnReset.setEnabled(true);
                    btnReset.setAlpha(1.0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fragmentManager = getFragmentManager();


    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_forget_toolbar_back) {
            finish();
        }

        if (Id == R.id.btn_forget){
            if (!TextUtils.isEmpty(etForget.getText().toString()) && phoneNumber!=null
                    && phoneNumber.matches(etForget.getText().toString())){
                SaveData.getInstance().setPhoneNumber(etForget.getText().toString());
                //Utils.sendOTPToUsers(this,phoneNumber,Utils.generateRandomNumber());
                Utils.storeOtpNumber(this,Utils.generateRandomNumber());
                btnReset.setEnabled(false);
                btnReset.setAlpha(0.5f);
                changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(fragmentManager,"Dialog");
               // Toast.makeText(this, "Success Forget" +Utils.generateRandomNumber(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Enter Valid PhoneNumber", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
