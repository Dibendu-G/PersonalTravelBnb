package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.entity.ReviewsEntity;
import com.travelbnb.exception.GlobalExceptionHandler;
import com.travelbnb.exception.NotFoundException;
import com.travelbnb.payloads.ReviewsPayload;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public ReviewsPayload addReviews(AppUserEntity appUser, long propertyid, ReviewsPayload reviewsPayload) {
        Optional<PropertyEntity> opProperty = propertyRepository.findById(propertyid);
        if(opProperty.isEmpty()){
            throw new NotFoundException("Property Not Found");
        }

        PropertyEntity property = opProperty.get();

        AppUserEntity User = reviewsPayload.getAppUserEntity();

        if(User == null){
            throw new NotFoundException("User Not Found");
        }

        if(reviewRepository.findReviewByUser(appUser,property)==null) {
            ReviewsEntity reviewsEntity = PayloadToEntity(reviewsPayload);
            reviewsEntity.setAppUserEntity(User);
            reviewsEntity.setPropertyEntity(property);

            ReviewsEntity saved = reviewRepository.save(reviewsEntity);

            ReviewsPayload rpd = EntityToPayload(saved);

            return rpd;
        }
        else{
            throw new RuntimeException("Review Already Exist");
        }
    }

    @Override
    public List<ReviewsPayload> getReviewsByUser(AppUserEntity user) {
        List<ReviewsEntity> reviewsEntities = reviewRepository.findByUserReviews(user);
        List<ReviewsPayload> reviewsPayloads = new ArrayList<>();
        for (ReviewsEntity re : reviewsEntities) {
            reviewsPayloads.add(EntityToPayload(re));
        }
        return reviewsPayloads;
    }

    //    Conversion Payload To Entity
    ReviewsEntity PayloadToEntity(ReviewsPayload reviewsPayload){
        ReviewsEntity re = new ReviewsEntity();
        re.setRatings(reviewsPayload.getRatings());
        re.setDescription(reviewsPayload.getDescription());
        re.setAppUserEntity(reviewsPayload.getAppUserEntity());
        re.setPropertyEntity(reviewsPayload.getPropertyEntity());

        return re;
    }

//    Conversion to Entity To Payload
    ReviewsPayload EntityToPayload(ReviewsEntity re){
        ReviewsPayload rpd = new ReviewsPayload();
        rpd.setId(re.getId());
        rpd.setRatings(re.getRatings());
        rpd.setDescription(re.getDescription());
        rpd.setAppUserEntity(re.getAppUserEntity());
        rpd.setPropertyEntity(re.getPropertyEntity());

        return rpd;
    }
}
