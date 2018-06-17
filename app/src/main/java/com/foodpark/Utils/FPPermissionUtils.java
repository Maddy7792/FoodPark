package com.foodpark.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by dennis on 5/6/17.
 */

public class FPPermissionUtils extends Activity {

    private static final int RequestPermissionCode = 1;


    public static void EnableRuntimePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, RequestPermissionCode);
    }

    public static void EnableSmsPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{
                Manifest.permission.SEND_SMS
        },RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int RequestCode, String permissions[]
            , int[] PermisionResult) {
        switch (RequestCode) {
            case RequestPermissionCode:
                if (PermisionResult.length > 0 && PermisionResult[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted," +
                            " Now your application can access Storage.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Canceled, " +
                            "Now your application cannot access Storage.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public static boolean checkPermissionStatus(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }

    }

    public static boolean checkSmsPermissionStatus(Activity activity){
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }

}
