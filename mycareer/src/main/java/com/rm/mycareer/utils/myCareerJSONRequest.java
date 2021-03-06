package com.rm.mycareer.utils;

/**
 * Created by vntramo on 6/25/2014.
 */

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.rm.mycareer.myCareer;
import com.rm.mycareer.utils.command.AsyncCommand;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Generic HTTP GET calls
 */
public class myCareerJSONRequest extends AsyncCommand {

    private static final String TAG = myCareerJSONRequest.class.getSimpleName();
    private int mTimeout;
    public static String FACEBOOK_ID;

    private AsyncTaskCompleteListener mCallback;
    private myCareerJSONObject mRequestObject;
    private int mStatusCode;

    public static final String GET_FB_PICTURE = "https://graph.facebook.com/%s/picture?type=large";
    public static final String GET_CAREER_ONET_BASE_URL = "http://services.onetcenter.org/v1.3/ws/mnm/careers/";
    public static final String GET_KEYWORD_ONET_BASE_URL = "http://services.onetcenter.org/v1.3/ws/mnm/search?keyword=";
    public static final String GET_ONET_OCCUPATION_BASE_URL = "http://services.onetcenter.org/ws/online/occupations/";
    public static final String GET_INTEREST_PROFILER_BASE_URL = "https://services.onetcenter.org/ws/mnm/interestprofiler/careers?";
    public static final String GET_REALISTIC = "Realistic=";
    public static final String GET_INVESTIGATIVE = "Investigative=";
    public static final String GET_ARTISTIC = "Artistic=";
    public static final String GET_SOCIAL = "Social=";
    public static final String GET_ENTERPRISING = "Enterprising=";
    public static final String GET_CONVETIONAL = "Conventional=";
    public static final String GET_ONET_OCCUPATION_TASKS = "/summary/tasks";
    public static final String GET_MYCAREER_OCCUPATION = "/api/user/connect/";
    public static final String MYCAREER_BASE_URL = "http://mycareer-mcwebservice.rhcloud.com";
    public static final String MYCAREER_ADD_USER = "/api/user/connect";
    public static final String MYCAREER_UPDATE_PERSONALITY = "/api/user/updatePersonality?id=";
    public static final String MYCAREER_UPDATE_RATING = "/api/user/updateRating?id=";
    public static final String MYCAREER_USER_RECOMMENDATION = "/api/user/recommendations?id=";
    public static final String MYCAREER_TRENDING = "/api/user/trending";
    public static final String ONET_USERNAME = "puc_campinas_edu_br";
    public static final String ONET_PASSWORD = "6392kpv";

    //Request Type
    public enum RequestType {
        GET,
        ONET,
        POST,
        DELETE,
        ONET_INTEREST,
        PUT;
    }

    public myCareerJSONRequest(Context context, CommandListener listener) {
        super(context, listener);
    }

    public myCareerJSONRequest(myCareerJSONObject requestObject, AsyncTaskCompleteListener callback) {
        super(myCareer.getContext(), callback);
        mCallback = callback;
        mRequestObject = requestObject;
        mTimeout = 0;

    }

    public myCareerJSONRequest(myCareerJSONObject requestObject, int timeout, AsyncTaskCompleteListener callback) {
        super(myCareer.getContext(), callback);
        mCallback = callback;
        mRequestObject = requestObject;
        mTimeout = timeout;

    }

    /**
     * Unregister the callback. You must be used when exit of the activity.
     */
    public void unregisterCallback() {
        mCallback = null;
    }

    /**
     * Using Bismark Lib to implement Async requests.
     */
    @Override
    protected void onPostExecute(boolean result) {

        if (mCallback != null) {
            try {
                mCallback.onTaskComplete(returnListener, mStatusCode, mRequestObject.getRequestCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "mCallback is NULL!");
        }

        super.onPostExecute(result);
    }

    @Override
    protected boolean execute() {
        String responseStr = "";

        String uri = mRequestObject.getURL();
        HttpURLConnection urlConnection = null;
        try {
        /* Create connection */
            URL url = null;
            url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            //Setting parameters
            //Timeout
            if (mTimeout != 0) {
                urlConnection.setConnectTimeout(mTimeout);
            }

            Log.d(TAG, url.toString());

            String jsonEn = mRequestObject.getmJSONObject();
            RequestType requestType = mRequestObject.getmRequestType();
            switch (requestType) {
                case POST:
                    Log.d(TAG, "myCareer POST ****************************** " + jsonEn);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    if (jsonEn != null) {
                        try {
                            DataOutputStream wr = new DataOutputStream(
                                    urlConnection.getOutputStream());
                            wr.writeBytes(jsonEn);
                            wr.flush();
                            wr.close();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DELETE:
                    break;
                case PUT:
                    break;
                case ONET:
                    Log.i(TAG, "myCareer ONET GET ");
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/vnd.org.onetcenter.mnm.career+xml");

                    String authorization = ONET_USERNAME + ":" + ONET_PASSWORD;
                    String encodedAuthorization = new String(Base64.encode(authorization.getBytes(), 0));
                    urlConnection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

                    if (responseStr.length() > 0) {
                        Log.d(TAG, "Response from cache: " + responseStr);
                        mStatusCode = 200;
                        returnListener = responseStr;
                        return true;
                    }
                    break;
                case ONET_INTEREST:
                    Log.i(TAG, "myCareer ONET GET ");
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/xml");

                    String auth = ONET_USERNAME + ":" + ONET_PASSWORD;
                    String encodedAuth = new String(Base64.encode(auth.getBytes(), 0));
                    urlConnection.setRequestProperty("Authorization", "Basic " + encodedAuth);

                    if (responseStr.length() > 0) {
                        Log.d(TAG, "Response from cache: " + responseStr);
                        mStatusCode = 200;
                        returnListener = responseStr;
                        return true;
                    }
                    break;

                case GET:
                default:
                    Log.i(TAG, "myCareer GET ");
                    urlConnection.setRequestMethod("GET");

                    if (responseStr.length() > 0) {
                        Log.d(TAG, "Response from cache: " + responseStr);
                        mStatusCode = 200;
                        returnListener = responseStr;
                        return true;
                    }
                    break;
            }

            //Get Response
            mStatusCode = urlConnection.getResponseCode();
            InputStream is = urlConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            responseStr = response.toString();
            Log.d(TAG, "myCareer Response: " + mStatusCode + " : " + responseStr);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.toString());
            e.printStackTrace();
            mStatusCode = 0;
            returnListener = null;
            return false;
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.toString());
            e.printStackTrace();
            mStatusCode = 0;
            returnListener = null;
            return false;
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointer: " + e.toString());
            mStatusCode = 0;
            returnListener = null;
            return false;
        }


        returnListener = responseStr;
        urlConnection.disconnect();
        return true;
    }
}