package com.foodpark.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.foodpark.R;
import com.foodpark.Utils.AppConstants;
import com.foodpark.Utils.Utils;
import com.foodpark.Utils.Validation;
import com.foodpark.application.SaveData;
import com.foodpark.auth.FPForgetPasswordActivity;
import com.foodpark.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordDialog extends DialogFragment implements View.OnClickListener {

    Dialog dialog;
    private TextView fpTVTitle;
    private TextView fpTVTitleMessage;
    private EditText etOTP;
    private EditText etNewPassword;
    private EditText etRetypePassword;
    private Button btnConfirm;
    private Button btnReset;
    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private LottieAnimationView lottieAnimationView;

    public ChangePasswordDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FP_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int screenHeight = Utils.deviceDimensions(getActivity(), 100, AppConstants.WIDTH);
            dialog.getWindow().setLayout(width, height);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat
                    .getColor(getActivity(), R.color.colorDialogBack)));
            getDialog().getWindow().setGravity(Gravity.BOTTOM);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.changepassword_dialog_layout, container);
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference(AppConstants.KEY_USER);
        lottieAnimationView = view.findViewById(R.id.loading);
        fpTVTitle = view.findViewById(R.id.fp_tv_reset_text);
        fpTVTitleMessage = view.findViewById(R.id.fp_tv_reset_message);
        etOTP = view.findViewById(R.id.et_otp);
        etNewPassword = view.findViewById(R.id.et_change_password);
        etRetypePassword = view.findViewById(R.id.et_retype_password);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnReset = view.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        Log.d("OTP-Number", "" + Utils.getOtpNumber(getActivity()));
        return view;
    }


    @Override
    public void onClick(View v) {
        int Id = v.getId();
        if (Id == R.id.btn_reset) {
            if (!TextUtils.isEmpty(etOTP.getText().toString())) {
                String otp = etOTP.getText().toString();
                if (otp != null && otp.equalsIgnoreCase(String.valueOf(Utils.getOtpNumber(getActivity())))) {
                    etNewPassword.setVisibility(View.VISIBLE);
                    etRetypePassword.setVisibility(View.VISIBLE);
                    etOTP.setVisibility(View.GONE);
                    btnConfirm.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "OTP is InCorrect", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please Enter Otp Number", Toast.LENGTH_SHORT).show();
            }
        }

        if (Id == R.id.btn_confirm) {
            if (isValid()) {
                String newPassword = etNewPassword.getText().toString();
                String retypePassword = etRetypePassword.getText().toString();
                if (newPassword.matches(retypePassword)) {
                    lottieAnimationView.playAnimation();
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    updatePassword(newPassword);
                } else {
                    Toast.makeText(getActivity(), "Check the passwords once again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*Update the password
     * */
    private void updatePassword(final String newPassword) {
        userReference.child(SaveData.getInstance().getPhoneNumber())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            user.setPassword(newPassword);
                            userReference.getRef().child(SaveData.getInstance().getPhoneNumber()).setValue(user);
                        }
                        userReference.removeEventListener(this);
                        lottieAnimationView.setVisibility(View.GONE);
                        lottieAnimationView.cancelAnimation();
                        getDialog().dismiss();
                        getActivity().finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private boolean isValid() {
        if (Validation.isValidText(etNewPassword.getText().toString())) {
            if (Validation.isValidText(etRetypePassword.getText().toString())) {
                return true;
            } else {
                Toast.makeText(getActivity(), "" + AppConstants.RETYPE_NEW_PASSWORD, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getActivity(), "" + AppConstants.NEW_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
