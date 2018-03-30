package com.foodpark.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.foodpark.R;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {


    private MaterialEditText etName;
    private MaterialEditText etPassword;
    private MaterialEditText etPhoneNumber;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName=(MaterialEditText)findViewById(R.id.et_Name);
        etPhoneNumber=(MaterialEditText)findViewById(R.id.et_phone_number);
        etPassword=(MaterialEditText)findViewById(R.id.et_password);
        btnSignUp=(Button)findViewById(R.id.btn_signup_activity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDailog = new ProgressDialog(SignUpActivity.this);
                mDailog.setMessage("Please Waiting.....");
                mDailog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(etPhoneNumber.getText().toString()).exists()){
                            mDailog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                        }else {
                            mDailog.dismiss();
                            User user = new User(etName.getText().toString(),etPassword.getText().toString());
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
        });
    }
}
