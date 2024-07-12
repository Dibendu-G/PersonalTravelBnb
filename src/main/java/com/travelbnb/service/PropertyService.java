package com.travelbnb.service;

import com.travelbnb.payloads.PropertyPayload;

import java.util.List;

public interface PropertyService {
    PropertyPayload addProperty(PropertyPayload propertyPayload);

    List<PropertyPayload> getAllProperties(int pageSize, int pageNo, String sortBy, String sortDir);
}
