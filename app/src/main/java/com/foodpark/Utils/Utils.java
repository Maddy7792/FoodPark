package com.foodpark.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.foodpark.R;
import com.foodpark.activities.NavigationActivity;
import com.foodpark.auth.SignInActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.security.SecureRandom;

/**
 * Created by dennis on 7/5/18.
 */

public class Utils {

    public static int deviceDimensions(Context context, int percent, String dimension) {
        String TAG = "deviceDimensions";
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double onePercentWidth;
        double requiredWidth;
        Log.i(TAG, "device_width " + width);
        Log.i(TAG, "device_height" + height);
        Log.i(TAG, "required_height" + (height / 100) * percent);

        if (dimension.equalsIgnoreCase("width")) {
            onePercentWidth = width / 100.00;
            requiredWidth = onePercentWidth * (double) percent;
            Log.i(TAG, "requiredWidth double" + requiredWidth
                    + ", requiredWidth int" + (int) Math.ceil(requiredWidth));
            return (int) Math.ceil(requiredWidth);
        } else if (dimension.equalsIgnoreCase("height")) {
            return (height / 100) * percent;
        } else {
            return -1;
        }
    }

    public static void setStatuscolor(Activity activity) {
        try {
            Window window = activity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }


    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }


    public static ProgressDialog showProgressDialog(Activity activity) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(AppConstants.PROGRESS_TEXT);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static int dpToPx(int dp, Activity activity) {
        Resources r = activity.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static void setNormalIntent(Activity source, Class<?> destination) {
        Intent intent = new Intent(source, destination);
        source.startActivity(intent);
    }

    public static void setDataIntent(Activity source, Class<?> destination, String type) {
        Intent intent = new Intent(source, destination);
        intent.putExtra(AppConstants.KEY_TYPE, type);
        source.startActivity(intent);
    }


    public static void hideKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /*
    Signout method
    * */
    public static void signOut(Activity source, Class<?> destination) {
        Intent intent = new Intent(source, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        source.startActivity(intent);
    }


    //store phone number for checking forgetpassword
    public static void storePhoneNumber(Context context, String phoneNumber) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.KEY_APP_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.KEY_SHARED_PHONE, phoneNumber);
        editor.apply();
    }

    /*
    get the phoneNumber from sharedPreferences.
     */
    public static String getPhoneNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.KEY_APP_NAME, 0);
        return preferences.getString(AppConstants.KEY_SHARED_PHONE, "");
    }


    //store otp number
    public static void storeOtpNumber(Context context, int otpNumber) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.KEY_APP_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AppConstants.KEY_OTP_NUMBER, otpNumber);
        editor.apply();
    }

    /*
    get the otpNumber.
     */
    public static int getOtpNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.KEY_APP_NAME, 0);
        return preferences.getInt(AppConstants.KEY_OTP_NUMBER, 0);
    }


    /*
    sending otp number to mobile users
    */
    public static void sendOTPToUsers(Context context, String phoneNumber, int otp) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, String.valueOf(otp), null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    /*
    generate 4 digit Random numbers for OTP
    * */

    public static int generateRandomNumber() {
        int randomNumber;

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < AppConstants.length; i++) {
            int number = secureRandom.nextInt(AppConstants.range);
            if (number == 0 && i == 0) {
                i = -1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);

        return randomNumber;
    }

    public ColorStateList setBottomTintColor(Context context){
        int colorInt = context.getResources().getColor(R.color.colorPrimary);
        ColorStateList csl = ColorStateList.valueOf(colorInt);

        return csl;
    }


    public static void dataIntent(Activity source, Class<?> destination,String key, String data) {
        Intent intent = new Intent(source, destination);
        intent.putExtra(key, data);
        source.startActivity(intent);
    }

    public static void runLayoutAnimation(RecyclerView recyclerView, int animationLayout){
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, animationLayout);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
