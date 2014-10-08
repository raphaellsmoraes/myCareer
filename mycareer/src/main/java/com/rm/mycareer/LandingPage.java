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

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.android.Facebook;
import com.facebook.model.GraphObject;
import com.rm.mycareer.model.FavoriteBooks;
import com.rm.mycareer.utils.myCareerUtils;
import com.rm.mycareer.view.PersonalityView;
import com.rm.mycareer.view.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

        try {
            if (Session.getActiveSession().isOpened()) {

                /* Check if user exists */
                if (myCareerUtils.isUser()) {
                    /* Check if user completed holland test */
                    if (myCareerUtils.getHolland()) {
                        /* Goes to Search page */
                        Intent intent = new Intent(this, SearchActivity.class);
                        startActivity(intent);
                    } else {
                        /* Start holland test */
                        Intent intent = new Intent(this, PersonalityView.class);
                        startActivity(intent);
                    }
                } else {
                    /* Request user by fb id if user exists goes to search
                    * else add new user */


                    Bundle params = new Bundle();
                    params.putString("fields", "id,name,birthday,location,books{id,name},movies{id,name},music{id,name},favorite_athletes");
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

                                        Log.d("teste", mUserInfo.getInnerJSONObject().getJSONArray("data").toString());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).executeAsync();
                }
            } else {
                Log.d("Session", "tá fechada =/");
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (Session.getActiveSession().isOpened()) {

                /* Check if user exists */
                if (myCareerUtils.isUser()) {
                    /* Check if user completed holland test */
                    if (myCareerUtils.getHolland()) {
                        /* Goes to Search page */
                        Intent intent = new Intent(this, SearchActivity.class);
                        startActivity(intent);
                    } else {
                        /* Start holland test */
                        Intent intent = new Intent(this, PersonalityView.class);
                        startActivity(intent);
                    }

                } else {
                    /* Request user by fb id if user exists goes to search
                    * else add new user */


                    Bundle params = new Bundle();
                    params.putString("fields", "id,name,birthday,location,books{id,name},movies{id,name},music{id,name},favorite_athletes,gender");
                    params.putString("limit", "50000");

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

                                        /* First time log-in, retrieve from facebook all information */

                                        JSONObject user = mUserInfo.getInnerJSONObject();

                                        /* Id */
                                        String id = user.getString("id");

                                        /* Birthday */
                                        String birthday = user.getString("birthday");

                                        /* Location */
                                        String location = user.getString("location");
                                        JSONObject userLocation = new JSONObject(location);

                                        /* Gender */
                                        String gender = user.getString("gender");

                                        /* Books */
                                        String books = user.getString("books");
                                        ArrayList<FavoriteBooks> booksArrayList = new ArrayList<FavoriteBooks>();
                                        JSONObject booksObject = new JSONObject(books);
                                        JSONArray booksArray = booksObject.getJSONArray("data");

                                        for (int i = 0; i < booksArray.length(); i++) {
                                            JSONObject object = booksArray.getJSONObject(i);
                                            String booksId = object.getString("id");
                                            String booksName = object.getString("name");
                                            booksArrayList.add(new FavoriteBooks(booksId, booksName));
                                        }

                                        Log.d("teste", booksArrayList.get(0).getId());

                                        String booksPagingString = booksObject.getString("paging");
                                        JSONObject booksPagingObject = new JSONObject(booksPagingString);

                                        /* Check if there's pagination */
                                        if (booksPagingObject.has("next")) {
                                            Log.d("teste", booksPagingObject.getString("next"));

                                            /* Request Next */
                                            getRequestedPaginationBooks(booksPagingObject.getString("next"));

                                        } else {
                                            Log.d("teste", booksArray.toString());
                                        }
                                        /* End books */

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).executeAsync();
                }

            } else {
                Log.d("Session", "tá fechada =/");
            }
        } catch (Exception e) {

        }
    }

    private void getRequestedPaginationBooks(String nextUrl) {

        new Request(
                Session.getActiveSession(),
                nextUrl,
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {

                         /* Books */
                        JSONArray object = null;

                        try {

                            object = new JSONArray(response.getRawResponse());
                            Log.d("OBJECT", object.toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                         /* End books */

                    }
                }
        ).executeAsync();
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