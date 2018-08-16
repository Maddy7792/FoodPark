package com.foodpark.Utils;


import android.util.Log;

;import com.foodpark.application.App;

/**
 * Created by dennis on 14/5/18.
 */

public class Logger {
    public static void d(String tag, String msg) {
        if (App.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    //Warning Logs
    public static void w(String tag, String msg) {
        if (App.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    //Verbose Logs
    public static void v(String tag, String msg) {
        if (App.IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

    //Error Logs
    public static void e(String tag, String msg, Throwable t) {
        if (App.IS_DEBUG) {
            Log.e(tag, msg, t);
        }
    }
}
