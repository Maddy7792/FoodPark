package com.foodpark.auth;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Validation;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {


    private EditText etName;
    private EditText etPassword;
    private EditText etPhoneNumber;
    private EditText etSurName;
    private EditText etEmail;
    private Button btnSignUp;
    private Toolbar toolbar;
    private TextView fpTVToolBarName;
    private ImageView fpIVBack;
    private FirebaseDatabase database;
    private DatabaseReference table_user;
    private Spinner fpCountryCode;
    private TextView fpErrorText;
    private String errorText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = (EditText) findViewById(R.id.et_Name);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup_activity);
        etSurName = findViewById(R.id.et_SurName);
        etEmail = findViewById(R.id.et_email);
        fpErrorText = findViewById(R.id.fp_tv_validation_error);
        toolbar = findViewById(R.id.fp_signup_toolbar);
        fpCountryCode = findViewById(R.id.fp_country_code);
        fpIVBack = toolbar.findViewById(R.id.fp_tv_signup_toolbar_back);
        fpTVToolBarName = toolbar.findViewById(R.id.fp_tv_signup_toolbar_name);
        fpTVToolBarName.setText(getString(R.string.register));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        fpIVBack.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        setSpinnerData();
        fpCountryCode.setOnItemSelectedListener(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    setUser();
                }

            }
        });
    }

    private void setUser() {
        final ProgressDialog mDailog = new ProgressDialog(SignUpActivity.this);
        mDailog.setMessage("Please Waiting.....");
        mDailog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(etPhoneNumber.getText().toString()).exists()) {
                    mDailog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                } else {
                    mDailog.dismiss();
                    User user = new User(etName.getText().toString(),
                            etPassword.getText().toString(),
                            etSurName.getText().toString(),
                            etEmail.getText().toString());
                    table_user.child(etPhoneNumber.getText().toString()).setValue(user);
                    Toast.makeText(SignUpActivity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                    finish();
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

        if (Id == R.id.fp_tv_signup_toolbar_back) {
            finish();
        }
    }

    private void setSpinnerData() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.countryCodes, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fpCountryCode.setAdapter(arrayAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isValid() {
        if (Validation.isValidName(etName.getText().toString())) {
            if (Validation.isValidName(etSurName.getText().toString())) {
                if (Validation.isValidEmail(etEmail.getText().toString())) {
                    if (Validation.isValidPhoneNumber(etPhoneNumber.getText().toString())) {
                        if (Validation.isValidText(etPassword.getText().toString())) {
                            return true;
                        } else {
                            errorText = AppConstants.VALID_PASSWORD;
                            setErrorText(errorText);
                            return false;
                        }
                    } else {
                        errorText = AppConstants.VALID_PHONE;
                        setErrorText(errorText);
                        return false;
                    }
                } else {
                    errorText = AppConstants.VALID_EMAIL;
                    setErrorText(errorText);
                    return false;
                }
            } else {
                errorText = AppConstants.VALID_SUR_NAME;
                setErrorText(errorText);
                return false;
            }
        } else {
            errorText = AppConstants.VALID_FIRST_NAME;
            setErrorText(errorText);
            return false;
        }
    }

    private void setErrorText(String error) {
        fpErrorText.setText(error);
    }
}