package com.foodpark.App;

import android.app.Application;

/**
 * Created by dennis on 10/5/18.
 */

public class SaveData extends Application {

    private static SaveData mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized SaveData getInstance(){
        return mInstance;
    }
}
