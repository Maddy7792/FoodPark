package com.foodpark.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.application.App;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FpProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName;
    private EditText etName;
    private Button btnUpdate;
    private ImageView Back;
    private Toolbar toolbar;
    private String data = null;
    private String type = null;
    private FirebaseDatabase database;
    private DatabaseReference userRefDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_profile_update);
        database = FirebaseDatabase.getInstance();
        userRefDatabase = database.getReference(AppConstants.KEY_USER).
                child(App.getInstance().getPhoneNumber());
        toolbar = findViewById(R.id.fp_toolbar);
        Back = toolbar.findViewById(R.id.fp_iv_update_back);
        tvName = findViewById(R.id.fp_tv_update_text);
        etName = findViewById(R.id.fp_et_update_text);
        btnUpdate = findViewById(R.id.btn_update);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(this);

        data = getIntent().getStringExtra(AppConstants.KEY_DATA);
        type = getIntent().getStringExtra(AppConstants.KEY_TYPE);
        setData(type, data);


    }

    private void setData(String type, String data) {
        if (type != null && data != null) {
            switch (type) {
                case AppConstants.KEY_FIRST_NAME:
                    tvName.setText(AppConstants.KEY_FIRST_NAME);
                    etName.setText(data);
                    enableCursor();
                    break;
                case AppConstants.KEY_SURNAME:
                    tvName.setText(AppConstants.KEY_SURNAME);
                    etName.setText(data);
                    enableCursor();
                    break;
                case AppConstants.KEY_EMAIL:
                    tvName.setText(AppConstants.KEY_EMAIL);
                    etName.setText(data);
                    enableCursor();
                    break;
                case AppConstants.KEY_PASSWORD:
                    tvName.setText(AppConstants.KEY_PASSWORD);
                    etName.setText(data);
                    enableCursor();
                    break;
                case AppConstants.KEY_PHONE_NUMBER:
                    tvName.setText(AppConstants.KEY_PHONE_NUMBER);
                    etName.setText(data);
                    enableCursor();
                    break;
            }
        }
    }

    private void enableCursor() {
        etName.setCursorVisible(true);
        etName.setSelection(data.length());
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.btn_update) {
            updataData();
        }
    }

    private void updataData() {
        userRefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    switch (type) {
                        case AppConstants.KEY_FIRST_NAME:
                            user.setName(etName.getText().toString());
                            userRefDatabase.setValue(user);
                            break;
                        case AppConstants.KEY_SURNAME:
                            user.setSurName(etName.getText().toString());
                            userRefDatabase.setValue(user);
                            break;
                        case AppConstants.KEY_EMAIL:
                            user.setEmail(etName.getText().toString());
                            userRefDatabase.setValue(user);
                            break;
                        case AppConstants.KEY_PASSWORD:
                            user.setPassword(etName.getText().toString());
                            userRefDatabase.setValue(user);
                            break;
                        case AppConstants.KEY_PHONE_NUMBER:
                            user.setPhone(etName.getText().toString());
                            userRefDatabase.setValue(user);
                            break;
                    }

                }
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR",""+databaseError);
            }
        });


    }
}
