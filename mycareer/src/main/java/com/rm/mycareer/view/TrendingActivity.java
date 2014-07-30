package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.utils.ONETXmlReader;
import com.rm.mycareer.utils.myCareerJSONRequest;

/**
 * Created by vntramo on 6/26/2014.
 */
public class TrendingActivity extends BaseActivity {
    private CardUI mCardView;
    private ONETXmlReader obj;

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
        obj = new ONETXmlReader(myCareerJSONRequest.GET_CAREER_ONET_BASE_URL + "17-2051.00");
        obj.fetchXML();
        while (obj.parsingComplete) ;
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
