package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.ReviewsPayload;

public interface ReviewService {
    ReviewsPayload addReviews(AppUserEntity appUser, long propertyid, ReviewsPayload reviewsPayload);
}
