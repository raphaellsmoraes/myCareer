package com.rm.mycareer.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.model.User;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONException;

/**
 * Created by vntramo on 10/17/2014.
 */
public class RecommendationActivity extends BaseActivity {

    private CardUI mCardView;
    private ProgressBar mProgressBar;
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

        /* Request a Profession */
        myCareerJSONObject requestObject =
                new myCareerJSONObject(myCareerJSONRequest.RequestType.GET, myCareerJSONRequest.MYCAREER_BASE_URL
                        + myCareerJSONRequest.MYCAREER_USER_RECOMMENDATION + user.getId(), null, 0);

        myCareerJSONRequest req = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
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

        req.start();
    }

}
