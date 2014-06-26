package com.rm.mycareer.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

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
    public static final String APP_ID = getStringResourceByName("app_id");

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
}
