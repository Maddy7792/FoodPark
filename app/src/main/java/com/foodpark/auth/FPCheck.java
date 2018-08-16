package com.foodpark.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.foodpark.application.App;
import com.foodpark.Common.Common;
import com.foodpark.home.HomeActivity;
import com.foodpark.selectAuth.SplashLoginActivity;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

/**
 * Created by dennis on 20/5/18.
 */

public class FPCheck extends Activity {
    private String user;
    private String password;
    private DatabaseReference table_user;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        Paper.init(this);
        user = Paper.book().read(AppConstants.KEY_PAPER_USER);
        password = Paper.book().read(AppConstants.KEY_PAPER_PASSWORD);
        SharedPreferences sharedPreferences = getSharedPreferences("firstrun", 0);
        boolean firstRun = sharedPreferences.getBoolean("firstrun", false);
        if (!firstRun) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstrun", true);
            editor.commit();
            Utils.setNormalIntent(FPCheck.this, SplashLoginActivity.class);
            finish();
        } else {
            if (user != null && password != null) {
                if (!user.isEmpty() && !password.isEmpty()) {
                    signin(user, password);
                }
            } else {
                Utils.setNormalIntent(FPCheck.this, SignInActivity.class);
                finish();
            }

        }
    }

    private void signin(final String phone, final String password) {
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                App.getInstance().setPhoneNumber(phone);
                if (dataSnapshot.child(phone).exists() && !phone.isEmpty()) {
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    if (user.getPassword().equals(password) && !password.isEmpty()) {
                        Intent navigationIntent = new Intent(FPCheck.this, HomeActivity.class);
                        Common.currentUser = user;
                        user.setPhone(phone);
                        Log.d("PhoneNumber", "" + user.getPhone());
                        App.getInstance().setUser(user);
                        startActivity(navigationIntent);
                        table_user.removeEventListener(this);
                        finish();
                    } else {
                        Toast.makeText(FPCheck.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FPCheck.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
