package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.CountryEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.CountryPayload;
import com.travelbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //    Updating

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryEntity> updateUser(@PathVariable long countryId,
                                                    @RequestBody CountryPayload countryPayload)
    {
        CountryEntity app = countryService.updateCountryDetails(countryId,countryPayload);
        return new ResponseEntity<>(app,HttpStatus.OK);
    }

//    Deleting

//    @DeleteMapping("/{countryId}")
//    public ResponseEntity<?> deleteUser(@PathVariable long countryId) {
//        try {
//            countryService.deleteUser(countryId);
//            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
//        } catch (RuntimeException e)
//        {
//            return new  ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
//        }
//    }


    //    Applying Pagination and Sorting
    @GetMapping
    public ResponseEntity<List<CountryPayload>> getAllCountries(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<CountryPayload> allCountriesDetails = countryService.getAllCountries(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allCountriesDetails,HttpStatus.OK);
    }
}
