package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.FavouritePayload;

import java.util.List;

public interface FavouriteService {
    FavouritePayload addFavourtie(AppUserEntity user, long propertyId, FavouritePayload favouritePayload);

    List<FavouritePayload> getAllFavourites(int pageSize, int pageNo, String sortBy, String sortDir);
}
