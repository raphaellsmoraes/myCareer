package com.rm.mycareer;

import android.app.Application;
import android.content.Context;

/**
 * Created by rapha_000 on 29/05/2014.
 */
public class myCareer extends Application {

    public static Context context;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
