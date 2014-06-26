package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rm.mycareer.R;

/**
 * Created by vntramo on 6/26/2014.
 */
public class TrendingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.trending_profession_layout);
        super.onCreate(savedInstanceState);
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
