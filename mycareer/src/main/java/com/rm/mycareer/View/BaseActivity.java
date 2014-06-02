package com.rm.mycareer.view;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.widget.ProfilePictureView;
import com.rm.mycareer.R;

import java.util.ArrayList;

/**
 * Created by rapha_000 on 13/05/2014.
 */
public class BaseActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initilization

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout)findViewById(R.id.left_drawer);

        //set a custom shadow that overlays the main content when drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        final ProfilePictureView profile = (ProfilePictureView)findViewById(R.id.profile_pic);
        final TextView profile_user = (TextView)findViewById(R.id.profile_username);

        Bundle params = new Bundle();
        params.putString("fields", "id,name");
        params.putString("limit", "500");

               /* User request */
        new Request(
                Session.getActiveSession(),
                "/me",
                params,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                                /* handle the result */
                        GraphObject mUserInfo = response.getGraphObject();
                        try {
                            profile.setProfileId(mUserInfo.getProperty("id").toString());
                            profile_user.setText(mUserInfo.getProperty("name").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


    }
}

