package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.CountryEntity;
import com.travelbnb.entity.LocationEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.PropertyPayload;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.repository.LocationRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private LocationRepository locationRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository, CountryRepository countryRepository, LocationRepository locationRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public PropertyPayload addProperty(PropertyPayload propertyPayload) {
        PropertyEntity pre = PayloadToEntity(propertyPayload);
        PropertyEntity saved = propertyRepository.save(pre);
        PropertyPayload ppd = EntityToPayload(saved);
        return ppd;
    }

    @Override
    public List<PropertyPayload> getAllProperties(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable;
        if (sortDir.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        Page<PropertyEntity> userPage = propertyRepository.findAll(pageable);
        List<PropertyPayload> propertyPayloads = userPage.getContent().stream()
                .map(this::EntityToPayload)
                .collect(Collectors.toList());

        return propertyPayloads;
    }

    //    Conversions Payload To Entity
    PropertyEntity PayloadToEntity(PropertyPayload propertyPayload)
    {
        PropertyEntity pre = new PropertyEntity();
        pre.setName(propertyPayload.getName());
        pre.setNightlyPrice(propertyPayload.getNightlyPrice());
        pre.setNoBathroom(propertyPayload.getNoBathroom());
        pre.setNoBedroom(propertyPayload.getNoBedroom());
        pre.setNoGuest(propertyPayload.getNoGuest());

        if(propertyPayload.getCountryEntity() != null)
        {
            CountryEntity countryEntity = countryRepository.findById(propertyPayload.getCountryEntity()).orElseThrow(()-> new IllegalArgumentException("Country With that ID not Found!!"));
            pre.setCountryEntity(countryEntity);
        }
        if(propertyPayload.getLocationEntity()!=null)
        {
            LocationEntity locationEntity = locationRepository.findById(propertyPayload.getLocationEntity()).orElseThrow(()-> new IllegalArgumentException("Location With that ID not Found!!"));
            pre.setLocationEntity(locationEntity);
        }
        return pre;
    }

//    Conversions Entity to Payload
    PropertyPayload EntityToPayload(PropertyEntity pre)
    {
        PropertyPayload ppd = new PropertyPayload();
        ppd.setId(pre.getId());
        ppd.setName(pre.getName());
        ppd.setNightlyPrice(pre.getNightlyPrice());
        ppd.setNoBathroom(pre.getNoBathroom());
        ppd.setNoBedroom(pre.getNoBedroom());
        ppd.setNoGuest(pre.getNoGuest());

        if(pre.getCountryEntity()!=null)
        {
            ppd.setCountryEntity(pre.getCountryEntity().getId());
        }
        if(pre.getLocationEntity()!=null)
        {
            ppd.setLocationEntity(pre.getLocationEntity().getId());
        }

        return ppd;
    }
}
