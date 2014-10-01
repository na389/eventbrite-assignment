package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements DataModel{
    @JsonProperty(value = "name")
    Name name;

    @JsonProperty(value = "description")
    Description description;

    @JsonProperty(value = "url")
    String url;

    @JsonProperty(value = "logo_url")
    String logoURL;

    @JsonProperty(value = "start")
    Time startTime;

    @JsonProperty(value = "end")
    Time endTime;

    @JsonProperty(value = "status")
    String status;

    @JsonProperty(value = "venue")
    Venue venue;

    @JsonProperty(value = "category")
    Category category;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (category != null ? !category.equals(event.category) : event.category != null)
            return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        if (endTime != null ? !endTime.equals(event.endTime) : event.endTime != null) return false;
        if (logoURL != null ? !logoURL.equals(event.logoURL) : event.logoURL != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (startTime != null ? !startTime.equals(event.startTime) : event.startTime != null)
            return false;
        if (status != null ? !status.equals(event.status) : event.status != null) return false;
        if (url != null ? !url.equals(event.url) : event.url != null) return false;
        if (venue != null ? !venue.equals(event.venue) : event.venue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (venue != null ? venue.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name=" + name +
                ", description=" + description +
                ", url='" + url + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", venue=" + venue +
                ", category=" + category +
                '}';
    }
}
