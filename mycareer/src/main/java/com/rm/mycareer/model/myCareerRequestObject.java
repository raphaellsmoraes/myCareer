package com.rm.mycareer.model;

import java.util.HashMap;

/**
 * Created by vntramo on 6/25/2014.
 */
public class myCareerRequestObject {
    private static final boolean DEFAULT_CACHE = false;
    private RequestType mRequestType;
    private String mURL;
    private String mJsonCareerObject;
    private int mRequestCode;
    private HashMap<String, String> mHeaders;

    private boolean isCached;

    // Request Type
    public enum RequestType {
        GET,
        POST,
        DELETE,
        PUT;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean isCached) {
        this.isCached = isCached;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public String getURL() {
        return mURL;
    }

    public String getJsonKickerObject() {
        return mJsonCareerObject;
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

    public void addHeader(String headerName, String headerValue)
    {
        if(mHeaders == null)
            mHeaders = new HashMap<String, String>();

        mHeaders.put(headerName, headerValue);
    }

    public myCareerRequestObject(RequestType requestType, String url, String jsonKickerObject, int requestCode) {
        mRequestType = requestType;
        mURL = url;
        mJsonCareerObject = jsonKickerObject;
        mRequestCode = requestCode;
        isCached = DEFAULT_CACHE;
    }

    public myCareerRequestObject(RequestType requestType, String url, String jsonKickerObject, int requestCode, boolean isCached) {
        mRequestType = requestType;
        mURL = url;
        mJsonCareerObject = jsonKickerObject;
        mRequestCode = requestCode;
        this.isCached = isCached;
    }
}
