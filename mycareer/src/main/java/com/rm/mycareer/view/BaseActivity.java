package com.rm.mycareer.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
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
import com.rm.mycareer.myCareer;
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

        onClickConfigure();
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

            final RenderScript rs = RenderScript.create(myCareer.getContext());
            final Allocation input = Allocation.createFromBitmap(rs, result, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(10.f /* e.g. 3.f */);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(result);

            bmImage.setImageBitmap(result);
        }
    }

    public void onClickConfigure() {
        onClickTrending();
        onClickSearch();
        onClickPersonalityTest();
        onClickRecommendation();
    }

    public void onClickTrending() {
    /* Setting up menu */
        RelativeLayout mTrending = (RelativeLayout) findViewById(R.id.rl_trending);
        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickPersonalityTest() {
    /* Setting up menu */
        RelativeLayout mTrending = (RelativeLayout) findViewById(R.id.rl_personality_retake);
        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalityView.class);
                startActivity(intent);
            }
        });
    }

    public void onClickSearch() {
    /* Setting up menu */
        RelativeLayout mTrending = (RelativeLayout) findViewById(R.id.rl_history);
        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickRecommendation() {
    /* Setting up menu */
        RelativeLayout mTrending = (RelativeLayout) findViewById(R.id.rl_recommendation);
        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

