package com.rm.mycareer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.facebook.Session;
import com.facebook.android.Facebook;
import com.rm.mycareer.view.PersonalityView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class LandingPage extends FragmentActivity {

    private Facebook facebook;
    private SharedPreferences prefs;
    private Button loginButton;

    private static final List<String> PERMISSIONS = Arrays.asList("user_location", "user_birthday", "user_likes", "user_interests", "friends_interests");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

        if (Session.getActiveSession().isOpened()) {
            Intent intent = new Intent(this, PersonalityView.class);
            startActivity(intent);
        } else {
            Log.d("Session", "tá fechada =/");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Session.getActiveSession().isOpened()) {
            Intent intent = new Intent(this, PersonalityView.class);
            startActivity(intent);
        } else {
            Log.d("Session", "tá fechada =/");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Session.getActiveSession().isOpened())
            Session.getActiveSession().close();
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }

    public void getFacebookKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.rm.mycareer",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}