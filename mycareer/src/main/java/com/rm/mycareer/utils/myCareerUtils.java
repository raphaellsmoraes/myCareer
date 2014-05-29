package com.rm.mycareer.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.rm.mycareer.myCareer;
import com.rm.mycareer.view.BaseActivity;

/**
 * Created by rapha_000 on 29/05/2014.
 */
public class myCareerUtils {

    public static Drawable getDrawable(String name) {

        Context context = myCareer.getContext();

        int resourceId = context.getResources().getIdentifier(name, "drawable", myCareer.getContext().getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

}
