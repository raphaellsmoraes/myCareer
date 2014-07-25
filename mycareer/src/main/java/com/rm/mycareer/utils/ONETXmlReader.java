package com.rm.mycareer.utils;

import android.util.Base64;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vntramo on 7/23/2014.
 */
public class ONETXmlReader {

    private String code = "code";
    private String title = "title";
    private String what_they_do = "what_they_do";
    private String on_the_job = "on_the_job";
    private String task = "task";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public ONETXmlReader(String url) {
        this.urlString = url;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getWhat_they_do() {
        return what_they_do;
    }

    public String getOn_the_job() {
        return on_the_job;
    }

    public String getTask() {
        return task;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("code")) {
                            code = text;
                        } else if (name.equals("title")) {
                            title = myParser.getAttributeValue(null, "value");
                        } else if (name.equals("what_they_do")) {
                            what_they_do = myParser.getAttributeValue(null, "value");
                        } else if (name.equals("on_the_job")) {
                            on_the_job = myParser.getAttributeValue(null, "value");
                        } else if (name.equals("task")) {
                            task = myParser.getAttributeValue(null, "value");
                        } else {
                        }
                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
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

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
