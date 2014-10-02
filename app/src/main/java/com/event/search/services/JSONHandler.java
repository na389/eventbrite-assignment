package com.event.search.services;

import android.util.Log;

import com.event.search.utils.JSONUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by na389 on 9/27/14.
 * Class to handle interaction with the web.
 * It makes the http call and returns the json result
 */

public class JSONHandler {
    public static final String TAG = "JSONHandler";
    private HashMap<String, String> mParams;
    private String mApiName;

    public JSONHandler(HashMap<String, String> params, String apiName) {
        this.mParams = params;
        this.mApiName = apiName;
    }

    /**
     * Method to return json result for the call made for eventbrite API:
     * 1. Event search
     * 2. Category
     *
     * @return
     */
    public String executeWebMethod() {

        if (mParams == null) {
            return "";
        }
        String responseString = null;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(JSONUtils.API_URL_COMMON).append(mApiName).append("?token=").append(JSONUtils.OAUTH_CLIENT_TOKEN).append("&");
            if (mParams != null) {
                for (Map.Entry<String, String> item : mParams.entrySet()) {
                    urlBuilder.append(item.getKey() + "=" + item.getValue() + "&");
                }
            }
            //Remove & at the end
            String url = urlBuilder.substring(0, urlBuilder.length() - 1);
            //There can be spaces in the string. Replace then with relevant character which server understands
            String finalUrl = url.replaceAll("\\s+", "%20");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>." + finalUrl);
            HttpResponse response = httpclient.execute(new HttpGet(finalUrl));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                responseString = EntityUtils.toString(response.getEntity());
            } else {
                Log.e(TAG, "Failed to download--" + statusLine.toString());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    /**
     * Method to return json result of Google Places API depending upon what is user looking for.
     *
     * @param input
     * @return
     */
    public String placesAutoComplete(String input) {
        String result = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(JSONUtils.PLACES_API_BASE + JSONUtils.TYPE_AUTOCOMPLETE + JSONUtils.OUT_JSON);
            sb.append("?key=" + JSONUtils.API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return result;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return jsonResults.toString();
    }
}