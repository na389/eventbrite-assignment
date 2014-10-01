package com.event.search.workers;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.event.search.R;
import com.event.search.activities.SearchActivity;
import com.event.search.models.Category;
import com.event.search.models.Event;
import com.event.search.models.ListCategories;
import com.event.search.models.ListEvents;
import com.event.search.models.Venue;
import com.event.search.utils.JSONUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**Class handling search related functionality
 *User Input
 *  a. Keyword
 *  b. Location
 *  c. Event Category
 *
 * Created by na389 on 9/30/14.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<List<String>>, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener,LocationListener,
        GetEventData.GetEventDataListener, TextView.OnEditorActionListener {
    public static String TAG = "SearchFragment";

    /*Keywords used to send parameters for the event search*/
    private final String LOCATION_ADDRESS = "location.address";
    private final String SEL_CATEGORIES = "categories";
    private final String KEYWORD = "q";
    private final String LOCATION_WITHIN = "location.within";
    private final String RADIUS = "1000mi";

    protected String mSelectedCategory;
    private SearchActivity mActivity;
    private ArrayAdapter mAdapter;
    private AutoCompleteTextView mPlacesAutoComplete;
    private AutoCompleteTextView mEventsAutoComplete;
    private int mApiType = 0;
    private ArrayList<Category> mListCategory;
    private LocationClient mLocationClient;
    private Location loc;
    private Spinner mCategorySpinner;
    private int mSelectedCatPos = 0;

    public SearchFragment() {
        mSelectedCategory = "";
        setArguments(new Bundle());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof SearchActivity)
            mActivity = (SearchActivity)activity;

        /*Used to populate Event categories on startup*/
        HashMap<String, String> params = new HashMap<String, String>();
        GetEventData getEventCategoriesThread = new GetEventData(JSONUtils.GET_CATEGORIES, this, mActivity);
        getEventCategoriesThread.execute(params);

        /*For fused location provider. It needs to check if google play services is available
        * Known bug exists when it is executed in emulator then it is unable to update the GooglePlayServices APK thus
        * giving an exception. Therefore, testing for this has been done in device*/
        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
        if(resp == ConnectionResult.SUCCESS){
            mLocationClient = new LocationClient(mActivity,this,this);
            mLocationClient.connect();
        }
        else{
            Toast.makeText(mActivity, "Google Play Service Error " + resp, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*Initiate th loader used to support the autocomplete feature for keyword and location*/
        Bundle args = new Bundle();
        args.putInt(JSONUtils.API_NAME, 0);
        args.putSerializable(JSONUtils.API_PARAMS, null);
        getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = (Button)rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
        mCategorySpinner = (Spinner)rootView.findViewById(R.id.category_options);
        mCategorySpinner.setOnItemSelectedListener(this);
        mPlacesAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.location_search_box);
        mPlacesAutoComplete.setOnEditorActionListener(this);
        mPlacesAutoComplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Bundle args = new Bundle();
                mApiType = JSONUtils.GET_PLACES_CONSTANT;
                args.putInt(JSONUtils.API_NAME, JSONUtils.GET_PLACES_CONSTANT);
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(JSONUtils.GET_PLACES_INPUT, s.toString());
                args.putSerializable(JSONUtils.API_PARAMS, params);
                getLoaderManager().restartLoader(0, args, SearchFragment.this);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEventsAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.event_search_box);
        mEventsAutoComplete.setOnEditorActionListener(this);
        mEventsAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Bundle args = new Bundle();
                mApiType = JSONUtils.GET_EVENTS_CONSTANT;
                args.putInt(JSONUtils.API_NAME, JSONUtils.GET_EVENTS_CONSTANT);
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(KEYWORD, charSequence.toString());
                args.putSerializable(JSONUtils.API_PARAMS, params);
                getLoaderManager().restartLoader(0, args, SearchFragment.this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
    }


    /**
    * Populates spinner with category list
    */
    public void setSpinnerData( List<Category> list) {
        String[] spinnerData = new String[list.size()];
        int i= 0;
        for(Category category: list){
            spinnerData[i++] = category.getName();
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, spinnerData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mSelectedCategory = ((TextView)view).getText().toString();
        mSelectedCatPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mSelectedCategory = "";
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle bundle) {
        return new SearchTask(mActivity, bundle.getInt(JSONUtils.API_NAME), (HashMap<String, String>)bundle.getSerializable(JSONUtils.API_PARAMS));
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> result) {
        if(result == null)
            return;
        String[] resultArray = new String[result.size()];
        result.toArray(resultArray);
        mAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, android.R.id.text1, resultArray);
        if(mApiType == JSONUtils.GET_EVENTS_CONSTANT)
            mEventsAutoComplete.setAdapter(mAdapter);
        else if(mApiType == JSONUtils.GET_PLACES_CONSTANT)
            mPlacesAutoComplete.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        loc = mLocationClient.getLastLocation();
        GetEventData getEventData = new GetEventData(JSONUtils.GET_EVENTS, this, mActivity);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(LOCATION_WITHIN, RADIUS);
        params.put("location.longitude", String.valueOf(loc.getLongitude()));
        params.put("location.latitude", String.valueOf(loc.getLatitude()));
        getEventData.execute(params);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void setCategories(ListCategories listCategories) {
        mListCategory = listCategories.getListCategory();
        setSpinnerData(mListCategory);
    }

    @Override
    public void setEvents(ListEvents listEvents) {
        final List<Event> list = listEvents.getEventList();
        int mapType = GoogleMap.MAP_TYPE_NORMAL;
        SupportMapFragment fragment = (SupportMapFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        final GoogleMap googleMap = fragment.getMap();
        googleMap.setMapType(mapType);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng((int) (loc.getLatitude() * 1E6),
                        (int) (loc.getLongitude() * 1E6)))
                .title("my position")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng((int) (loc.getLatitude() * 1E6),
                        (int) (loc.getLongitude() * 1E6)), 15.0f));

        final View mapView = fragment.getView();

        if (mapView.getViewTreeObserver().isAlive()) {

            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                // We check which build version we are using.
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    int counter = 0;
                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                    for (int i = 0; i < list.size(); i++) {
                        Venue venue = list.get(i).getVenue();
                        if(venue!=null) {

                            double latitude = Double.valueOf(venue.getLatitude());
                            double longitude = Double.valueOf(venue.getLongitude());
                            LatLng ll = new LatLng(latitude, longitude);
                            bld.include(ll);
                            counter++;
                            googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latitude, longitude))
                                            .title(list.get(i).getName().getText())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue))
                            );
                        }
                    }
                    if(counter > 0) {
                        LatLngBounds bounds = bld.build();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                }
            });
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch();
            return true;
        }
        return false;

    }

    /**
     * Method to perform search search when search button is pressed depending upon the user input
     */

    private void performSearch(){
        HashMap<String, String> params = new HashMap<String, String>();
        if(!mEventsAutoComplete.getText().toString().isEmpty()) {
            String val = mEventsAutoComplete.getText().toString();
            val.replaceAll("\\s+","+");
            params.put(KEYWORD, val);
        }
        if(!mSelectedCategory.isEmpty()){
            params.put(SEL_CATEGORIES, mListCategory.get(mSelectedCatPos).getId());
        }
        params.put(LOCATION_WITHIN, RADIUS);
        if(!mPlacesAutoComplete.getText().toString().isEmpty()) {
            String val = mPlacesAutoComplete.getText().toString();
            val.replaceAll("\\s+","+");
            params.put(LOCATION_ADDRESS, val);
        }
        GetEventData eventData = new GetEventData(JSONUtils.GET_EVENTS, this, mActivity);
        eventData.execute(params);
    }
}
