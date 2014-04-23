package com.rm.mycareer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.rm.mycareer.view.PersonalityView;

import java.util.Arrays;
import java.util.List;

public class LandingPage extends FragmentActivity {

    private LoginButton loginBtn;
    private Button requestInfoBtn;
    private Button moveToPersonality;
    private TextView userName;
    private UiLifecycleHelper uiHelper;

    private static final List<String> PERMISSIONS = Arrays.asList("user_location", "user_birthday", "user_likes", "user_interests", "friends_interests");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing_page);

        userName = (TextView) findViewById(R.id.user_name);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        moveToPersonality = (Button) findViewById(R.id.to_personality_view);

        loginBtn.setReadPermissions(PERMISSIONS);

        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userName.setText("Hello, " + user.getName());
                } else {
                    userName.setText("You are not logged");
                }
            }
        });

        requestInfoBtn = (Button) findViewById(R.id.request_info);
        requestInfoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putString("fields", "id,birthday,name,location,gender,music,likes,movies,education");
                params.putString("limit", "500");

               /* User request */
                new Request(
                        Session.getActiveSession(),
                        "/me",
                        params,
                        HttpMethod.GET,
                        new Request.Callback() {
                            public void onCompleted(Response response) {
                                /* handle the result */
                                GraphObject mUserInfo = response.getGraphObject();
                                try {
                                    Log.d("user_info", mUserInfo.getProperty("id").toString() + mUserInfo.getProperty("name"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
            }
        });

        moveToPersonality.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalityView.class);
                startActivity(intent);
            }
        });


        buttonsEnabled(false);
    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {

            if (state.isOpened()) {
                buttonsEnabled(true);
                Log.d(this.getClass().toString(), "Facebook session opened");
            } else if (state.isClosed()) {
                buttonsEnabled(false);
                Log.d(this.getClass().toString(), "Facebook session closed");
            }
        }
    };

    public void buttonsEnabled(boolean isEnabled) {
        requestInfoBtn.setEnabled(isEnabled);
        moveToPersonality.setEnabled(isEnabled);
    }

    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().containsAll(PERMISSIONS);
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        buttonsEnabled(Session.getActiveSession().isOpened());
    }


    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}