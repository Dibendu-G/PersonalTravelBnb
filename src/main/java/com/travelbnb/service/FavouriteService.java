package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.FavouritePayload;

public interface FavouriteService {
    FavouritePayload addFavourtie(AppUserEntity user, long propertyId, FavouritePayload favouritePayload);
}
