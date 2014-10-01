package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/27/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subcategory implements DataModel{
    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "parent_category")
    Category parentCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subcategory)) return false;

        Subcategory that = (Subcategory) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentCategory != null ? !parentCategory.equals(that.parentCategory) : that.parentCategory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }
}
