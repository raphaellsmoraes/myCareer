package com.rm.mycareer.utils;

import java.util.HashMap;

/**
 * Created by vntramo on 7/22/2014.
 */
public class myCareerJSONObject {
    private myCareerJSONRequest.RequestType mRequestType;
    private String mURL;
    private String mJSONObject;
    private int mRequestCode;
    private HashMap<String, String> mHeaders;


    public String getURL() {
        return mURL;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public HashMap<String, String> getmHeaders() {
        return mHeaders;
    }

    public void setmHeaders(HashMap<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }

    public void addHeader(String headerName, String headerValue) {
        if (mHeaders == null)
            mHeaders = new HashMap<String, String>();

        mHeaders.put(headerName, headerValue);
    }

    public myCareerJSONObject(myCareerJSONRequest.RequestType mRequestType, String mURL, String mJSONObject, int mRequestCode) {
        this.mRequestType = mRequestType;
        this.mURL = mURL;
        this.mJSONObject = mJSONObject;
        this.mRequestCode = mRequestCode;
    }

    public String getmJSONObject() {
        return mJSONObject;
    }

    public myCareerJSONRequest.RequestType getmRequestType() {
        return mRequestType;
    }
}
