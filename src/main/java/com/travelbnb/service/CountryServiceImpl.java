package com.travelbnb.service;

import com.travelbnb.entity.CountryEntity;
import com.travelbnb.payloads.CountryPayload;
import com.travelbnb.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryPayload addCountry(CountryPayload countryPayload) {

        CountryEntity ce = PayloadToEntity(countryPayload);
        CountryEntity saved = countryRepository.save(ce);
        CountryPayload cpd = EntityToPayload(saved);

        return cpd;
    }

//    Conversions PayloadToEntity
    CountryEntity PayloadToEntity(CountryPayload countryPayload)
    {
        CountryEntity ce = new CountryEntity();
        ce.setName(countryPayload.getName());

        return ce;
    }

//    Conversions EntityToPayload
    CountryPayload EntityToPayload(CountryEntity countryEntity)
    {
        CountryPayload cpd = new CountryPayload();
        cpd.setId(countryEntity.getId());
        cpd.setName(countryEntity.getName());

        return cpd;
    }
}
