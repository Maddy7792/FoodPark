package com.foodpark.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.foodpark.application.App;
import com.foodpark.Common.Common;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.Utils.Validation;
import com.foodpark.home.HomeActivity;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhoneNumber;
    private EditText etPassword;
    private Button btnSignIn;
    private Toolbar fpToolbar;
    private ImageView ivBack;
    private TextView tvToolBarText;
    private TextView fpTVForgetPassword;
    private TextView fpTVRegister;
    private DatabaseReference table_user;
    private FirebaseDatabase database;
    private Context context;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = this;
        Paper.init(context);
        rememberMe = findViewById(R.id.fp_remember_me);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin_activity);
        fpToolbar = findViewById(R.id.fp_signin_toolbar);
        fpTVRegister = findViewById(R.id.fp_tv_register);
        fpTVForgetPassword = findViewById(R.id.fp_forget_password);
        ivBack = fpToolbar.findViewById(R.id.fp_tv_toolbar_back);
        tvToolBarText = fpToolbar.findViewById(R.id.fp_tv_toolbar_name);
        tvToolBarText.setText(getString(R.string.log_in));
        setSupportActionBar(fpToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fpTVForgetPassword.setOnClickListener(this);
        fpTVRegister.setOnClickListener(this);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setCursor(etPassword);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCursor(etPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCursor(etPassword);
            }
        });
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setCursor(etPhoneNumber);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCursor(etPhoneNumber);
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCursor(etPhoneNumber);
            }
        });
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()) {
                    setupAccount();
                    Utils.hideKeyboard(SignInActivity.this);
                }

            }
        });
    }

    private void setupAccount() {
        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Please waiting..");
        mDialog.setCancelable(false);
        mDialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                App.getInstance().setPhoneNumber(etPhoneNumber.getText().toString());
                if (dataSnapshot.child(etPhoneNumber.getText().toString()).exists() &&
                        !etPhoneNumber.getText().toString().isEmpty()) {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(etPhoneNumber.getText().toString()).getValue(User.class);
                    if (user.getPassword().equals(etPassword.getText().toString()) &&
                            !etPassword.getText().toString().isEmpty()) {
                        //Remember Me for saving phoneNumber and passwords
                        if (rememberMe.isChecked()) {
                            Paper.book().write(AppConstants.KEY_PAPER_USER, etPhoneNumber.getText().toString());
                            Paper.book().write(AppConstants.KEY_PAPER_PASSWORD, etPassword.getText().toString());
                        } else {
                            Paper.book().delete(AppConstants.KEY_PAPER_USER);
                            Paper.book().delete(AppConstants.KEY_PAPER_PASSWORD);
                        }
                        //store phonenumber for checking forget activity
                        Utils.storePhoneNumber(context, etPhoneNumber.getText().toString());
                        Intent navigationIntent = new Intent(SignInActivity.this, HomeActivity.class);
                        Common.currentUser = user;
                        user.setPhone(etPhoneNumber.getText().toString());
                        Log.d("PhoneNumber", "" + user.getPhone());
                        App.getInstance().setUser(user);
                        startActivity(navigationIntent);
                        table_user.removeEventListener(this);
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this,
                                "Sign In failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignInActivity.this,
                            "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_forget_password) {
            Utils.setNormalIntent(this, FPForgetPasswordActivity.class);
        }

        if (Id == R.id.fp_tv_register) {
            openSignUPActivity();
        }
    }

    private void openSignUPActivity() {
        Intent signUp = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }

    private void setCursor(EditText editText) {
        editText.setCursorVisible(true);
    }

    boolean isValid() {
        if (Validation.isValidPhoneNumber(etPhoneNumber.getText().toString())) {
            if (Validation.isValidText(etPassword.getText().toString())) {
                return true;
            } else {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Enter PhoneNumber", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}
