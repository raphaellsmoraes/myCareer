package com.rm.mycareer.utils;

/**
 * Created by vntramo on 6/25/2014.
 */

import android.content.Context;
import android.util.Log;

import com.facebook.Session;
import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.command.AsyncCommand;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/**
 * Generic HTTP GET calls
 */
public class myCareerJSONRequest extends AsyncCommand {
    private static final String TAG = myCareerJSONRequest.class.getSimpleName();
    private int mTimeout;

    public myCareerJSONRequest(Context context, CommandListener listener) {
        super(context, listener);
    }
}