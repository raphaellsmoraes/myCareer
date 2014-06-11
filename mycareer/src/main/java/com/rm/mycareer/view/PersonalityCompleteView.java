package com.rm.mycareer.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.rm.mycareer.R;
import com.rm.mycareer.adapter.PersonalityListAdapter;
import com.rm.mycareer.utils.myCareerUtils;

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
public class PersonalityCompleteView extends BaseActivity {

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


        Bundle bundle = getIntent().getExtras();
        hollandSelection = bundle.getBooleanArray(myCareerUtils.PERSONALITY);
        answerList = (ListView) findViewById(R.id.lv_personality);
        buildRIASEC();
    }


    private void buildRIASEC() {
        String riasec = "";
        int R = 0, I = 1, A = 2, S = 3, E = 4, C = 5;


        int looper = 0;
        for (Boolean bool : hollandSelection) {
            if (looper == R) {
                if (bool) hollandArray[realistic] = hollandArray[realistic] + 1;
                R = R + 6;
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
        PersonalityListAdapter adapter = new PersonalityListAdapter(returnMap);
        answerList.setAdapter(adapter);

        buildGraph();
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

}
