package com.foodpark.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.foodpark.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSignUp;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSignIn=(Button) findViewById(R.id.btn_signin);
        btnSignUp=(Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int Id = view.getId();

        if (Id == R.id.btn_signin){
            Intent signIn = new Intent(HomeActivity.this,SignInActivity.class);
            startActivity(signIn);
        }
        if (Id == R.id.btn_signup){
            Intent signUp = new Intent(HomeActivity.this,SignUpActivity.class);
            startActivity(signUp);
        }
    }
}
