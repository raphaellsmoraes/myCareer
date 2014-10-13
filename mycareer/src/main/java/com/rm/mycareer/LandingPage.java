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
import com.google.gson.Gson;
import com.rm.mycareer.model.FavoriteAthletes;
import com.rm.mycareer.model.FavoriteBooks;
import com.rm.mycareer.model.FavoriteMovies;
import com.rm.mycareer.model.FavoriteMusics;
import com.rm.mycareer.model.Location;
import com.rm.mycareer.model.User;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
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

                                    /* Name */
                                    String name = user.getString("name");

                                        /* Birthday */
                                    String birthday = user.getString("birthday");

                                        /* Location */
                                    String location = user.getString("location");
                                    JSONObject userLocation = new JSONObject(location);
                                    String locationId = userLocation.getString("id");
                                    String locationName = userLocation.getString("name");

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

                                    String booksPagingString = booksObject.getString("paging");
                                    JSONObject booksPagingObject = new JSONObject(booksPagingString);

                                    /* Check if there's pagination
                                    if (booksPagingObject.has("next")) {
                                        Log.d("teste", booksPagingObject.getString("next"));

                                    ArrayList<FavoriteBooks> arrayAux = getRequestedPaginationBooks(booksPagingObject.getString("next"), booksArrayList);
                                    booksArrayList.addAll(arrayAux);

                                    Log.d("teste", "Tamanho:" + arrayAux.size());

                                    }else{

                                    }*/

                                    Log.d("teste", booksArray.toString());
                                    /* End books */

                                    /* Music */
                                    String music = user.getString("music");
                                    ArrayList<FavoriteMusics> musicArrayList = new ArrayList<FavoriteMusics>();
                                    JSONObject musicObject = new JSONObject(music);
                                    JSONArray musicArray = musicObject.getJSONArray("data");

                                    for (int i = 0; i < musicArray.length(); i++) {
                                        JSONObject object = musicArray.getJSONObject(i);
                                        String musicId = object.getString("id");
                                        String musicName = object.getString("name");
                                        musicArrayList.add(new FavoriteMusics(musicId, musicName));
                                    }

                                    String musicPagingString = musicObject.getString("paging");
                                    JSONObject musicPagingObject = new JSONObject(musicPagingString);

                                    /* Check if there's pagination
                                    if (booksPagingObject.has("next")) {
                                        Log.d("teste", booksPagingObject.getString("next"));

                                    ArrayList<FavoriteBooks> arrayAux = getRequestedPaginationBooks(booksPagingObject.getString("next"), booksArrayList);
                                    booksArrayList.addAll(arrayAux);

                                    Log.d("teste", "Tamanho:" + arrayAux.size());

                                    }else{

                                    }*/

                                    Log.d("teste", musicArray.toString());
                                    /* End music */

                                    /* movies */
                                    String movies = user.getString("movies");
                                    ArrayList<FavoriteMovies> moviesArrayList = new ArrayList<FavoriteMovies>();
                                    JSONObject moviesObject = new JSONObject(movies);
                                    JSONArray moviesArray = moviesObject.getJSONArray("data");

                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject object = moviesArray.getJSONObject(i);
                                        String moviesId = object.getString("id");
                                        String moviesName = object.getString("name");
                                        moviesArrayList.add(new FavoriteMovies(moviesId, moviesName));
                                    }

                                    String moviesPagingString = moviesObject.getString("paging");
                                    JSONObject moviesPagingObject = new JSONObject(moviesPagingString);

                                    /* Check if there's pagination
                                    if (booksPagingObject.has("next")) {
                                        Log.d("teste", booksPagingObject.getString("next"));

                                    ArrayList<FavoriteBooks> arrayAux = getRequestedPaginationBooks(booksPagingObject.getString("next"), booksArrayList);
                                    booksArrayList.addAll(arrayAux);

                                    Log.d("teste", "Tamanho:" + arrayAux.size());

                                    }else{

                                    }*/

                                    Log.d("teste", moviesArray.toString());
                                    /* End movies */

                                    /* Atlhetes */

                                    ArrayList<FavoriteAthletes> atlhetesArrayList = new ArrayList<FavoriteAthletes>();
                                    JSONArray atlhetesArray = user.getJSONArray("favorite_athletes");


                                    for (int i = 0; i < atlhetesArray.length(); i++) {
                                        JSONObject object = atlhetesArray.getJSONObject(i);
                                        String atlhetesId = object.getString("id");
                                        String atlhetesName = object.getString("name");
                                        atlhetesArrayList.add(new FavoriteAthletes(atlhetesId, atlhetesName));
                                    }

                                    //String atlhetesPagingString = atlhetesObject.getString("paging");
                                    //JSONObject atlhetesPagingObject = new JSONObject(booksPagingString);

                                    /* Check if there's pagination
                                    if (booksPagingObject.has("next")) {
                                        Log.d("teste", booksPagingObject.getString("next"));

                                    ArrayList<FavoriteBooks> arrayAux = getRequestedPaginationBooks(booksPagingObject.getString("next"), booksArrayList);
                                    booksArrayList.addAll(arrayAux);

                                    Log.d("teste", "Tamanho:" + arrayAux.size());

                                    }else{
                                    }*/

                                    Log.d("teste", atlhetesArray.toString());
                                    /* End atlhetes */


                                    User newUser = new User(name, id, birthday, booksArrayList, moviesArrayList, musicArrayList, atlhetesArrayList, new Location(locationId, locationName), gender);
                                    Log.d("teste", newUser.toString());

                                    Gson gson = new Gson();
                                    String json = gson.toJson(newUser);

                                     /* add User*/
                                    myCareerJSONObject requestObject =
                                            new myCareerJSONObject(myCareerJSONRequest.RequestType.POST, myCareerJSONRequest.MYCAREER_BASE_URL + myCareerJSONRequest.MYCAREER_ADD_USER, json, 0);

                                    myCareerJSONRequest addUser = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                                        @Override
                                        public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                                            if (statusCode == 200) {
                                                Log.d("teste", result);
                                            }
                                        }

                                        @Override
                                        public void onCommandFinished(boolean result) {

                                        }
                                    });

                                    addUser.start();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
            }
        } else

        {
            Log.d("Session", "tá fechada =/");
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
                }

            } else {
                Log.d("Session", "tá fechada =/");
            }
        } catch (Exception e) {

        }
    }

    private ArrayList<FavoriteBooks> getRequestedPaginationBooks(String nextUrl, final ArrayList<FavoriteBooks> books) {

        final ArrayList<FavoriteBooks> booksAux = new ArrayList<FavoriteBooks>();
        booksAux.addAll(books);

         /* Request a Profession*/
        myCareerJSONObject requestObject =
                new myCareerJSONObject(myCareerJSONRequest.RequestType.GET, nextUrl, null, 0);

        myCareerJSONRequest reqNext = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
            @Override
            public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                JSONObject booksPagingObject = new JSONObject(result);
                JSONArray booksArray = booksPagingObject.getJSONArray("data");

                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject object = booksArray.getJSONObject(i);
                    String booksId = object.getString("id");
                    String booksName = object.getString("name");
                    booksAux.add(new FavoriteBooks(booksId, booksName));
                }

                if (booksPagingObject.has("next")) {
                    Log.d("teste", booksPagingObject.getString("next"));
                                            /* Request Next */
                    ArrayList<FavoriteBooks> booksRet = getRequestedPaginationBooks(booksPagingObject.getString("next"), booksAux);
                    booksAux.addAll(booksRet);
                } else {
                   /* return */
                }
            }

            @Override
            public void onCommandFinished(boolean result) {

            }
        });

        reqNext.start();

        while (reqNext.isRunning()) {
        }
        ;

        return booksAux;
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