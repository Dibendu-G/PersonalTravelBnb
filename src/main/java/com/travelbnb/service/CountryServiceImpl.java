package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.CountryEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.CountryPayload;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, PropertyRepository propertyRepository) {
        this.countryRepository = countryRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public CountryPayload addCountry(CountryPayload countryPayload) {

        CountryEntity ce = PayloadToEntity(countryPayload);
        CountryEntity saved = countryRepository.save(ce);
        CountryPayload cpd = EntityToPayload(saved);

        return cpd;
    }

    @Override
    public List<CountryPayload> getAllCountries(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable;
        if (sortDir.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        Page<CountryEntity> countryPage = countryRepository.findAll(pageable);
        List<CountryPayload> countryPayloads = countryPage.getContent().stream()
                .map(this::EntityToPayload)
                .collect(Collectors.toList());

        return countryPayloads;
    }

    @Override
    public CountryEntity updateCountryDetails(long countryId, CountryPayload countryPayload) {
        Optional<CountryEntity> byId = countryRepository.findById(countryId);
        CountryEntity aue = byId.get();

        aue.setName(countryPayload.getName());

        return countryRepository.save(aue);


    }

//    @Override
//    public void deleteUser(long countryId) {
//        countryRepository.deleteById(countryId);
//    }

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
