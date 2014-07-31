package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.utils.ONETXmlReader;
import com.rm.mycareer.utils.myCareerJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vntramo on 6/26/2014.
 */
public class TrendingActivity extends BaseActivity {
    private CardUI mCardView;
    private ONETXmlReader obj;
    private JSONArray mCareers;
    private JSONObject mCareerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.trending_cardui_layout);
        super.onCreate(savedInstanceState);

        // init CardView
        mCardView = (CardUI) findViewById(R.id.cardsview);
        mCardView.setSwipeable(false);

        /* Request a Profession */
        obj = new ONETXmlReader(myCareerJSONRequest.GET_KEYWORD_ONET_BASE_URL + "computer");
        obj.fetchXML();
        while (obj.parsingComplete) ;

        try {
            mCareers = obj.getJsonObj().getJSONObject("careers").getJSONArray("career");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mCareers.length(); i++) {

            try {
                String title = mCareers.getJSONObject(i).getString("title");
                String code = mCareers.getJSONObject(i).getString("code");


                /* Request a Profession */
                obj = new ONETXmlReader(myCareerJSONRequest.GET_CAREER_ONET_BASE_URL + code);
                obj.fetchXML();
                while (obj.parsingComplete) ;

                mCareerDetail = obj.getJsonObj().getJSONObject("career");
                String description = mCareerDetail.getString("what_they_do");


                mCardView.addCard(new CardPlay(title, description, "RED", "BLACK", false, true));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



            /* End request */


        // draw cards
        mCardView.refresh();
    }


    public void onClickTrending() {
    /* Setting up menu */
        RelativeLayout mTrending = (RelativeLayout) findViewById(R.id.rl_trending);
        mTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrendingActivity.class);
                startActivity(intent);
            }
        });
    }
}
