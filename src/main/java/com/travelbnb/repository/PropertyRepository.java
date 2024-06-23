package com.travelbnb.repository;

import com.travelbnb.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<PropertyEntity,Long> {
}
