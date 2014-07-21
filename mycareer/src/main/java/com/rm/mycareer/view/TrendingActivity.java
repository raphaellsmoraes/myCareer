package com.rm.mycareer.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.model.myCareerWebService;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONArray;

/**
 * Created by vntramo on 6/26/2014.
 */
public class TrendingActivity extends BaseActivity implements AsyncTaskCompleteListener {
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

    @Override
    public void onTaskComplete(String result, int statusCode, int requestCode) {

        myCareerWebService webService = new myCareerWebService(myCareerUtils.ONET_BASE_URL + "17-2051.00");
        String resultado = webService.getEstados();
        Log.d("TESTE", resultado);
    }

    @Override
    public void onCommandFinished(boolean result) {

    }
}
