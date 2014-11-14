package com.rm.mycareer.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rm.mycareer.R;
import com.rm.mycareer.model.Profession;
import com.rm.mycareer.model.User;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;

/**
 * Created by vntramo on 8/4/2014.
 */
public class ProfessionDetailsView extends BaseActivity {

    private ProgressBar mProgressBar;
    private String mStringTasks;
    private RatingBar ratingBar;
    private JSONArray mCareers;
    private JSONObject mCareerDetail;
    private LinearLayout ll_tasks;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.profession_layout);
        super.onCreate(savedInstanceState);

        /* Set back button on ActionBar */
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String type = bundle.getString(myCareerUtils.TYPE);
        ll_tasks = (LinearLayout) findViewById(R.id.ll_tasks);

        if (type.equals(myCareerUtils.KEYWORD)) {

            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            TextView tv_description = (TextView) findViewById(R.id.tv_description);
            TextView tv_onthejob_task1 = (TextView) findViewById(R.id.tv_onthejob_task1);
            TextView tv_onthejob_task2 = (TextView) findViewById(R.id.tv_onthejob_task2);
            TextView tv_onthejob_task3 = (TextView) findViewById(R.id.tv_onthejob_task3);
            mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);
            ratingBar = (RatingBar) findViewById(R.id.rating_bar);

            ArrayList<String> tasks = bundle.getStringArrayList(myCareerUtils.ONTHEJOB);
            tv_title.setText(bundle.getString(myCareerUtils.TITLE));
            tv_description.setText(bundle.getString(myCareerUtils.WHATTHEYDO));

            if (!tasks.isEmpty()) {
                tv_onthejob_task1.setText(tasks.get(0).toString());
                tv_onthejob_task2.setText(tasks.get(1).toString());
                tv_onthejob_task3.setText(tasks.get(2).toString());
            }

            code = bundle.getString(myCareerUtils.CODE);
            //Log.d("code", code);

            User user = myCareerUtils.getUser();
            ArrayList<Profession> professions = user.getProfessions();
            for (Profession profession : professions) {
                //Log.d("code", "code: " + profession.getOccupation().getOnet_soc() + "/ rating: " + profession.getRating());
                if (code.equals(profession.getOccupation().getOnet_soc())) {
                    Log.d("codeFinal", "rating: " + profession.getRating());
                    if (profession.getRating() != -1.0) {
                        ratingBar.setRating((float) profession.getRating());
                    }
                }
            }


            addListenerOnRatingBar();
            mProgressBar.setVisibility(View.GONE);

        } else if (type.equals(myCareerUtils.HOLLAND)) {

            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            TextView tv_description = (TextView) findViewById(R.id.tv_description);
            mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);
            ratingBar = (RatingBar) findViewById(R.id.rating_bar);

            tv_title.setText(bundle.getString(myCareerUtils.TITLE));

            code = bundle.getString(myCareerUtils.CODE);

            User user = myCareerUtils.getUser();
            ArrayList<Profession> professions = user.getProfessions();
            for (Profession profession : professions) {
                Log.d("code", "code: " + profession.getOccupation().getOnet_soc() + "/ desc: " + profession.getOccupation().getDescription());
                if (code.equals(profession.getOccupation().getOnet_soc())) {
                    Log.d("codeFinal", "rating: " + profession.getRating());
                    tv_description.setText(profession.getOccupation().getDescription());

                    if (profession.getRating() != -1.0) {
                        ratingBar.setRating((float) profession.getRating());
                    }
                }
            }


            addListenerOnRatingBar();
            mProgressBar.setVisibility(View.GONE);


        } else if (type.equals(myCareerUtils.RECOMMEND)) {

            code = bundle.getString(myCareerUtils.CODE);

            /* Request a Profession*/
            myCareerJSONObject requestObject =
                    new myCareerJSONObject(myCareerJSONRequest.RequestType.ONET, myCareerJSONRequest.GET_ONET_OCCUPATION_BASE_URL
                            + code + myCareerJSONRequest.GET_ONET_OCCUPATION_TASKS, null, 0);

            myCareerJSONRequest request = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                @Override
                public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {

                    if (statusCode == 200) {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = XML.toJSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mCareerDetail = jsonObj.getJSONObject("tasks");

                        TextView tv_onthejob_task1 = (TextView) findViewById(R.id.tv_onthejob_task1);
                        TextView tv_onthejob_task2 = (TextView) findViewById(R.id.tv_onthejob_task2);
                        TextView tv_onthejob_task3 = (TextView) findViewById(R.id.tv_onthejob_task3);

                        tv_onthejob_task1.setVisibility(View.GONE);
                        tv_onthejob_task2.setVisibility(View.GONE);
                        tv_onthejob_task3.setVisibility(View.GONE);

                        TextView tv_tasks = new TextView(myCareer.getContext());
                        tv_tasks.setTextColor(Color.BLACK);
                        tv_tasks.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        tv_tasks.setText("Tasks:");
                        ll_tasks.addView(tv_tasks);

                        for (int i = 0; i < mCareerDetail.getJSONArray("task").length(); i++) {
                            //tasks.add(mCareerDetail.getJSONObject("on_the_job").getJSONArray("task").get(i).toString());
                            Log.d("tasks", mCareerDetail.getJSONArray("task").get(i).toString());
                            TextView tv = new TextView(myCareer.getContext());
                            tv.setTextColor(Color.BLACK);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            tv.setText(mCareerDetail.getJSONArray("task").getJSONObject(i).getString("content"));
                            ll_tasks.addView(tv);
                        }
                    } else {
                        TextView tv_onthejob_task1 = (TextView) findViewById(R.id.tv_onthejob_task1);
                        TextView tv_onthejob_task2 = (TextView) findViewById(R.id.tv_onthejob_task2);
                        TextView tv_onthejob_task3 = (TextView) findViewById(R.id.tv_onthejob_task3);

                        tv_onthejob_task1.setVisibility(View.INVISIBLE);
                        tv_onthejob_task2.setVisibility(View.INVISIBLE);
                        tv_onthejob_task3.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCommandFinished(boolean result) {

                }
            });

            request.start();


            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            TextView tv_description = (TextView) findViewById(R.id.tv_description);
            TextView tv_onthejob_task1 = (TextView) findViewById(R.id.tv_onthejob_task1);
            TextView tv_onthejob_task2 = (TextView) findViewById(R.id.tv_onthejob_task2);
            TextView tv_onthejob_task3 = (TextView) findViewById(R.id.tv_onthejob_task3);
            mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);
            ratingBar = (RatingBar) findViewById(R.id.rating_bar);

            tv_title.setText(bundle.getString(myCareerUtils.TITLE));
            tv_description.setText(bundle.getString(myCareerUtils.WHATTHEYDO));

            User user = myCareerUtils.getUser();
            ArrayList<Profession> professions = user.getProfessions();
            for (Profession profession : professions) {
                //Log.d("code", "code: " + profession.getOccupation().getOnet_soc() + "/ rating: " + profession.getRating());
                if (code.equals(profession.getOccupation().getOnet_soc())) {
                    Log.d("codeFinal", "rating: " + profession.getRating());
                    if (profession.getRating() != -1.0) {
                        ratingBar.setRating((float) profession.getRating());
                    }
                }
            }


            addListenerOnRatingBar();
            mProgressBar.setVisibility(View.GONE);

        }
    }


    public void addListenerOnRatingBar() {

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, final float rating,
                                        boolean fromUser) {

                Log.d("teste", "rating: " + String.valueOf(rating) + "-" + code);

                User user = myCareerUtils.getUser();
                Gson gson = new Gson();
                String json = gson.toJson(user);

                myCareerJSONObject requestObject =
                        new myCareerJSONObject(myCareerJSONRequest.RequestType.GET, myCareerJSONRequest.MYCAREER_BASE_URL
                                + myCareerJSONRequest.MYCAREER_UPDATE_RATING + user.getId() +
                                "&occupationId=" + code + "&rating=" + rating, json, 0);

                myCareerJSONRequest updateRating = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                    @Override
                    public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                        if (statusCode == 200) {
                            Gson gson1 = new Gson();
                            User user1 = gson1.fromJson(result, User.class);
                            myCareerUtils.setUser(user1);
                            Toast.makeText(getApplicationContext(), "Rated : " + rating, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCommandFinished(boolean result) {

                    }
                });

                updateRating.start();

            }
        });
    }
}
