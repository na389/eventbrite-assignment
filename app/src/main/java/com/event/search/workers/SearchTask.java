package com.event.search.workers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.event.search.R;
import com.event.search.models.ListCategories;
import com.event.search.services.JSONHandler;
import com.event.search.utils.AlertDialogManager;
import com.event.search.utils.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by na389 on 9/30/14.
 */
public class SearchTask extends AsyncTaskLoader<List<String>> {
    private String TAG = "SearchTask";
    private int mApiName;
    private Context mContext;
    private List<String> mSearchResults;
    private HashMap<String, String> mParams;
    private GetSearchSuggestionsListener mListener;
    private List<String> mResult;
    public SearchTask(Context context, int apiName, HashMap<String, String> params) {
        super(context);
        this.mContext = context;
        this.mApiName = apiName;
        this.mParams = params;
    }

    @Override
    public List<String> loadInBackground() {

            JSONHandler jsonHandler = null;
            List<String> result = new ArrayList<String>();
            String jsonResult = "";

            if(mApiName == 0 || mParams == null) {
                Log.e(TAG, "API/Parameters not included in request. Operation cancelled");
                return result;
            }else {


                switch (mApiName) {
                    case JSONUtils.GET_EVENTS_CONSTANT:
                        jsonHandler = new JSONHandler(mParams, JSONUtils.GET_EVENTS);
                        jsonResult = jsonHandler.executeWebMethod();
                        result = JSONUtils.createSearchResults(jsonResult);
                        return result;
                    case JSONUtils.GET_PLACES_CONSTANT:
                        jsonHandler = new JSONHandler(null, null);
                        jsonResult = jsonHandler.placesAutoComplete(mParams.get(JSONUtils.GET_PLACES_INPUT));
                        result = JSONUtils.createPlacesSuggestions(jsonResult);
                        return result;

                }
                return result;
            }
    }

    @Override
    public void deliverResult(List<String> data) {
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    public interface  GetSearchSuggestionsListener{
        public void setPlacesSuggestions(List<String> placesSuggestions);
        public void setKeywordSuggestions(List<String> keywordSuggestions);
    }
}