package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/27/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements DataModel{
    @JsonProperty(value = "id")
    String id;
    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "name_localized")
    String nameLocalized;

    @JsonProperty(value = "short_name")
    String shortName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(value = "subcategory")
    Subcategory subCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLocalized() {
        return nameLocalized;
    }

    public void setNameLocalized(String nameLocalized) {
        this.nameLocalized = nameLocalized;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Subcategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Subcategory subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nameLocalized='" + nameLocalized + '\'' +
                ", shortName='" + shortName + '\'' +
                ", subCategory=" + subCategory +
                '}';
    }
}
