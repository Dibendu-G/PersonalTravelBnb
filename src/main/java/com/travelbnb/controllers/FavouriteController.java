package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.FavouritePayload;
import com.travelbnb.service.FavouriteService;
import com.travelbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
