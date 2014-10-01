package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by na389 on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListEvents {

    @JsonProperty(value = "events")
    ArrayList<Event> eventList;

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListEvents)) return false;

        ListEvents that = (ListEvents) o;

        if (eventList != null ? !eventList.equals(that.eventList) : that.eventList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return eventList != null ? eventList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListEvents{" +
                "eventList=" + eventList +
                '}';
    }
}
