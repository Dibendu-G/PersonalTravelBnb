package com.travelbnb.service;

import com.travelbnb.entity.CountryEntity;
import com.travelbnb.entity.LocationEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.payloads.PropertyPayload;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.repository.LocationRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
