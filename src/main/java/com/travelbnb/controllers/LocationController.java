package com.travelbnb.controllers;

import com.travelbnb.payloads.LocationPayload;
import com.travelbnb.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/addLocations")
    public ResponseEntity<LocationPayload> addLocations(@RequestBody LocationPayload locationPayload)
    {
        LocationPayload lpayload = locationService.addLocations(locationPayload);
        return new ResponseEntity<>(lpayload, HttpStatus.OK);
    }
}
