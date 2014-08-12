package com.rm.mycareer.view;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.fima.cardsui.views.CardUI;
import com.rm.mycareer.R;
import com.rm.mycareer.utils.AsyncTaskCompleteListener;
import com.rm.mycareer.utils.myCareerJSONObject;
import com.rm.mycareer.utils.myCareerJSONRequest;
import com.rm.mycareer.utils.myCareerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vntramo on 6/26/2014.
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private CardUI mCardView;
    private JSONArray mCareers;
    private JSONObject mCareerDetail;
    private ProgressBar mProgressBar;
    private SearchView mSearchView;
    private String mStatusView;
    private TextView mTextView;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.trending_cardui_layout);
        super.onCreate(savedInstanceState);


        // init CardView
        mCardView = (CardUI) findViewById(R.id.cardsview);
        mCardView.setSwipeable(false);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_trending);
        mTextView = (TextView) findViewById(R.id.tv_search_description);
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_actions, menu);

        // Add SearchWidget.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.options_menu_main_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        setupSearchView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mStatusView = s;

        myCareerUtils.hideKeyboard(this);

        mTextView.setVisibility(View.GONE);
        mCardView.clearCards();
        mCardView.refresh();
        mProgressBar.setVisibility(View.VISIBLE);

        /* Request a Profession */
        myCareerJSONObject requestObject =
                new myCareerJSONObject(myCareerJSONRequest.RequestType.ONET, myCareerJSONRequest.GET_KEYWORD_ONET_BASE_URL
                        + mStatusView, null, 0);

        myCareerJSONRequest req = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
            @Override
            public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {

                if (statusCode == 200) {


                    JSONObject jsonObj = null;
                    try {
                        jsonObj = XML.toJSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mCareers = jsonObj.getJSONObject("careers").getJSONArray("career");

                    for (i = 0; i < mCareers.length(); i++) {
                        //Log.d("TESTE", "i" + i);
                        String code = mCareers.getJSONObject(i).getString("code");

                        /* Request a Profession*/
                        myCareerJSONObject requestObject =
                                new myCareerJSONObject(myCareerJSONRequest.RequestType.ONET, myCareerJSONRequest.GET_CAREER_ONET_BASE_URL
                                        + code, null, 0);

                        myCareerJSONRequest reqProfession = new myCareerJSONRequest(requestObject, new AsyncTaskCompleteListener() {
                            @Override
                            public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException {

                                if (statusCode == 200) {
                                    JSONObject jsonObj = null;
                                    try {
                                        jsonObj = XML.toJSONObject(result);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    mCareerDetail = jsonObj.getJSONObject("career");
                                    final String title = mCareerDetail.getString("title");
                                    final String description = mCareerDetail.getString("what_they_do");
                                    final ArrayList<String> tasks = new ArrayList<String>();

                                    for (int i = 0; i < mCareerDetail.getJSONObject("on_the_job").getJSONArray("task").length(); i++) {
                                        tasks.add(mCareerDetail.getJSONObject("on_the_job").getJSONArray("task").get(i).toString());
                                    }

                                    final CardPlay newCard = new CardPlay(title, description, "RED", "BLACK", false, true);
                                    newCard.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(SearchActivity.this, ProfessionDetailsView.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(myCareerUtils.TITLE, title);
                                            bundle.putString(myCareerUtils.WHATTHEYDO, newCard.getDescription());
                                            bundle.putStringArrayList(myCareerUtils.ONTHEJOB, tasks);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    });

                                    mCardView.addCard(newCard);

                                    // draw cards
                                    //Log.d("card", "Size:" + mCardView.getChildCount());
                                    mProgressBar.setVisibility(View.GONE);
                                    mCardView.refresh();
                                }
                            }

                            @Override
                            public void onCommandFinished(boolean result) {

                            }
                        });
                        reqProfession.start();
                    /* End request */
                    }
                }
            }

            @Override
            public void onCommandFinished(boolean result) {
            }
        });

        req.start();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }
}
