package com.travelbnb.service;

import com.travelbnb.payloads.LocationPayload;

public interface LocationService {
    LocationPayload addLocations(LocationPayload locationPayload);
}
