package com.foodpark.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.foodpark.App.SaveData;
import com.foodpark.Common.Common;
import com.foodpark.R;
import com.foodpark.Utils.Validation;
import com.foodpark.Home.HomeActivity;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
                }

            }
        });
    }

    private void setupAccount() {
        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Please waiting..");
        mDialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SaveData.getInstance().setPhoneNumber(etPhoneNumber.getText().toString());
                if (dataSnapshot.child(etPhoneNumber.getText().toString()).exists() && !etPhoneNumber.getText().toString().isEmpty()) {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(etPhoneNumber.getText().toString()).getValue(User.class);
                    if (user.getPassword().equals(etPassword.getText().toString()) && !etPassword.getText().toString().isEmpty()) {
                        Intent navigationIntent = new Intent(SignInActivity.this, HomeActivity.class);
                        Common.currentUser = user;
                        user.setPhone(etPhoneNumber.getText().toString());
                        Log.d("PhoneNumber",""+user.getPhone());
                        SaveData.getInstance().setUser(user);
                        startActivity(navigationIntent);
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show();
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
