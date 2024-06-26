package com.travelbnb.repository;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<ReviewsEntity,Long> {

    @Query("Select r from ReviewsEntity r where r.appUserEntity=:user and r.propertyEntity=:property")
    ReviewsEntity findReviewByUser(@RequestParam("user") AppUserEntity user,@RequestParam("property") PropertyEntity property);
}
