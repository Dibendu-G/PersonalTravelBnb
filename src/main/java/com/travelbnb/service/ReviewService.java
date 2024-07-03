package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.ReviewsPayload;

import java.util.List;

public interface ReviewService {
    ReviewsPayload addReviews(AppUserEntity appUser, long propertyid, ReviewsPayload reviewsPayload);

    List<ReviewsPayload> getReviewsByUser(AppUserEntity user);
}
