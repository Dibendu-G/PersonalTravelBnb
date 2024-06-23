package com.travelbnb.controllers;

import com.travelbnb.payloads.CountryPayload;
import com.travelbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Country")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<CountryPayload> addCountry(@RequestBody CountryPayload countryPayload)
    {
        CountryPayload cpd = countryService.addCountry(countryPayload);
        return new ResponseEntity<>(cpd, HttpStatus.OK);
    }
}
