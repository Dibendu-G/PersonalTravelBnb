package com.travelbnb.payloads;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.PropertyEntity;


public class FavouritePayload {
    private Long id;
    private Boolean status;
    private PropertyEntity propertyEntity;
    private AppUserEntity appUserEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PropertyEntity getPropertyEntity() {
        return propertyEntity;
    }

    public void setPropertyEntity(PropertyEntity propertyEntity) {
        this.propertyEntity = propertyEntity;
    }

    public AppUserEntity getAppUserEntity() {
        return appUserEntity;
    }

    public void setAppUserEntity(AppUserEntity appUserEntity) {
        this.appUserEntity = appUserEntity;
    }
}
