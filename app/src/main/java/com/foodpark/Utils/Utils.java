package com.foodpark.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.foodpark.R;

import java.lang.reflect.Field;

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


}
