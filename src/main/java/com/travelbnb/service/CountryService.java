package com.travelbnb.service;

import com.travelbnb.entity.CountryEntity;
import com.travelbnb.payloads.CountryPayload;

import java.util.List;

public interface CountryService {
    CountryPayload addCountry(CountryPayload countryPayload);

    List<CountryPayload> getAllCountries(int pageSize, int pageNo, String sortBy, String sortDir);

    CountryEntity updateCountryDetails(long countryId, CountryPayload countryPayload);

//    void deleteUser(long countryId);
}
