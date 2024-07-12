package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.FavouritePayload;
import com.travelbnb.service.FavouriteService;
import com.travelbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Favourites")
public class FavouriteController {

    private FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService, PropertyService propertyService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping("/addFavourites")
    public ResponseEntity<FavouritePayload> addFavourites(@AuthenticationPrincipal AppUserEntity user,
                                                          @RequestParam long propertyId,
                                                          @RequestBody FavouritePayload favouritePayload)
    {
        FavouritePayload favourite = favouriteService.addFavourtie(user,propertyId,favouritePayload);
        return new ResponseEntity<>(favourite, HttpStatus.CREATED);
    }

    //    Applying Pagination and Sorting
    @GetMapping
    public ResponseEntity<List<FavouritePayload>> getAllFavourites(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<FavouritePayload> allFavouritesDetails = favouriteService.getAllFavourites(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allFavouritesDetails,HttpStatus.OK);
    }
}
