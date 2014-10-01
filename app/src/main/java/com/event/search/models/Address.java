package com.event.search.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by na389 on 10/1/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements DataModel{
    @JsonProperty(value = "address_1")
    String address_1;

    @JsonProperty(value = "address_2")
    String address_2;

    @JsonProperty(value = "city")
    String city;

    @JsonProperty(value = "region")
    String region;

    @JsonProperty(value = "postal_code")
    String postal_code;

    @JsonProperty(value = "country")
    String country;

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (address_1 != null ? !address_1.equals(address.address_1) : address.address_1 != null)
            return false;
        if (address_2 != null ? !address_2.equals(address.address_2) : address.address_2 != null)
            return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (country != null ? !country.equals(address.country) : address.country != null)
            return false;
        if (postal_code != null ? !postal_code.equals(address.postal_code) : address.postal_code != null)
            return false;
        if (region != null ? !region.equals(address.region) : address.region != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address_1 != null ? address_1.hashCode() : 0;
        result = 31 * result + (address_2 != null ? address_2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (postal_code != null ? postal_code.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address_1='" + address_1 + '\'' +
                ", address_2='" + address_2 + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
