package com.travelbnb.repository;

import com.travelbnb.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<PropertyEntity,Long> {

    @Query("Select p from PropertyEntity p JOIN LocationEntity l on p.locationEntity=l.id JOIN CountryEntity c on p.countryEntity =c.id where l.name=:locationName or c.name=:locationName")
    List<PropertyEntity> searchProperty(@Param("locationName") String locationName);
}
