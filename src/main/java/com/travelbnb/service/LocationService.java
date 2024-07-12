package com.travelbnb.service;

import com.travelbnb.payloads.LocationPayload;

import java.util.List;

public interface LocationService {
    LocationPayload addLocations(LocationPayload locationPayload);

    List<LocationPayload> getAllLocations(int pageSize, int pageNo, String sortBy, String sortDir);
}
