package com.travelbnb.service;

import com.travelbnb.entity.LocationEntity;
import com.travelbnb.payloads.LocationPayload;
import com.travelbnb.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService{

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationPayload addLocations(LocationPayload locationPayload) {
        LocationEntity le = PayloadsToEntity(locationPayload);
        LocationEntity saved = locationRepository.save(le);
        LocationPayload lpd = EntityToPayload(saved);

        return lpd;
    }

//    Conversions PayloadToEntity
    LocationEntity PayloadsToEntity(LocationPayload lpd)
    {
        LocationEntity le = new LocationEntity();
        le.setName(lpd.getName());

        return le;
    }

//    Conversions EntityToPayload
    LocationPayload EntityToPayload(LocationEntity le)
    {
        LocationPayload lpd = new LocationPayload();
        lpd.setId(le.getId());
        lpd.setName(le.getName());

        return lpd;
    }
}
