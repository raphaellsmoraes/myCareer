package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rm.mycareer.R;
import com.rm.mycareer.model.User;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by vntramo on 8/4/2014.
 */
public class ProfessionDetailsView extends BaseActivity {

    private ProgressBar mProgressBar;
    private String mStringTasks;
    private RatingBar ratingBar;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.profession_layout);
        super.onCreate(savedInstanceState);

        /* Set back button on ActionBar */
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_description = (TextView) findViewById(R.id.tv_description);
        TextView tv_onthejob_task1 = (TextView) findViewById(R.id.tv_onthejob_task1);
        TextView tv_onthejob_task2 = (TextView) findViewById(R.id.tv_onthejob_task2);
        TextView tv_onthejob_task3 = (TextView) findViewById(R.id.tv_onthejob_task3);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);

        ArrayList<String> tasks = bundle.getStringArrayList(myCareerUtils.ONTHEJOB);
        tv_title.setText(bundle.getString(myCareerUtils.TITLE));
        tv_description.setText(bundle.getString(myCareerUtils.WHATTHEYDO));

        if (!tasks.isEmpty()) {
            tv_onthejob_task1.setText(tasks.get(0).toString());
            tv_onthejob_task2.setText(tasks.get(1).toString());
            tv_onthejob_task3.setText(tasks.get(2).toString());
        }

        code = bundle.getString(myCareerUtils.CODE);

        addListenerOnRatingBar();
        mProgressBar.setVisibility(View.GONE);
    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
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
                            Log.d("teste", user1.getProfessions().toString());
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
