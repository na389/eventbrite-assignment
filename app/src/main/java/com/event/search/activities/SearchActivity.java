package com.event.search.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.event.search.R;
import com.event.search.models.Category;
import com.event.search.models.ListCategories;
import com.event.search.models.ListEvents;
import com.event.search.utils.JSONUtils;
import com.event.search.workers.GetEventData;
import com.event.search.workers.SearchFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activity holding the search fragment
 */
public class SearchActivity extends FragmentActivity{
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}