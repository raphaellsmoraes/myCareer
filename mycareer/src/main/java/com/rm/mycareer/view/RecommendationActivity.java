package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fima.cardsui.views.CardUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rm.mycareer.R;
import com.rm.mycareer.model.Prediction;
import com.rm.mycareer.model.User;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vntramo on 10/17/2014.
 */
public class RecommendationActivity extends BaseActivity {

    private CardUI mCardView;
    private ProgressBar mProgressBar;
    private JSONArray mCareers;
    private JSONArray mCareersDetail;
    private JSONObject mCareerDetail;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.trending_cardui_layout);
        super.onCreate(savedInstanceState);


        // init CardView
        mCardView = (CardUI) findViewById(R.id.cardsview);
        mCardView.setSwipeable(false);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);
        mTextView = (TextView) findViewById(R.id.tv_search_description);
        mTextView.setText("Retrieving recommendations");

        User user = myCareerUtils.getUser();

        /* Check if user already rated something */
        if (user.hasRated()) {

            /* Request myCareer Recommendations */
            myCareerJSONObject requestObject =
                    new myCareerJSONObject(myCareerJSONRequest.RequestType.GET, myCareerJSONRequest.MYCAREER_BASE_URL
                            + myCareerJSONRequest.MYCAREER_USER_RECOMMENDATION + user.getId(), null, 0);

            myCareerJSONRequest req = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                @Override
                public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                    if (statusCode == 200) {
                        Gson gson = new Gson();
                        ArrayList<Prediction> predictions = gson.fromJson(result, new TypeToken<ArrayList<Prediction>>() {
                        }.getType());

                        Collections.reverse(predictions);

                        for (final Prediction prediction : predictions) {

                            CardPlay newCard = new CardPlay(prediction.getOccupation().getTitle(), prediction.getOccupation().getDescription(), "RED", "BLACK", false, true);
                            newCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(RecommendationActivity.this, ProfessionDetailsView.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(myCareerUtils.TYPE, myCareerUtils.RECOMMEND);
                                    bundle.putString(myCareerUtils.CODE, prediction.getOccupation().getOnet_soc());
                                    bundle.putString(myCareerUtils.TITLE, prediction.getOccupation().getTitle());
                                    bundle.putString(myCareerUtils.WHATTHEYDO, prediction.getOccupation().getDescription());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                            mCardView.addCard(newCard);
                        }


                        // draw cards
                        //Log.d("card", "Size:" + mCardView.getChildCount());
                        mProgressBar.setVisibility(View.GONE);
                        mCardView.refresh();

                    }
                }

                @Override
                public void onCommandFinished(boolean result) {

                }
            });

            req.start();
        }
        /* Request ONET with personality Profile */
        else {
            Log.d("TESTE", "Requesting ONET -- Recommendation Activity");

            /* Request myCareer Recommendations */
            myCareerJSONObject requestObject =
                    new myCareerJSONObject(myCareerJSONRequest.RequestType.ONET_INTEREST, myCareerJSONRequest.GET_INTEREST_PROFILER_BASE_URL
                            + myCareerJSONRequest.GET_REALISTIC + user.getPersonality().getRealistic() + "&"
                            + myCareerJSONRequest.GET_INVESTIGATIVE + user.getPersonality().getInvestigative() + "&"
                            + myCareerJSONRequest.GET_ARTISTIC + user.getPersonality().getArtistic() + "&"
                            + myCareerJSONRequest.GET_SOCIAL + user.getPersonality().getSocial() + "&"
                            + myCareerJSONRequest.GET_ENTERPRISING + user.getPersonality().getEnterprising() + "&"
                            + myCareerJSONRequest.GET_CONVETIONAL + user.getPersonality().getConventional()
                            , null, 0);

            myCareerJSONRequest req = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                @Override
                public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                    if (statusCode == 200) {


                        JSONObject jsonObj = XML.toJSONObject(result);
                        Log.d("teste", jsonObj.toString());

                        /* Loop though jsonArray and add professions */
                        Log.d("teste", "size" + jsonObj.getJSONObject("careers").getJSONArray("career").length());

                        for (int i = 0; i < jsonObj.getJSONObject("careers").getJSONArray("career").length(); i++) {

                            JSONObject mHolland = jsonObj.getJSONObject("careers").getJSONArray("career").getJSONObject(i);
                            final String title = mHolland.getString("title");
                            final String code = mHolland.getString("code");

                            final CardPlay newCard = new CardPlay(title, "", "RED", "BLACK", false, true);
                            newCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(RecommendationActivity.this, ProfessionDetailsView.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(myCareerUtils.TYPE, myCareerUtils.HOLLAND);
                                    bundle.putString(myCareerUtils.CODE, code);
                                    bundle.putString(myCareerUtils.TITLE, title);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                            mCardView.addCard(newCard);
                        }


                        // draw cards
                        //Log.d("card", "Size:" + mCardView.getChildCount());
                        mProgressBar.setVisibility(View.GONE);
                        mCardView.refresh();
                    }
                }

                @Override
                public void onCommandFinished(boolean result) {

                }
            }

            );

            req.start();
        }


    }


}
