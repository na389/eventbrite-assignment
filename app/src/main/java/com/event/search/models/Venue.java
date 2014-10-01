package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue implements DataModel{
    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "longitude")
    String longitude;

    @JsonProperty(value = "latitude")
    String latitude;

    @JsonProperty(value = "address")
    Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venue)) return false;

        Venue venue = (Venue) o;

        if (address != null ? !address.equals(venue.address) : venue.address != null) return false;
        if (latitude != null ? !latitude.equals(venue.latitude) : venue.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(venue.longitude) : venue.longitude != null)
            return false;
        if (name != null ? !name.equals(venue.name) : venue.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    public void setAddress(Address address) {
        this.address = address;

    }

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address=" + address +
                '}';
    }
}
