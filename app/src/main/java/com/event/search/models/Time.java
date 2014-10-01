package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Time implements DataModel{
    @JsonProperty(value = "timezone")
    String timezone;
    @JsonProperty(value = "local")
    String local;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    @JsonProperty(value = "utc")
    String utc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;

        Time time = (Time) o;

        if (local != null ? !local.equals(time.local) : time.local != null) return false;
        if (timezone != null ? !timezone.equals(time.timezone) : time.timezone != null)
            return false;
        if (utc != null ? !utc.equals(time.utc) : time.utc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timezone != null ? timezone.hashCode() : 0;
        result = 31 * result + (local != null ? local.hashCode() : 0);
        result = 31 * result + (utc != null ? utc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Time{" +
                "timezone='" + timezone + '\'' +
                ", local='" + local + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }
}