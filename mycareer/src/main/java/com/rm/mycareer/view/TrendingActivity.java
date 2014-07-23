package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

/**
 * Created by vntramo on 6/26/2014.
 */
public class TrendingActivity extends BaseActivity{
    private CardUI mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.trending_cardui_layout);
        super.onCreate(savedInstanceState);

        // init CardView
        mCardView = (CardUI) findViewById(R.id.cardsview);
        mCardView.setSwipeable(false);

        // add one card
        mCardView.addCard(new CardImage("Computer Science", R.drawable.small_computer_science));
        mCardView.addCard(new TrendingCard("Computer Science"));
        mCardView.addCard(new CardPlay("Computer Science", "Teste legal", "RED", "BLACK", false, true));


        /* Request a Profession */
        myCareerJSONObject mObject = new myCareerJSONObject(myCareerJSONRequest.RequestType.ONET,
                myCareerJSONRequest.GET_CAREER_ONET_BASE_URL + "17-2051.00", null, 0);

        mRequest = new myCareerJSONRequest(mObject, new AsyncTaskCompleteListener() {
            @Override
            public void onTaskComplete(String result, int statusCode, int requestCode) {
                if (statusCode == 200) {
                    Log.d("TESTE", "OK: " + result);
                }else{
                    Log.d("TESTE", "NOT OK: " + statusCode);
                }
            }

            @Override
            public void onCommandFinished(boolean result) {

            }
        });

        mRequest.start();
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
