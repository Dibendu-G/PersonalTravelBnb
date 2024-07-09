package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.BookingPayload;
import com.travelbnb.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingPayload> createBooking(@RequestParam long porpertyId,
                                                        @AuthenticationPrincipal AppUserEntity user,
                                                        @RequestBody BookingPayload bookingPayload){
            BookingPayload crtBook = bookingService.createBookings(porpertyId,user,bookingPayload);
            return new ResponseEntity<>(crtBook, HttpStatus.CREATED);
    }
}
