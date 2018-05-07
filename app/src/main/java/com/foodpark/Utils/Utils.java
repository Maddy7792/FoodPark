package com.foodpark.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.foodpark.R;

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

}
