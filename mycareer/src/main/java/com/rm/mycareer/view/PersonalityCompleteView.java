package com.rm.mycareer.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.rm.mycareer.R;
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

    private boolean[] hollandSelection;
    private static int realistic = 0;
    private static int investigative = 1;
    private static int artistic = 2;
    private static int social = 3;
    private static int enterprising = 4;
    private static int conventional = 5;
    private static String sRealistic = "R";
    private static String sInvestigative = "I";
    private static String sArtistic = "A";
    private static String sSocial = "S";
    private static String sEnterprising = "E";
    private static String sConventional = "C";

    private TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.personalitycomplete_layout);
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        hollandSelection = bundle.getBooleanArray(myCareerUtils.PERSONALITY);
        answer = (TextView) findViewById(R.id.tv_personality_result);
        buildRIASEC();
    }


    private void buildRIASEC() {
        String riasec = "";
        int[] hollandArray = new int[6];
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

        Log.d("riasectest", "R:" + hollandArray[realistic] + "I:" + hollandArray[investigative]
                + "A:" + hollandArray[artistic] + "S:" + hollandArray[social] + "E:" + hollandArray[enterprising]
                + "R:" + hollandArray[conventional]);

        Map<String, Integer> lMap = new HashMap<String, Integer>();
        lMap.put(sRealistic, hollandArray[realistic]);
        lMap.put(sInvestigative, hollandArray[investigative]);
        lMap.put(sArtistic, hollandArray[artistic]);
        lMap.put(sSocial, hollandArray[social]);
        lMap.put(sEnterprising, hollandArray[enterprising]);
        lMap.put(sConventional, hollandArray[conventional]);

        Map<String, Integer> returnMap = sortByValue(lMap);
        Log.d("riasectest", returnMap.toString());

        answer.setText(returnMap.keySet().toString());

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

}
