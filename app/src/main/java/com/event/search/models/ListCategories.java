package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by na389 on 9/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCategories {

    @JsonProperty(value = "categories")
    private ArrayList<Category> mListCategory;

    public ArrayList<Category> getListCategory() {
        return mListCategory;
    }

    public void setListCategory(ArrayList<Category> mListCategory) {
        this.mListCategory = mListCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListCategories)) return false;

        ListCategories that = (ListCategories) o;

        if (mListCategory != null ? !mListCategory.equals(that.mListCategory) : that.mListCategory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mListCategory != null ? mListCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListCategories{" +
                "mListCategory=" + mListCategory +
                '}';
    }
}
