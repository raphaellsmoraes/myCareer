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

    public static boolean[] hollandSelection;
    private HollandAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.personality_page_viewer);
        super.onCreate(savedInstanceState);

        this.hollandSelection = new boolean[myCareerUtils.getDrawableList().size()];

        GridView gridview = (GridView) findViewById(R.id.grid_view);
        gridAdapter = new HollandAdapter(myCareerUtils.getDrawableList());
        gridview.setAdapter(gridAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HollandAdapter.ViewHolderItem holder = (HollandAdapter.ViewHolderItem) view.getTag();

                CheckBox cb = (CheckBox) holder.checkItem;
                int cbId = cb.getId();
                if (PersonalityView.hollandSelection[cbId]) {
                    cb.setChecked(false);
                    PersonalityView.hollandSelection[cbId] = false;
                } else {
                    cb.setChecked(true);
                    PersonalityView.hollandSelection[cbId] = true;
                }

                int i = 0;
                for(Boolean booleanTeste : hollandSelection){
                    Log.d("booleanTeste", (booleanTeste ? ("true"+i):("false"+i)));
                    i++;
                }
            }
        });
    }
}
