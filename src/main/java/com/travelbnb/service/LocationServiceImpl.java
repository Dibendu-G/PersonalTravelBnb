package com.travelbnb.service;

import com.travelbnb.entity.LocationEntity;
import com.travelbnb.payloads.LocationPayload;
import com.travelbnb.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<LocationPayload> getAllLocations(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable;
        if (sortDir.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        Page<LocationEntity> userPage = locationRepository.findAll(pageable);
        List<LocationPayload> locationPayloads = userPage.getContent().stream()
                .map(this::EntityToPayload)
                .collect(Collectors.toList());

        return locationPayloads;
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
