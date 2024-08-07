package com.travelbnb.controllers;

import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.PropertyPayload;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyService propertyService;
    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
    }

    @RequestMapping("/addProperty")
    public ResponseEntity<PropertyPayload> addProperty(@RequestBody PropertyPayload propertyPayload)
    {
        PropertyPayload ppd = propertyService.addProperty(propertyPayload);
        return new ResponseEntity<>(ppd, HttpStatus.OK);
    }

    @GetMapping("/search/properties")
    public ResponseEntity<List<PropertyEntity>> searchProperty(@RequestParam String name)
    {
        List<PropertyEntity> properties = propertyRepository.searchProperty(name);
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    //    Applying Pagination and Sorting
    @GetMapping
    public ResponseEntity<List<PropertyPayload>> getAllProperties(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<PropertyPayload> allPropertiesDetails = propertyService.getAllProperties(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allPropertiesDetails,HttpStatus.OK);
    }
}
