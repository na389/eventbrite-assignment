package com.event.search.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.event.search.R;
import com.event.search.models.Category;
import com.event.search.models.Event;
import com.event.search.models.ListCategories;
import com.event.search.models.ListEvents;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by na389 on 9/27/14.
 * Utility class containing various methods used during the JSON call to deliver results to the user.
 * Jackson parser has been used to parse the JSON in the following functions.
 */

public class JSONUtils {
    public static final String API_URL_COMMON = "https://www.eventbriteapi.com/v3/";
    public static final String OAUTH_CLIENT_TOKEN ="LT4ADC6NUIVTQP5EDKEW";
    public static final String GET_EVENTS = "events/search/";
    public static final int GET_EVENTS_CONSTANT = 1;
    public static final String GET_CATEGORIES = "categories/";
    public static final int GET_CATEGORIES_CONSTANT = 2;
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";
    //public static final String API_KEY = "AIzaSyDwKqSTtSRVSIf-UZ4YrAPBji_BPEhXgmo";
    public static final String API_KEY = "AIzaSyDgeJy1p65bBPFICszA0cmPpw8sK9IiuWQ";
    public static final int GET_PLACES_CONSTANT = 3;
    public static final String GET_PLACES_INPUT = "places_input";
    public static final String API_NAME = "api_name";
    public static final String API_PARAMS = "api_params";
    private static String TAG = "JSONUtils";

    /**
     * Returns the wrapper of the categories from the json result passed to it.
     */

    public static ListCategories createCategory(String json){
        ListCategories jsonResult = new ListCategories();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jsonResult = mapper.readValue(json, ListCategories.class);
        }catch (JsonParseException e2){
            e2.printStackTrace();
            return jsonResult;
        }catch (JsonMappingException e3){
            e3.printStackTrace();
            return jsonResult;
        }catch (IOException e1){
            e1.printStackTrace();
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * Returns the wrapper of the events from the json result passed to it.
     */

    public static ListEvents createEvents(String json){
        ListEvents jsonResult = new ListEvents();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jsonResult = mapper.readValue(json, ListEvents.class);
        }catch (JsonParseException e2){
            e2.printStackTrace();
            return jsonResult;
        }catch (JsonMappingException e3){
            e3.printStackTrace();
            return jsonResult;
        }catch (IOException e1){
            e1.printStackTrace();
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * Returns the search results for keyword search from the json result passed to it.
     * Events name has been used as suggestions.
     */


    public static List<String> createSearchResults(String json){
        ListEvents events = createEvents(json);
        List<String> eventName = new ArrayList<String>();
        if(events == null)
            return eventName;
        List<Event> eventList = events.getEventList();

        if(eventList == null)
            return eventName;

        for(Event event: eventList){
            eventName.add(event.getName().getText());
        }
        return eventName;
    }

    /**
     * Returns the search results for location search from the json result passed to it.
     * Description of the Places has been used as it contains a lot of useful search keywords.
     * Google Places API with autocomplete has been used.
     */

    public static List<String> createPlacesSuggestions(String json){
        List<String> resultList = new ArrayList<String>();
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(json);
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
            return resultList;
        }
        return resultList;
    }
}