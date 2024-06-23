package com.travelbnb.payloads;

import jakarta.persistence.Column;

public class PropertyPayload {
    private Long id;
    private String name;
    private Integer noGuest;
    private Integer noBathroom;
    private Integer nightlyPrice;
    private Integer noBedroom;
    private Long locationEntity;
    private Long countryEntity;

    public Long getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(Long locationEntity) {
        this.locationEntity = locationEntity;
    }

    public Long getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(Long countryEntity) {
        this.countryEntity = countryEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoGuest() {
        return noGuest;
    }

    public void setNoGuest(Integer noGuest) {
        this.noGuest = noGuest;
    }

    public Integer getNoBathroom() {
        return noBathroom;
    }

    public void setNoBathroom(Integer noBathroom) {
        this.noBathroom = noBathroom;
    }

    public Integer getNightlyPrice() {
        return nightlyPrice;
    }

    public void setNightlyPrice(Integer nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }

    public Integer getNoBedroom() {
        return noBedroom;
    }

    public void setNoBedroom(Integer noBedroom) {
        this.noBedroom = noBedroom;
    }
}
