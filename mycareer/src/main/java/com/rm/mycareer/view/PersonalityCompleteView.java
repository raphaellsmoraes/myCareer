package com.rm.mycareer.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.google.gson.Gson;
import com.rm.mycareer.R;
import com.rm.mycareer.adapter.PersonalityListAdapter;
import com.rm.mycareer.model.Personality;
import com.rm.mycareer.model.User;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rapha_000 on 08/06/2014.
 */
public class PersonalityCompleteView extends Activity {

    int[] hollandArray = new int[6];
    private boolean[] hollandSelection;
    private static int realistic = 0;
    private static int investigative = 1;
    private static int artistic = 2;
    private static int social = 3;
    private static int enterprising = 4;
    private static int conventional = 5;
    private static String sRealistic = "Realistic";
    private static String sInvestigative = "Investigative";
    private static String sArtistic = "Artistic";
    private static String sSocial = "Social";
    private static String sEnterprising = "Enterprising";
    private static String sConventional = "Conventional";

    private ListView answerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.personalitycomplete_layout);
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        answerList = (ListView) findViewById(R.id.lv_personality);

        Bundle bundle = getIntent().getExtras();
        if (myCareerUtils.getHolland()) {
            buildFromResource();
        } else {
            hollandSelection = bundle.getBooleanArray(myCareerUtils.PERSONALITY);
            buildRIASEC();
        }
    }


    private void buildRIASEC() {
        String riasec = "";
        int _R = 0, I = 1, A = 2, S = 3, E = 4, C = 5;


        int looper = 0;
        for (Boolean bool : hollandSelection) {
            if (looper == _R) {
                if (bool) hollandArray[realistic] = hollandArray[realistic] + 1;
                _R = _R + 6;
            } else if (looper == I) {
                if (bool) hollandArray[investigative] = hollandArray[investigative] + 1;
                I = I + 6;
            } else if (looper == A) {
                if (bool) hollandArray[artistic] = hollandArray[artistic] + 1;
                A = A + 6;
            } else if (looper == S) {
                if (bool) hollandArray[social] = hollandArray[social] + 1;
                S = S + 6;
            } else if (looper == E) {
                if (bool) hollandArray[enterprising] = hollandArray[enterprising] + 1;
                E = E + 6;
            } else if (looper == C) {
                if (bool) hollandArray[conventional] = hollandArray[conventional] + 1;
                C = C + 6;
            }

            looper++;
        }

        Map<String, Integer> hollandMap = new HashMap<String, Integer>();
        hollandMap.put(sRealistic, hollandArray[realistic]);
        hollandMap.put(sInvestigative, hollandArray[investigative]);
        hollandMap.put(sArtistic, hollandArray[artistic]);
        hollandMap.put(sSocial, hollandArray[social]);
        hollandMap.put(sEnterprising, hollandArray[enterprising]);
        hollandMap.put(sConventional, hollandArray[conventional]);

        /* returns your riasec type */
        Map<String, Integer> returnMap = sortByValue(hollandMap);
        final PersonalityListAdapter adapter = new PersonalityListAdapter(returnMap);
        answerList.setAdapter(adapter);
        final Context context = this;
        answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LayoutInflater inflater = getLayoutInflater();
                View inflaterView = inflater.inflate(R.layout.personality_card_layout, null);

                TextView textHeader = (TextView) inflaterView.findViewById(R.id.tv_card_header);
                TextView textDesc = (TextView) inflaterView.findViewById(R.id.tv_card_desc);


                Log.d("teste", adapter.getItem(i).toString());

                if (adapter.getItem(i).toString().equals(sRealistic)) {
                    textHeader.setText(R.string.realistic_header);
                    textDesc.setText(R.string.realistic_description);
                } else if (adapter.getItem(i).toString().equals(sInvestigative)) {
                    textHeader.setText(R.string.investigative_header);
                    textDesc.setText(R.string.investigative_description);
                } else if (adapter.getItem(i).toString().equals(sArtistic)) {
                    textHeader.setText(R.string.artistic_header);
                    textDesc.setText(R.string.artistic_description);
                } else if (adapter.getItem(i).toString().equals(sSocial)) {
                    textHeader.setText(R.string.social_header);
                    textDesc.setText(R.string.social_description);
                } else if (adapter.getItem(i).toString().equals(sEnterprising)) {
                    textHeader.setText(R.string.enterprising_header);
                    textDesc.setText(R.string.enterprising_description);
                } else if (adapter.getItem(i).toString().equals(sConventional)) {
                    textHeader.setText(R.string.conventional_header);
                    textDesc.setText(R.string.conventional_description);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(inflaterView)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });
        buildGraph();

        myCareerUtils.setHolland(hollandMap);

        /* Get user and update Personality */
        User user = myCareerUtils.getUser();
        Personality personality = new Personality(hollandArray[realistic], hollandArray[investigative],
                hollandArray[artistic], hollandArray[social],
                hollandArray[enterprising], hollandArray[conventional]);

        Gson gson = new Gson();
        String json = gson.toJson(personality);

          /* Update Personality */
        myCareerJSONObject requestObject =
                new myCareerJSONObject(myCareerJSONRequest.RequestType.POST, myCareerJSONRequest.MYCAREER_BASE_URL + myCareerJSONRequest.MYCAREER_UPDATE_PERSONALITY + user.getId(), json, 0);

        myCareerJSONRequest updatePersonality = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
            @Override
            public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {
                if (statusCode == 200) {
                    Gson gson1 = new Gson();
                    User user1 = gson1.fromJson(result, User.class);
                    myCareerUtils.setUser(user1);
                    Log.d("teste", user1.getPersonality().toString());

                    if (myCareerUtils.getHolland()) {
                    /* Goes to Search page */
                        Intent intent = new Intent(myCareer.getContext(), SearchActivity.class);
                        startActivity(intent);
                    } else {
                    /* Start holland test */
                        Intent intent = new Intent(myCareer.getContext(), PersonalityView.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCommandFinished(boolean result) {

            }
        });

        updatePersonality.start();

    }

    static Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            if (i > list.size() - 4)
                result.put(entry.getKey(), entry.getValue());
            i++;
        }
        return result;
    }

    private void buildGraph() {
        PieGraph pg = (PieGraph) findViewById(R.id.graph);
        pg.setInnerCircleRatio(150);

        /* realistic */
        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(hollandArray[realistic]);
        slice.setTitle(new String("Realistic"));
        pg.addSlice(slice);

        /* investigative */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(hollandArray[investigative]);
        slice.setTitle(new String("Investigative"));
        pg.addSlice(slice);

        /* artistic */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(hollandArray[artistic]);
        slice.setTitle(new String("Artistic"));
        pg.addSlice(slice);

        /* social */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#DDFFAA"));
        slice.setValue(hollandArray[social]);
        slice.setTitle(new String("Social"));
        pg.addSlice(slice);

        /* enterprising */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FF0033"));
        slice.setValue(hollandArray[enterprising]);
        slice.setTitle(new String("Enterprising"));
        pg.addSlice(slice);

        /* conventional */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#BBFF33"));
        slice.setValue(hollandArray[conventional]);
        slice.setTitle(new String("Conventional"));
        pg.addSlice(slice);

    }

    private void buildFromResource() {

        Map<String, Integer> hollandMap = myCareerUtils.getHollandValue();

        /* returns your riasec type */
        Map<String, Integer> returnMap = sortByValue(hollandMap);
        final PersonalityListAdapter adapter = new PersonalityListAdapter(returnMap);
        answerList.setAdapter(adapter);
        final Context context = this;
        answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LayoutInflater inflater = getLayoutInflater();
                View inflaterView = inflater.inflate(R.layout.personality_card_layout, null);

                TextView textHeader = (TextView) inflaterView.findViewById(R.id.tv_card_header);
                TextView textDesc = (TextView) inflaterView.findViewById(R.id.tv_card_desc);


                Log.d("teste", adapter.getItem(i).toString());

                if (adapter.getItem(i).toString().equals(sRealistic)) {
                    textHeader.setText(R.string.realistic_header);
                    textDesc.setText(R.string.realistic_description);
                } else if (adapter.getItem(i).toString().equals(sInvestigative)) {
                    textHeader.setText(R.string.investigative_header);
                    textDesc.setText(R.string.investigative_description);
                } else if (adapter.getItem(i).toString().equals(sArtistic)) {
                    textHeader.setText(R.string.artistic_header);
                    textDesc.setText(R.string.artistic_description);
                } else if (adapter.getItem(i).toString().equals(sSocial)) {
                    textHeader.setText(R.string.social_header);
                    textDesc.setText(R.string.social_description);
                } else if (adapter.getItem(i).toString().equals(sEnterprising)) {
                    textHeader.setText(R.string.enterprising_header);
                    textDesc.setText(R.string.enterprising_description);
                } else if (adapter.getItem(i).toString().equals(sConventional)) {
                    textHeader.setText(R.string.conventional_header);
                    textDesc.setText(R.string.conventional_description);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(inflaterView)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        buildGraphFromResource();
    }

    private void buildGraphFromResource() {

        Map<String, Integer> hollandMap = myCareerUtils.getHollandValue();

        PieGraph pg = (PieGraph) findViewById(R.id.graph);
        pg.setInnerCircleRatio(150);

        /* realistic */
        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(hollandMap.get(sRealistic));
        slice.setTitle(new String("Realistic"));
        pg.addSlice(slice);

        /* investigative */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(hollandMap.get(sInvestigative));
        slice.setTitle(new String("Investigative"));
        pg.addSlice(slice);

        /* artistic */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(hollandMap.get(sArtistic));
        slice.setTitle(new String("Artistic"));
        pg.addSlice(slice);

        /* social */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#DDFFAA"));
        slice.setValue(hollandMap.get(sSocial));
        slice.setTitle(new String("Social"));
        pg.addSlice(slice);

        /* enterprising */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FF0033"));
        slice.setValue(hollandMap.get(sEnterprising));
        slice.setTitle(new String("Enterprising"));
        pg.addSlice(slice);

        /* conventional */
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#BBFF33"));
        slice.setValue(hollandMap.get(sConventional));
        slice.setTitle(new String("Conventional"));
        pg.addSlice(slice);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
