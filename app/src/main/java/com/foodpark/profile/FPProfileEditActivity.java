package com.foodpark.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodpark.App.SaveData;
import com.foodpark.R;
import com.foodpark.model.User;

public class FPProfileEditActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView fpTVName;
    private TextView fpTVSurname;
    private TextView fpTVEmail;
    private TextView fpTVPhoneNumber;
    private TextView fpTVPassword;
    private ImageView fpBack;
    private ImageView fpProfileAvatar;
    private ImageView fpProfileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_profile_edit);
        fpProfileEdit = findViewById(R.id.fp_edit_small_image);
        fpProfileAvatar = findViewById(R.id.fp_iv_profile_avatar);
        fpBack = findViewById(R.id.fp_edit_back);
        fpTVName = findViewById(R.id.fp_tv_edit_name);
        fpTVEmail = findViewById(R.id.fp_tv_edit_email);
        fpTVPhoneNumber = findViewById(R.id.fp_tv_edit_phone);
        fpTVSurname = findViewById(R.id.fp_tv_edit_sur_name);
        fpTVPassword = findViewById(R.id.fp_tv_edit_password);
        setDetails();
        fpBack.setOnClickListener(this);
    }

    private void setDetails() {
        User user = SaveData.getInstance().getUser();
        fpTVName.setText(user.getName());
        fpTVSurname.setText(user.getSurName());
        fpTVEmail.setText(user.getEmail());
        fpTVPhoneNumber.setText(user.getPhone());
        fpTVPassword.setText(user.getPassword());
    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();

        if (Id == R.id.fp_edit_back) {
            finish();
        }
    }
}
