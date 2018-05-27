package com.foodpark.SelectAuth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.foodpark.R;
import com.foodpark.auth.SignInActivity;
import com.foodpark.auth.SignUpActivity;

public class SplashLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fpBtnLogin;
    private Button fpBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        fpBtnLogin = findViewById(R.id.fp_btn_login);
        fpBtnRegister = findViewById(R.id.fp_btn_register);
        fpBtnLogin.setOnClickListener(this);
        fpBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_btn_login) {
            openSignInActivity();
        }
        if (Id == R.id.fp_btn_register) {
            openSignUpActivity();
        }
    }

    private void openSignInActivity() {
        Intent signIn = new Intent(SplashLoginActivity.this, SignInActivity.class);
        startActivity(signIn);
    }
    private void openSignUpActivity(){
        Intent signUp = new Intent(SplashLoginActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }
}
