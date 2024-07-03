package com.travelbnb.repository;

import com.travelbnb.entity.FavouriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
}