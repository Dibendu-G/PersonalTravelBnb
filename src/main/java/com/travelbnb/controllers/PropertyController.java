package com.travelbnb.controllers;

import com.travelbnb.payloads.PropertyPayload;
import com.travelbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @RequestMapping("/addProperty")
    public ResponseEntity<PropertyPayload> addProperty(@RequestBody PropertyPayload propertyPayload)
    {
        PropertyPayload ppd = propertyService.addProperty(propertyPayload);
        return new ResponseEntity<>(ppd, HttpStatus.OK);
    }
}
