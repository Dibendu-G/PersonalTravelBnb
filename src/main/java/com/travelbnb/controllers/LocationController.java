package com.travelbnb.controllers;

import com.travelbnb.payloads.LocationPayload;
import com.travelbnb.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //    Applying Pagination and Sorting
    @GetMapping
    public ResponseEntity<List<LocationPayload>> getAllLocations(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<LocationPayload> allLocationsDetails = locationService.getAllLocations(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allLocationsDetails,HttpStatus.OK);
    }
}
