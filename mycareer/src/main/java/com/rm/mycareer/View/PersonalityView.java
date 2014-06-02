package com.rm.mycareer.view;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;

import com.rm.mycareer.R;
import com.rm.mycareer.adapter.HollandAdapter;
import com.rm.mycareer.utils.myCareerUtils;

public class PersonalityView extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.personality_page_viewer);
        super.onCreate(savedInstanceState);

        GridView gridview = (GridView) findViewById(R.id.grid_view);
        final HollandAdapter gridAdapter = new HollandAdapter(myCareerUtils.getDrawableList());
        gridview.setAdapter(gridAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("teste", "teste na pos:"+position+"-ischecked:"+gridAdapter.isChecked(position));
                gridAdapter.setChecked(position, !gridAdapter.isChecked(position));
            }
        });
    }
}
