package com.rm.mycareer.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rm.mycareer.R;
import com.rm.mycareer.utils.myCareerUtils;

/**
 * Created by vntramo on 8/4/2014.
 */
public class ProfessionDetailsView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.profession_layout);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        TextView tv_description = (TextView)findViewById(R.id.tv_description);
        TextView tv_onthejob = (TextView)findViewById(R.id.tv_onthejob);


        tv_title.setText(bundle.getString(myCareerUtils.TITLE));
        tv_description.setText(bundle.getString(myCareerUtils.WHATTHEYDO));


    }
}
