package com.event.search.workers;

import android.content.Context;
import android.os.AsyncTask;

import com.event.search.R;
import com.event.search.models.ListCategories;
import com.event.search.models.ListEvents;
import com.event.search.services.JSONHandler;
import com.event.search.utils.AlertDialogManager;
import com.event.search.utils.JSONUtils;

import java.util.HashMap;

/**
 * Created by na389 on 9/30/14.
 *
 * Thread used to get the search results depending upon API and parameters passed
 */
public class GetEventData extends AsyncTask<HashMap<String, String>, Void, String> {
    public static String TAG = "GetEventData";
    protected String mApiName;
    protected Context mContext;
    protected GetEventDataListener mListener;

    public GetEventData(String apiName, GetEventDataListener listener, Context context) {
        this.mApiName = apiName;
        this.mListener = listener;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(HashMap<String, String>... params) {
        if(mApiName == null || params == null ){
            return "";
        }

        JSONHandler jsonHandler = new JSONHandler(params[0], mApiName);
        String result = jsonHandler.executeWebMethod();
        return result;
    }

    @Override
    protected void onPostExecute(String jsonResult) {
        if(jsonResult == null){
            //AlertDialogManager.getInstance().showAlertDialog(mContext, mContext.getString(R.string.error_message_title), mContext.getString(R.string.error_server));
            return;
        }


        if(mListener == null)
            return;
        if(mApiName.equals(JSONUtils.GET_CATEGORIES)) {
            ListCategories result = JSONUtils.createCategory(jsonResult);
            mListener.setCategories(result);
        }
        else if(mApiName.equals(JSONUtils.GET_EVENTS)) {
            ListEvents result = JSONUtils.createEvents(jsonResult);
            mListener.setEvents(result);
        }


    }

    /*Interface to be implemented by the classes using the thread to use the result.
    * This is the only way of getting the output provided by the thread to keep the implementation neat*/
    public interface GetEventDataListener {
        public void setCategories(ListCategories listCategories);
        public void setEvents(ListEvents listEvents);
    }
}
