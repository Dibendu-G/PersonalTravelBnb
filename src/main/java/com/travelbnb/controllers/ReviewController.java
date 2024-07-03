package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.entity.ReviewsEntity;
import com.travelbnb.payloads.ReviewsPayload;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.repository.ReviewRepository;
import com.travelbnb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
    }

    @PostMapping("/addReviews")
    public ResponseEntity<ReviewsPayload> addReview(
            @AuthenticationPrincipal AppUserEntity appUser,
            @RequestParam long propertyid,
            @RequestBody ReviewsPayload reviewsPayload){

        if(reviewsPayload.getAppUserEntity()==null){
            reviewsPayload.setAppUserEntity(appUser);
        }
        ReviewsPayload review = reviewService.addReviews(appUser, propertyid, reviewsPayload);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
    }

    @GetMapping("/getReviewsByUser")
    public ResponseEntity<List<ReviewsPayload>>getUserReviews(@AuthenticationPrincipal AppUserEntity user)
    {
        List<ReviewsPayload> reviews= reviewService.getReviewsByUser(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }
}
