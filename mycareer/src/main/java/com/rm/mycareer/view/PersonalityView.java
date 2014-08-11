package com.rm.mycareer.view;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;

import com.rm.mycareer.R;
import com.rm.mycareer.adapter.HollandAdapter;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.myCareerUtils;

public class PersonalityView extends BaseActivity {

    public static boolean[] hollandSelection;
    private HollandAdapter gridAdapter;
    private ActionMode mActionMode;

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

                    int i = 0;
                    for (Boolean boo : hollandSelection) {
                        if (boo) {
                            i++;
                            mActionMode = PersonalityView.this.startActionMode(new ActionBarCallBack());
                            int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
                            View doneButton = PersonalityView.this.findViewById(doneButtonId);
                            doneButton.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    onActionModeDoneClick();
                                }
                            });
                        }
                    }
                    if (i == 0) mActionMode.finish();

                } else {
                    cb.setChecked(true);
                    PersonalityView.hollandSelection[cbId] = true;
                    mActionMode = PersonalityView.this.startActionMode(new ActionBarCallBack());
                    int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
                    View doneButton = PersonalityView.this.findViewById(doneButtonId);
                    doneButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            onActionModeDoneClick();
                        }
                    });
                }
            }
        });
    }


    public void onActionModeDoneClick() {
        Bundle bundle = new Bundle();
        bundle.putBooleanArray(myCareerUtils.PERSONALITY, hollandSelection);
        Intent intent = new Intent(myCareer.getContext(), PersonalityCompleteView.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class ActionBarCallBack implements ActionMode.Callback {

        boolean mActionModeIsActive = false;
        boolean mBackWasPressedInActionMode = false;

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mActionModeIsActive = true;
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        public int getBooleanCount() {
            int i = 0;
            for (Boolean bool : hollandSelection) {
                if (bool) i++;
            }
            return i;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(String.format("%d item selected", getBooleanCount()));
            return false;
        }
    }
}
