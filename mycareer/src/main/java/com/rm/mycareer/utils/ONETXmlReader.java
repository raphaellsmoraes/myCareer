package com.rm.mycareer.utils;

import android.util.*;

import com.rm.mycareer.model.Career;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vntramo on 7/23/2014.
 */
public class ONETXmlReader {

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public ONETXmlReader(String url) {
        this.urlString = url;
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/vnd.org.onetcenter.mnm.career+xml");

                    String authorization = myCareerJSONRequest.ONET_USERNAME + ":" + myCareerJSONRequest.ONET_PASSWORD;
                    String encodedAuthorization = new String(Base64.encode(authorization.getBytes(), 0));
                    conn.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    /*xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);*/

                    BufferedReader r = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    JSONObject jsonObj = null;
                    try {
                        jsonObj = XML.toJSONObject(total.toString());
                    } catch (JSONException e) {
                        android.util.Log.e("JSON exception", e.getMessage());
                        e.printStackTrace();
                    }

                    android.util.Log.d("XML", total.toString());
                    android.util.Log.d("JSON", jsonObj.toString());

                    stream.close();
                    parsingComplete = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
