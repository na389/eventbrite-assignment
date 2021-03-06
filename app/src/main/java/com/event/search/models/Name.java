package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Name implements DataModel{
    @JsonProperty(value = "text")
    private String text;
    @JsonProperty(value = "html")
    private String html;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;

        Name name = (Name) o;

        if (html != null ? !html.equals(name.html) : name.html != null) return false;
        if (text != null ? !text.equals(name.text) : name.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (html != null ? html.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Name{" +
                "text='" + text + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
