package com.foodpark.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodpark.Common.Common;
import com.foodpark.R;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private EditText etPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etPhoneNumber = (MaterialEditText) findViewById(R.id.et_phone_number);
        etPassword = (MaterialEditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin_activity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please waiting..");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(etPhoneNumber.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etPhoneNumber.getText().toString()).getValue(User.class);
                            user.setPhone(etPhoneNumber.getText().toString());
                            if (user.getPassword().equals(etPassword.getText().toString())) {
                                //Toast.makeText(SignInActivity.this, "Signin Successfully", Toast.LENGTH_SHORT).show();
                                Intent navigationIntent = new Intent(SignInActivity.this, NavigationActivity.class);
                                Common.currentUser = user;
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
        });
    }
}
