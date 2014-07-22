package com.rm.mycareer.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.widget.ProfilePictureView;
import com.rm.mycareer.R;
import com.rm.mycareer.utils.myCareerJSONRequest;

import java.io.InputStream;

/**
 * Created by rapha_000 on 13/05/2014.
 */
public class BaseActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    myCareerJSONRequest mRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initilization

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);

        //set a custom shadow that overlays the main content when drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        final ProfilePictureView profile = (ProfilePictureView) findViewById(R.id.profile_pic);
        final TextView profile_user = (TextView) findViewById(R.id.profile_username);
        final RelativeLayout topLayout = (RelativeLayout) findViewById(R.id.top_profile_rl);

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
                            profile.setDrawingCacheEnabled(true);
                            profile.setProfileId(mUserInfo.getProperty("id").toString());
                            profile_user.setText(mUserInfo.getProperty("name").toString());

                           /* myCareerJSONObject mObject = new myCareerJSONObject(myCareerJSONRequest.RequestType.GET,
                                    String.format(myCareerJSONRequest.GET_FB_PICTURE, profile.getProfileId()), null, 0);

                            mRequest = new myCareerJSONRequest(mObject, new AsyncTaskCompleteListener() {
                                @Override
                                public void onTaskComplete(String result, int statusCode, int requestCode) {
                                    if (statusCode == 200) {

                                    }
                                }

                                @Override
                                public void onCommandFinished(boolean result) {

                                }
                            });

                            mRequest.start();
                            */
                            new DownloadImageTask((ImageView) findViewById(R.id.top_profile_iv))
                                    .execute(String.format(myCareerJSONRequest.GET_FB_PICTURE, profile.getProfileId()));

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
        ) {
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

