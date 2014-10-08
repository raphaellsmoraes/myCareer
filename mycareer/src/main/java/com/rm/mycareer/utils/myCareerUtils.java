package com.rm.mycareer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rm.mycareer.myCareer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rapha_000 on 29/05/2014.
 */
public class myCareerUtils {

    public static final String ACCESS_EXPIRES = "access_expires";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PERSONALITY = "riasec_boolean";
    public static final String TITLE = "TITLE";
    public static final String WHATTHEYDO = "WHATTHEYDO";
    public static final String ONTHEJOB = "ONTHEJOB";
    public static final String SKILLS = "SKILLS";
    public static final String APP_ID = getStringResourceByName("app_id");
    public static final String SH_MYCAREER = "MyCareerKey";
    public static final String SH_MYCAREER_USER = "MyCareerUser";
    public static final String SH_MYCAREER_USERID = "MyCareerUserID";
    public static final String SH_MYCAREER_HOLLAND = "MyCareerUserHOLLAND";
    public static final String ONET_BASE_URL = "http://services.onetcenter.org/v1.3/ws/mnm/careers/";

    public static final boolean API_10 = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    public static final boolean API_11 = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    public static final boolean API_14 = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    public static final boolean API_19 = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static Drawable getDrawable(String name) {

        Context context = myCareer.getContext();

        int resourceId = context.getResources().getIdentifier(name, "drawable", myCareer.getContext().getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    public static String getStringResourceByName(String aString) {
        String packageName = myCareer.getContext().getPackageName();
        int resId = myCareer.getContext().getResources().getIdentifier(aString, "string", packageName);
        return myCareer.getContext().getString(resId);
    }

    public static List<Integer> getDrawableList() {
        Context context = myCareer.getContext();
        List<Integer> resourceList = new ArrayList<Integer>();

        for (int i = 1; i <= 48; i++) {
            resourceList.add(getResourseId("holland" + i, "drawable", myCareer.getContext().getPackageName()));
        }

        return resourceList;
    }

    public static int getResourseId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return myCareer.getContext().getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isUser() {
        SharedPreferences prfs = myCareer.getContext().getSharedPreferences(SH_MYCAREER, Context.MODE_PRIVATE);
        return prfs.getBoolean(SH_MYCAREER_USER, false);
    }

    public static String getUser() {
        SharedPreferences prfs = myCareer.getContext().getSharedPreferences(SH_MYCAREER, Context.MODE_PRIVATE);
        return prfs.getString(SH_MYCAREER_USERID, "");
    }

    public static void setUser(String id) {
        SharedPreferences preferences = myCareer.getContext().getSharedPreferences(SH_MYCAREER, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SH_MYCAREER_USERID, id);
        editor.apply();
    }

    public static void setHolland(boolean value) {
        SharedPreferences preferences = myCareer.getContext().getSharedPreferences(SH_MYCAREER, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SH_MYCAREER_HOLLAND, value);
        editor.apply();
    }

    public static boolean getHolland() {
        SharedPreferences prfs = myCareer.getContext().getSharedPreferences(SH_MYCAREER, Context.MODE_PRIVATE);
        return prfs.getBoolean(SH_MYCAREER_HOLLAND, false);
    }
}
