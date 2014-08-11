package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rm.mycareer.R;
import com.rm.mycareer.utils.myCareerUtils;

import java.util.ArrayList;

/**
 * Created by vntramo on 8/4/2014.
 */
public class ProfessionDetailsView extends BaseActivity {

    private ProgressBar mProgressBar;
    private String mStringTasks;


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

        mProgressBar.setVisibility(View.GONE);
    }
}
